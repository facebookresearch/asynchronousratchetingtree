/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.tree;

abstract public class ParentNode implements Node {
  protected Node left;
  protected Node right;

  public int numLeaves() {
    return left.numLeaves() + right.numLeaves();
  }

  public Node getLeft() {
    return left;
  }

  public Node getRight() {
    return right;
  }
}
