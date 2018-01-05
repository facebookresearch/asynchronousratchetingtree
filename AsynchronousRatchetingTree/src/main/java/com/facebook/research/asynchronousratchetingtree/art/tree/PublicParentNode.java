/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.tree;

import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public class PublicParentNode extends ParentNode {
  private DHPubKey pubKey;

  public PublicParentNode(DHPubKey pubKey, Node left, Node right) {
    this.pubKey = pubKey;
    this.left = left;
    this.right = right;
  }

  public DHPubKey getPubKey() {
    return pubKey;
  }
}
