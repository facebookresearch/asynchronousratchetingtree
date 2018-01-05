/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.crypto;

public class SignedDHPubKey extends DHPubKey {
  private byte[] signature;

  public SignedDHPubKey(byte[] pubKey, byte[] signature) {
    super(pubKey);
    this.signature = signature;
  }

  public byte[] getSignature() {
    return signature;
  }
}
