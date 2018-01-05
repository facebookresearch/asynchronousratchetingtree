/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art;

import com.facebook.research.asynchronousratchetingtree.KeyServer;
import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.art.message.*;
import com.facebook.research.asynchronousratchetingtree.GroupMessagingTestImplementation;
import com.facebook.research.asynchronousratchetingtree.MessageDistributer;
import com.facebook.research.asynchronousratchetingtree.art.message.CiphertextMessage;
import com.facebook.research.asynchronousratchetingtree.crypto.Crypto;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.crypto.SignedDHPubKey;
import com.facebook.research.asynchronousratchetingtree.art.message.ARTMessageDistributer;
import com.facebook.research.asynchronousratchetingtree.art.message.AuthenticatedMessage;

import java.util.*;

final public class ARTTestImplementation implements GroupMessagingTestImplementation<ARTState> {
  public byte[] setupMessageForPeer(ARTState state, DHPubKey[] peers, KeyServer keyServer, int peer) {
    byte[] setupMessageSerialised = state.getSetupMessage();
    if (setupMessageSerialised == null) {
      Map<Integer, DHPubKey> preKeys = new HashMap<>();
      for (int i = 1; i < peers.length; i++) {
        SignedDHPubKey signedPreKey = keyServer.getSignedPreKey(state, i);
        if (!peers[i].verify(signedPreKey.getPubKeyBytes(), signedPreKey.getSignature())) {
          Utils.except("PreKey signature check failed.");
        }
        preKeys.put(i, signedPreKey);
      }
      AuthenticatedMessage setupMessage = ART.setupGroup(state, peers, preKeys);
      setupMessageSerialised = setupMessage.serialise();
      state.setSetupMessage(setupMessageSerialised);
    }
    return setupMessageSerialised;
  }

  public void processSetupMessage(ARTState state, byte[] serialisedMessage, int leafNum) {
    AuthenticatedMessage message = new AuthenticatedMessage(serialisedMessage);
    ART.processSetupMessage(state, message, leafNum);
  }

  public MessageDistributer sendMessage(ARTState state, byte[] plaintext) {
    AuthenticatedMessage updateMessage = ART.updateKey(state);

    // All peers have the same key, so the "withPeer(0)" aspect of this is a no-op.
    byte[] key = state.getKeyWithPeer(0);
    byte[] ciphertext = Crypto.encrypt(plaintext, key);

    CiphertextMessage message = new CiphertextMessage(updateMessage, ciphertext);
    return new ARTMessageDistributer(message);
  }

  public byte[] receiveMessage(ARTState state, byte[] serialisedMessage) {
    CiphertextMessage message = new CiphertextMessage(serialisedMessage);
    ART.processUpdateMessage(state, message.getAuthenticatedMessage());

    // All peers have the same key, so the "withPeer(0)" aspect of this is a no-op.
    byte[] key = state.getKeyWithPeer(0);
    return Crypto.decrypt(message.getCiphertext(), key);
  }
}
