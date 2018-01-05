/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.tree;

import com.facebook.research.asynchronousratchetingtree.art.message.thrift.NodeStruct;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public interface Node {
  DHPubKey getPubKey();
  int numLeaves();

  static Node fromThrift(NodeStruct thrift) {
    if (thrift == null) {
      return null;
    }
    DHPubKey pubKey = DHPubKey.pubKey(thrift.getPublicKey());
    NodeStruct left = thrift.getLeft();
    NodeStruct right = thrift.getRight();

    if (left == null && right == null) {
      return new PublicLeafNode(pubKey);
    }

    return new PublicParentNode(
      pubKey,
      fromThrift(left),
      fromThrift(right)
    );
  }

  static NodeStruct toThrift(Node tree) {
    if (tree == null) {
      return null;
    }
    NodeStruct struct = new NodeStruct();
    struct.setPublicKey(tree.getPubKey().getPubKeyBytes());
    if (tree instanceof ParentNode) {
      struct.setLeft(Node.toThrift(((ParentNode) tree).getLeft()));
      struct.setRight(Node.toThrift(((ParentNode) tree).getRight()));
    }
    return struct;
  }
}
