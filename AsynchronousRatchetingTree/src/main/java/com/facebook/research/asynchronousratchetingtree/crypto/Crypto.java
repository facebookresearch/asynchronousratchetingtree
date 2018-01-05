/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.crypto;

import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.art.tree.Node;
import djb.Curve25519;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * This crypto class is put together simply to make coding the rest of the example program easier. Please never actually
 * use it in production.
 */
public class Crypto {
  final public static int HASH_LENGTH = 32;

  public static MessageDigest startSHA256() {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw Utils.except(e);
    }
    return md;
  }

  public static byte[] hmacSha256(byte[] data, byte[] key) {
    Mac mac;
    try {
      mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(key, "HmacSHA256"));
    } catch (Exception e) {
      throw Utils.except(e);
    }
    mac.update(data);
    return mac.doFinal();
  }

  public static byte[] hkdf(byte[] input_keying_material, byte[] salt, byte[] info, int num_bytes) {
    // Extract step
    byte[] pseudo_random_key = hmacSha256(salt, input_keying_material);

    // Expand step
    byte[] output_bytes = new byte[num_bytes];
    byte[] t = new byte[0];
    for (byte i = 0; i < (num_bytes + 31) / 32; i++) {
      byte[] tInput = new byte[t.length + info.length + 1];
      System.arraycopy(t, 0, tInput, 0, t.length);
      System.arraycopy(info, 0, tInput, t.length, info.length);
      tInput[tInput.length - 1] = i;

      t = hmacSha256(pseudo_random_key, tInput);
      int num_to_copy = num_bytes - (i * 32);
      if (num_to_copy > 32) {
        num_to_copy = 32;
      }

      System.arraycopy(t, 0, output_bytes, i * 32, num_to_copy);
    }
    return output_bytes;
  }

  public static byte[] artKDF(
    byte[] lastStageKey,
    byte[] treeKey,
    DHPubKey[] identities,
    Node tree
  ) {
    byte[] serialisedTree = Utils.serialise(Node.toThrift(tree));
    byte[] ikm = new byte[lastStageKey.length + treeKey.length + serialisedTree.length];
    System.arraycopy(lastStageKey, 0, ikm, 0, lastStageKey.length);
    System.arraycopy(treeKey, 0, ikm, lastStageKey.length, treeKey.length);
    System.arraycopy(serialisedTree, 0, ikm, lastStageKey.length + treeKey.length, serialisedTree.length);

    byte[] info = new byte[identities.length * Curve25519.KEY_SIZE];
    for (int i = 0; i < identities.length; i++) {
      System.arraycopy(identities[i].getPubKeyBytes(), 0, info, i * Curve25519.KEY_SIZE, Curve25519.KEY_SIZE);
    }
    return hkdf(ikm, new byte[0], info, 16);
  }

  public static byte[] randomBytes(int n) {
    byte[] result = new byte[n];
    try {
	/* Nobody should ever use java.util.Random. However,
	 * SecureRandom doesn't work out of the box on all platforms
	 * (e.g. the openjdk in Ubuntu's repositories), so we'll use
	 * Random here to make it easier for anyone to
	 * run. Fortunately, this is not intended to be used for
	 * actual secure messaging, so that's all right. */
	Random rng = new Random();
	rng.nextBytes(result);
    } catch (Exception e) {
      throw Utils.except(e);
    }
    return result;
  }

  public static byte[] encrypt(byte[] message, byte[] keyBytes) {
    Cipher cipher;
    Key key;
    try {
      cipher = Cipher.getInstance("AES/GCM/NoPadding");
      byte[] nonce = randomBytes(12);
      GCMParameterSpec paramSpec = new GCMParameterSpec(16 * 8, nonce);

      key = new SecretKeySpec(keyBytes, "AES");
      cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

      int len = cipher.getOutputSize(message.length);
      byte[] result = new byte[len + 12];
      System.arraycopy(nonce, 0, result, 0, 12);

      cipher.doFinal(
        message,
        0,
        message.length,
        result,
        12
      );
      return result;
    } catch (Exception e) {
      throw Utils.except(e);
    }
  }

  public static byte[] decrypt(byte[] encrypted, byte[] keyBytes) {
    Cipher cipher;
    Key key;
    try {
      cipher = Cipher.getInstance("AES/GCM/NoPadding");

      byte[] nonce = Arrays.copyOfRange(encrypted, 0, 12);
      byte[] ciphertext = Arrays.copyOfRange(encrypted, 12, encrypted.length);
      GCMParameterSpec paramSpec = new GCMParameterSpec(16 * 8, nonce);

      key = new SecretKeySpec(keyBytes, "AES");
      cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

      return cipher.doFinal(ciphertext);
    } catch (Exception e) {
      throw Utils.except(e);
    }
  }

  public static byte[] keyExchangeInitiate(
    DHKeyPair selfIdentity,
    DHPubKey remoteIdentity,
    DHKeyPair keyExchangeKeyPair,
    DHPubKey remoteEphemeralKey
  ) {
    MessageDigest md = Crypto.startSHA256();
    md.update(selfIdentity.exchange(remoteIdentity));
    md.update(selfIdentity.exchange(remoteEphemeralKey));
    md.update(keyExchangeKeyPair.exchange(remoteIdentity));
    md.update(keyExchangeKeyPair.exchange(remoteEphemeralKey));
    return md.digest();
  }

  public static byte[] keyExchangeReceive(
    DHKeyPair selfIdentity,
    DHPubKey remoteIdentity,
    DHKeyPair ephemeralKey,
    DHPubKey keyExchangeKey
  ) {
    MessageDigest md = Crypto.startSHA256();
    md.update(selfIdentity.exchange(remoteIdentity));
    md.update(ephemeralKey.exchange(remoteIdentity));
    md.update(selfIdentity.exchange(keyExchangeKey));
    md.update(ephemeralKey.exchange(keyExchangeKey));
    return md.digest();
  }
}
