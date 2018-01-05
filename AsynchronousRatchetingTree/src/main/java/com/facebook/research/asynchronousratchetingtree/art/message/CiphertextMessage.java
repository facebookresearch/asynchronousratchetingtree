/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.message;

import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.art.message.thrift.CiphertextMessageStruct;

public class CiphertextMessage {
  private AuthenticatedMessage authenticatedMessage;
  private byte[] ciphertext;

  public CiphertextMessage(AuthenticatedMessage authenticatedMessage, byte[] ciphertext) {
    this.authenticatedMessage = authenticatedMessage;
    this.ciphertext = ciphertext;
  }

  public CiphertextMessage(byte[] thriftSerialised) {
    CiphertextMessageStruct struct = new CiphertextMessageStruct();
    Utils.deserialise(struct, thriftSerialised);
    authenticatedMessage = new AuthenticatedMessage(struct.getAuthenticatedMessage());
    ciphertext = struct.getCiphertext();
  }

  public AuthenticatedMessage getAuthenticatedMessage() {
    return authenticatedMessage;
  }

  public byte[] getCiphertext() {
    return ciphertext;
  }

  public byte[] serialise() {
    CiphertextMessageStruct struct = new CiphertextMessageStruct();
    struct.setAuthenticatedMessage(authenticatedMessage.getThriftStruct());
    struct.setCiphertext(ciphertext);
    return Utils.serialise(struct);
  }
}
