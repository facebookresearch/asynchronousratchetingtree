/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.dhratchet;

import com.facebook.research.asynchronousratchetingtree.KeyServer;
import com.facebook.research.asynchronousratchetingtree.GroupMessagingTestImplementation;
import com.facebook.research.asynchronousratchetingtree.MessageDistributer;
import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.crypto.Crypto;
import com.facebook.research.asynchronousratchetingtree.crypto.DHKeyPair;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.crypto.SignedDHPubKey;
import com.facebook.research.asynchronousratchetingtree.dhratchet.message.DHRatchetMessageDistributer;
import com.facebook.research.asynchronousratchetingtree.dhratchet.message.DHRatchetSetupMessage;
import com.facebook.research.asynchronousratchetingtree.dhratchet.message.DHRatchetMessage;

public class DHRatchet implements GroupMessagingTestImplementation<DHRatchetState> {
  @Override
  public byte[] setupMessageForPeer(DHRatchetState state, DHPubKey[] peers, KeyServer keyServer, int i) {
    DHKeyPair identityKeyPair = state.getIdentityKeyPair();

    SignedDHPubKey signedPreKey = keyServer.getSignedPreKey(state, i);
    if (!peers[i].verify(signedPreKey.getPubKeyBytes(), signedPreKey.getSignature())) {
      Utils.except("PreKey signature check failed.");
    }

    DHKeyPair ratchetKey = DHKeyPair.generate(false);
    state.setRootKey(
      i,
      Crypto.keyExchangeInitiate(
        identityKeyPair,
        peers[i],
        ratchetKey,
        keyServer.getSignedPreKey(state, i)
      )
    );
    state.setSelfRatchetKey(i, ratchetKey);
    state.setRatchetFlag(i, false);
    state.setIsSetup(i);

    DHRatchetSetupMessage setupMessage = new DHRatchetSetupMessage(
      state.getPeerNum(),
      identityKeyPair.getPubKey(),
      ratchetKey.getPubKey()
    );
    return setupMessage.serialise();
  }

  @Override
  public void processSetupMessage(DHRatchetState state, byte[] serialisedMessage, int participantNum) {
    DHRatchetSetupMessage message = new DHRatchetSetupMessage(serialisedMessage);
    int peerNum = message.getPeerNum();
    state.setRootKey(
      peerNum,
      Crypto.keyExchangeReceive(
        state.getIdentityKeyPair(),
        message.getIdentity(),
        state.getPreKeyFor(peerNum),
        message.getEphemeralKey()
      )
    );
    state.setRemoteRatchetKey(peerNum, message.getEphemeralKey());
    state.setRatchetFlag(peerNum, true);
    state.setIsSetup(peerNum);
  }

  @Override
  public MessageDistributer sendMessage(DHRatchetState state, byte[] plaintext) {
    byte[][] messages = new byte[state.getPeerCount()][];
    for (int i = 0; i < state.getPeerCount(); i++) {
      if (i == state.getPeerNum()) {
        continue;
      }
      byte[] rootKey = state.getRootKey(i);
      if (state.getRatchetFlag(i)) {
        DHKeyPair selfRatchetKey = DHKeyPair.generate(false);
        state.setSelfRatchetKey(i, selfRatchetKey);
        rootKey = Crypto.hmacSha256(
          rootKey,
          selfRatchetKey.exchange(state.getRemoteRatchetKey(i))
        );
        state.setRootKey(i, rootKey);
        state.setRatchetFlag(i, false);
      }

      byte[] ciphertext = Crypto.encrypt(plaintext, rootKey);
      DHRatchetMessage message = new DHRatchetMessage(
        state.getPeerNum(),
        state.getSelfRatchetKey(i).getPubKey(),
        ciphertext
      );
      messages[i] = message.serialise();
    }
    return new DHRatchetMessageDistributer(messages);
  }

  @Override
  public byte[] receiveMessage(DHRatchetState state, byte[] serialisedMessage) {
    DHRatchetMessage message = new DHRatchetMessage(serialisedMessage);
    int i = message.getPeerNum();
    byte[] rootKey = state.getRootKey(i);

    if (!state.getRatchetFlag(i)) {
      DHKeyPair selfRatchetKey = state.getSelfRatchetKey(i);
      DHPubKey remoteRatchetKey = message.getRatchetKey();

      rootKey = Crypto.hmacSha256(
        rootKey,
        selfRatchetKey.exchange(remoteRatchetKey)
      );
      state.setRootKey(i, rootKey);
      state.setRemoteRatchetKey(
        i,
        remoteRatchetKey
      );
      state.setRatchetFlag(i, true);
    }

    return Crypto.decrypt(message.getCiphertext(), rootKey);
  }
}
