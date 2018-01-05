/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.message;

import com.facebook.research.asynchronousratchetingtree.art.message.thrift.SetupMessageStruct;
import com.facebook.research.asynchronousratchetingtree.art.tree.Node;
import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

import java.util.*;

public class SetupMessage {
  private DHPubKey[] identities;
  private Map<Integer, DHPubKey> ephemeralKeys;
  private DHPubKey keyExchangeKey;
  private Node tree;

  public SetupMessage(DHPubKey[] identities, Map<Integer, DHPubKey> ephemeralKeys, DHPubKey keyExchangeKey, Node tree) {
    this.identities = identities;
    this.ephemeralKeys = ephemeralKeys;
    this.keyExchangeKey = keyExchangeKey;
    this.tree = tree;
  }

  public SetupMessage(byte[] thriftSerialised) {
    SetupMessageStruct struct = new SetupMessageStruct();
    Utils.deserialise(struct, thriftSerialised);

    identities = new DHPubKey[struct.getIdentitiesSize()];
    for (int i = 0; i < identities.length; i++) {
      identities[i] = DHPubKey.pubKey(
        Base64.getDecoder().decode(struct.getIdentities().get(i))
      );
    }

    ephemeralKeys = new HashMap<>();
    for (int i = 1; i < identities.length; i++) {
      ephemeralKeys.put(
        i,
        DHPubKey.pubKey(
          Base64.getDecoder().decode(struct.getEphemeralKeys().get(i))
        )
      );
    }

    keyExchangeKey = DHPubKey.pubKey(struct.getKeyExchangeKey());
    tree = Node.fromThrift(struct.getTree());
  }

  public DHPubKey[] getIdentities() {
    return identities;
  }

  public Map<Integer, DHPubKey> getEphemeralKeys() {
    return ephemeralKeys;
  }

  public DHPubKey getKeyExchangeKey() {
    return keyExchangeKey;
  }

  public Node getTree() {
    return tree;
  }

  public byte[] serialise() {
    List<String> identities = new ArrayList<>();
    Map<Integer, String> ephemeralKeys = new HashMap<>();

    for (int i = 0; i < this.identities.length; i++) {
      identities.add(Base64.getEncoder().encodeToString(this.identities[i].getPubKeyBytes()));
    }

    for (int i = 1; i < this.identities.length; i++) {
      ephemeralKeys.put(i, Base64.getEncoder().encodeToString(this.ephemeralKeys.get(i).getPubKeyBytes()));
    }

    SetupMessageStruct struct = new SetupMessageStruct();
    struct.setIdentities(identities);
    struct.setEphemeralKeys(ephemeralKeys);
    struct.setKeyExchangeKey(keyExchangeKey.getPubKeyBytes());
    struct.setTree(Node.toThrift(tree));

    return Utils.serialise(struct);
  }
}
