/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.tree;

import com.facebook.research.asynchronousratchetingtree.crypto.Crypto;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.crypto.DHKeyPair;

public class SecretParentNode extends ParentNode implements SecretNode {
  private DHKeyPair keyPair;

  public SecretParentNode(SecretNode left, Node right) {
    this.left = left;
    this.right = right;
    byte[] dhOutput = left.getKeyPair().exchange(right.getPubKey());
    // Derive both a private ECDH key and an AES-128 encryption key.
    byte[] key = Crypto.hkdf(dhOutput, new byte[0], new byte[0], 32);

    keyPair = DHKeyPair.fromBytes(key, false);
  }

  public SecretParentNode(Node left, SecretNode right) {
    this.left = left;
    this.right = right;
    this.keyPair = DHKeyPair.fromBytes(right.getKeyPair().exchange(left.getPubKey()), false);
    byte[] dhOutput = right.getKeyPair().exchange(left.getPubKey());
    // Derive both a private ECDH key and an AES-128 encryption key.
    byte[] key = Crypto.hkdf(dhOutput, new byte[0], new byte[0], 32);

    keyPair = DHKeyPair.fromBytes(key, false);
  }

  public DHPubKey getPubKey() {
    return keyPair.getPubKey();
  }

  public DHKeyPair getKeyPair() {
    return keyPair;
  }

  public byte[] getRawSecretKey() {
    return keyPair.getPrivKeyBytes();
  }
}
