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
import com.facebook.research.asynchronousratchetingtree.dhratchet.message.thrift.DHRatchetSetupMessageStruct;

public class DHRatchetSetupMessage {
  private int peerNum;
  private DHPubKey identity;
  private DHPubKey ephemeralKey;

  public DHRatchetSetupMessage(
    int peerNum,
    DHPubKey identity,
    DHPubKey ephemeralKey
  ) {
    this.peerNum = peerNum;
    this.identity = identity;
    this.ephemeralKey = ephemeralKey;
  }

  public DHRatchetSetupMessage(byte[] thriftSerialised) {
    DHRatchetSetupMessageStruct struct = new DHRatchetSetupMessageStruct();
    Utils.deserialise(struct, thriftSerialised);
    peerNum = struct.getPeerNum();
    identity = DHPubKey.pubKey(struct.getIdentityKey());
    ephemeralKey = DHPubKey.pubKey(struct.getEphemeralKey());
  }

  public int getPeerNum() {
    return peerNum;
  }

  public DHPubKey getIdentity() {
    return identity;
  }

  public DHPubKey getEphemeralKey() {
    return ephemeralKey;
  }

  public byte[] serialise() {
    DHRatchetSetupMessageStruct struct = new DHRatchetSetupMessageStruct();
    struct.setPeerNum(peerNum);
    struct.setIdentityKey(identity.getPubKeyBytes());
    struct.setEphemeralKey(ephemeralKey.getPubKeyBytes());
    return Utils.serialise(struct);
  }
}
