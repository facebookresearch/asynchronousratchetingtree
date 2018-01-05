/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.message;

import com.facebook.research.asynchronousratchetingtree.Utils;
import com.facebook.research.asynchronousratchetingtree.art.message.thrift.AuthenticatedMessageStruct;

public class AuthenticatedMessage {
  private byte[] message;
  private byte[] authenticator;

  public AuthenticatedMessage(
    byte[] message,
    byte[] authenticator
  ) {
    this.message = message;
    this.authenticator = authenticator;
  }

  public AuthenticatedMessage(byte[] thriftSerialised) {
    AuthenticatedMessageStruct struct = new AuthenticatedMessageStruct();
    Utils.deserialise(struct, thriftSerialised);

    message = struct.getMessage();
    authenticator = struct.getAuthenticator();
  }

  public AuthenticatedMessage(AuthenticatedMessageStruct struct) {
    message = struct.getMessage();
    authenticator = struct.getAuthenticator();
  }

  public byte[] getMessage() {
    return message;
  }

  public byte[] getAuthenticator() {
    return authenticator;
  }

  public AuthenticatedMessageStruct getThriftStruct() {
    AuthenticatedMessageStruct struct = new AuthenticatedMessageStruct();
    struct.setMessage(message);
    struct.setAuthenticator(authenticator);
    return struct;
  }

  public byte[] serialise() {
    return Utils.serialise(getThriftStruct());
  }
}
