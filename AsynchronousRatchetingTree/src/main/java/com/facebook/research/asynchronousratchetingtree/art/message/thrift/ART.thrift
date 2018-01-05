# Copyright (c) 2017-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#
# Compile this file using the Thrift compiler, from the current directory, with the following command:
# thrift -r --gen java -out ../../../../../../../ ART.thrift

namespace java com.facebook.research.asynchronousratchetingtree.art.message.thrift

struct SetupMessageStruct {
  1: i32 leafNum,
  2: list<string> identities,
  3: map<i32, string> ephemeralKeys,
  4: binary keyExchangeKey,
  5: NodeStruct tree,
}

struct NodeStruct {
  1: binary publicKey,
  2: optional NodeStruct left,
  3: optional NodeStruct right,
}

struct UpdateMessageStruct {
  1: i32 leafNum,
  2: list<string> path,
}

struct CiphertextMessageStruct {
  1: AuthenticatedMessageStruct authenticatedMessage,
  2: binary ciphertext,
}

struct AuthenticatedMessageStruct {
  1: binary message,
  2: binary authenticator,
}
