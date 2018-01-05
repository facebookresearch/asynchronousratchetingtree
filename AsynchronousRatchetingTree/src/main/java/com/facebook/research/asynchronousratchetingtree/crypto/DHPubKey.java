/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.crypto;

import djb.Curve25519;

import java.security.MessageDigest;
import java.util.Arrays;

public class DHPubKey {
  private byte[] pub;

  protected DHPubKey(byte[] pub) {
    this.pub = pub;
  }

  public static DHPubKey pubKey(byte[] pub) {
    if (pub == null || pub.length == 0) {
      return null;
    }
    return new DHPubKey(pub);
  }

  public byte[] getPubKeyBytes() {
    return pub;
  }

  public boolean verify(byte[] data, byte[] sig) {
    byte[] sig_first_part = Arrays.copyOfRange(sig, 0, Crypto.HASH_LENGTH);
    byte[] sig_second_part = Arrays.copyOfRange(sig, Crypto.HASH_LENGTH, sig.length);

    MessageDigest md = Crypto.startSHA256();
    md.update(data);
    md.update(getPubKeyBytes());
    byte[] digest = md.digest();

    byte[] output = new byte[Curve25519.KEY_SIZE];
    Curve25519.verify(
      output,
      sig_second_part,
      digest,
      getPubKeyBytes()
    );

    return Arrays.equals(
      Crypto.startSHA256().digest(output),
      sig_first_part
    );
  }
}
