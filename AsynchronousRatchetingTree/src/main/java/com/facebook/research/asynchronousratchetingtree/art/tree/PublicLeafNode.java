/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.tree;

import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public class PublicLeafNode extends LeafNode {
  private DHPubKey pubKey;

  public PublicLeafNode(DHPubKey pubKey) {
    this.pubKey = pubKey;
  }

  public DHPubKey getPubKey() {
    return pubKey;
  }
}
