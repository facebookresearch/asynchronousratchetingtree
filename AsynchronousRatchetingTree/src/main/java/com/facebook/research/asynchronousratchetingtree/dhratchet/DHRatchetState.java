/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.dhratchet;

import com.facebook.research.asynchronousratchetingtree.GroupMessagingState;
import com.facebook.research.asynchronousratchetingtree.crypto.Crypto;
import com.facebook.research.asynchronousratchetingtree.crypto.DHKeyPair;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public class DHRatchetState extends GroupMessagingState {
  private byte[][] rootKeys;
  private DHKeyPair[] selfRatchetKeys;
  private DHPubKey[] remoteRatchetKeys;
  private boolean[] ratchetFlags;
  private boolean[] isSetup;

  public DHRatchetState(int peerNum, int peerCount) {
    super(peerNum, peerCount);
    rootKeys = new byte[peerCount][];
    selfRatchetKeys = new DHKeyPair[peerCount];
    remoteRatchetKeys = new DHPubKey[peerCount];
    ratchetFlags = new boolean[peerCount];
    isSetup = new boolean[peerCount];
  }

  public void setRootKey(int i, byte[] rootKey) {
    rootKeys[i] = rootKey;
  }

  public byte[] getRootKey(int i) {
    return rootKeys[i];
  }

  public void setSelfRatchetKey(int i, DHKeyPair ratchetKey) {
    selfRatchetKeys[i] = ratchetKey;
  }

  public DHKeyPair getSelfRatchetKey(int i) {
    return selfRatchetKeys[i];
  }

  public void setRemoteRatchetKey(int i, DHPubKey ratchetKey) {
    remoteRatchetKeys[i] = ratchetKey;
  }

  public DHPubKey getRemoteRatchetKey(int i) {
    return remoteRatchetKeys[i];
  }

  public void setRatchetFlag(int i, boolean flag) {
    ratchetFlags[i] = flag;
  }

  public boolean getRatchetFlag(int i) {
    return ratchetFlags[i];
  }

  public byte[] getKeyWithPeer(int n) {
    return Crypto.hkdf(rootKeys[n], new byte[0], new byte[0], 16);
  }

  public void setIsSetup(int i) {
    isSetup[i] = true;
  }

  public boolean getIsSetup(int i) {
    return isSetup[i];
  }
}
