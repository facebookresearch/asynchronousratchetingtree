/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

import com.facebook.research.asynchronousratchetingtree.crypto.DHKeyPair;
import com.facebook.research.asynchronousratchetingtree.crypto.SignedDHPubKey;

import java.util.HashMap;
import java.util.Map;

abstract public class GroupMessagingState {
  private int peerNum;
  private int peerCount;
  private DHKeyPair identityKeyPair;
  private Map<Integer, DHKeyPair> preKeys = new HashMap<>();
  private Map<Integer, SignedDHPubKey> signedPreKeys = new HashMap<>();

  public GroupMessagingState(int peerNum, int peerCount) {
    this.peerNum = peerNum;
    this.peerCount = peerCount;
    identityKeyPair = DHKeyPair.generate(true);
  }

  final public int getPeerNum() {
    return peerNum;
  }

  final public int getPeerCount() {
    return peerCount;
  }

  final public DHKeyPair getIdentityKeyPair() {
    return identityKeyPair;
  }

  final public DHKeyPair getPreKeyFor(int peerNum) {
    if (!preKeys.containsKey(peerNum)) {
      preKeys.put(peerNum, DHKeyPair.generate(false));
    }
    return preKeys.get(peerNum);
  }

  final public SignedDHPubKey getSignedDHPreKeyFor(int peerNum) {
    if (!signedPreKeys.containsKey(peerNum)) {
      byte[] pkBytes = getPreKeyFor(peerNum).getPubKeyBytes();
      byte[] signature = getIdentityKeyPair().sign(pkBytes);
      signedPreKeys.put(peerNum, new SignedDHPubKey(pkBytes, signature));
    }
    return signedPreKeys.get(peerNum);
  }

  abstract public byte[] getKeyWithPeer(int n);
}
