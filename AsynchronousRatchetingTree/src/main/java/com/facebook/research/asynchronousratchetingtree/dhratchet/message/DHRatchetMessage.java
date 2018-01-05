/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.dhratchet.message;

import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.dhratchet.message.thrift.DHRatchetMessageStruct;

public class DHRatchetMessage {
  private int peerNum;
  private DHPubKey ratchetKey;
  private byte[] ciphertext;

  public DHRatchetMessage(
    int peerNum,
    DHPubKey ratchetKey,
    byte[] ciphertext
  ) {
    this.peerNum = peerNum;
    this.ratchetKey = ratchetKey;
    this.ciphertext = ciphertext;
  }

  public DHRatchetMessage(byte[] thriftSerialised) {
    DHRatchetMessageStruct struct = new DHRatchetMessageStruct();
    Utils.deserialise(struct, thriftSerialised);
    peerNum = struct.peerNum;
    ratchetKey = DHPubKey.pubKey(struct.getRatchetKey());
    ciphertext = struct.getCiphertext();
  }

  public int getPeerNum() {
    return peerNum;
  }

  public DHPubKey getRatchetKey() {
    return ratchetKey;
  }

  public byte[] getCiphertext() {
    return ciphertext;
  }

  public byte[] serialise() {
    DHRatchetMessageStruct struct = new DHRatchetMessageStruct();
    struct.setPeerNum(peerNum);
    struct.setRatchetKey(ratchetKey.getPubKeyBytes());
    struct.setCiphertext(ciphertext);
    return Utils.serialise(struct);
  }
}
