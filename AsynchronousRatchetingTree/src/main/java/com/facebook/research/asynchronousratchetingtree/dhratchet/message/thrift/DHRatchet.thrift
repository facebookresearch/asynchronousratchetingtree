# Copyright (c) 2017-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#
# Compile this file using the Thrift compiler, from the current directory, with the following command:
# thrift -r --gen java -out ../../../../../../../ DHRatchet.thrift

namespace java com.facebook.research.asynchronousratchetingtree.dhratchet.message.thrift

struct DHRatchetSetupMessageStruct {
  1: i32 peerNum,
  2: binary identityKey,
  3: binary ephemeralKey,
}

struct DHRatchetMessageStruct {
  1: i32 peerNum,
  2: binary ratchetKey,
  3: binary ciphertext,
}
