/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.crypto;

import com.facebook.research.asynchronousratchetingtree.Utils;
import djb.Curve25519;

import java.security.MessageDigest;

public class DHKeyPair extends DHPubKey {
  private byte[] priv;
  private byte[] privSign;

  protected DHKeyPair(byte[] pub, byte[] priv, byte[] privSign) {
    super(pub);
    this.priv = priv;
    this.privSign = privSign;
  }

  public static DHKeyPair generate(boolean allowSignatures) {
    return DHKeyPair.fromBytes(Crypto.randomBytes(Curve25519.KEY_SIZE), allowSignatures);
  }

  public static DHKeyPair fromBytes(byte[] priv, boolean allowSignatures) {
    byte[] pub = new byte[Curve25519.KEY_SIZE];
    byte[] privSign;
    if (allowSignatures) {
      privSign = new byte[Curve25519.KEY_SIZE];
      Curve25519.keygen(pub, privSign, priv);
    } else {
      privSign = null;
      Curve25519.keygen(pub, null, priv);
    }
    return new DHKeyPair(pub, priv, privSign);
  }

  public byte[] getPrivKeyBytes() {
    return priv;
  }

  public DHPubKey getPubKey() {
    return pubKey(getPubKeyBytes());
  }

  public byte[] exchange(DHPubKey bob) {
    byte[] result = new byte[Curve25519.KEY_SIZE];
    Curve25519.curve(result, getPrivKeyBytes(), bob.getPubKeyBytes());
    return result;
  }

  /**
   *  The Curve25519 library that we use implements KCDSA. The API provided accepts the first curve point
   *  as an argument, and returns the second part of the signature. The first part is a hash of the public
   *  curve point, so we append this ourselves.
   *  In DHPubKey, we then verify signatures by hashing the input data similarly, and checking that the
   *  output of the verification method equals the first part of the signature.
   *  http://grouper.ieee.org/groups/1363/P1363a/contributions/kcdsa1363.pdf
   */
  public byte[] sign(byte[] data) {
    if (privSign == null) {
      Utils.except("Non-signing key cannot be used for signing.");
    }
    boolean success = false;
    byte[] sig_second_part = new byte[0];
    byte[] sig_first_part = new byte[0];

    // Signature generation can fail, in which case we need to try a different Curve point.
    while (!success) {
      byte[] privCurvePoint = Crypto.randomBytes(Curve25519.KEY_SIZE);
      byte[] pubCurvePoint = new byte[Curve25519.KEY_SIZE];
      Curve25519.keygen(pubCurvePoint, null, privCurvePoint);
      sig_first_part = Crypto.startSHA256().digest(pubCurvePoint);

      MessageDigest md = Crypto.startSHA256();
      md.update(data);
      md.update(getPubKeyBytes());
      byte[] digest = md.digest();

      sig_second_part = new byte[Curve25519.KEY_SIZE];
      success = Curve25519.sign(
        sig_second_part,
        digest,
        privCurvePoint,
        privSign
      );
    }

    byte[] sig = new byte[Crypto.HASH_LENGTH + Curve25519.KEY_SIZE];
    System.arraycopy(sig_first_part, 0, sig, 0, Crypto.HASH_LENGTH);
    System.arraycopy(sig_second_part, 0, sig, Crypto.HASH_LENGTH, Curve25519.KEY_SIZE);

    return sig;
  }
}
