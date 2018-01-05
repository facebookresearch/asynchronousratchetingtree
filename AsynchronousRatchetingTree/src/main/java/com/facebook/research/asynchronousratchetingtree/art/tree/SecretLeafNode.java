/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.tree;

import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.crypto.DHKeyPair;

final public class SecretLeafNode extends LeafNode implements SecretNode {
  private DHKeyPair keyPair;

  public SecretLeafNode(DHKeyPair keyPair) {
    this.keyPair = keyPair;
  }

  public DHPubKey getPubKey() {
    return keyPair.getPubKey();
  }

  public DHKeyPair getKeyPair() {
    return keyPair;
  }
}
