/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art;

import com.facebook.research.asynchronousratchetingtree.GroupMessagingState;
import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.art.tree.SecretParentNode;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.art.tree.SecretNode;

public class ARTState extends GroupMessagingState {
  private SecretParentNode tree;
  private DHPubKey[] identities;
  private byte[] stageKey = new byte[0];
  private byte[] setupMessage;

  public ARTState(int peerNum, int peerCount) {
    super(peerNum, peerCount);
  }

  public SecretNode getTree() {
    return tree;
  }

  public void setSetupMessage(byte[] setupMessage) {
    this.setupMessage = setupMessage;
  }

  public byte[] getSetupMessage() {
    return setupMessage;
  }

  public byte[] getKeyWithPeer(int n) {
    return getStageKey();
  }

  public DHPubKey[] getIdentities() {
    return identities;
  }

  public void setIdentities(DHPubKey[] identities) {
    this.identities = identities;
  }

  public byte[] getStageKey() {
    return stageKey;
  }

  public void setStageKey(byte[] stageKey) {
    this.stageKey = stageKey;
  }

  public void setTree(SecretNode tree) {
    if (!(tree instanceof SecretParentNode)) {
      Utils.except("Tree cannot be a leaf.");
    }
    this.tree = (SecretParentNode) tree;
  }
}
