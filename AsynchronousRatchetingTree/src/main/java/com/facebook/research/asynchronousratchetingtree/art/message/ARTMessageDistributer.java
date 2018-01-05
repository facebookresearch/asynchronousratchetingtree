/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art.message;

import com.facebook.research.asynchronousratchetingtree.MessageDistributer;

public class ARTMessageDistributer implements MessageDistributer {
  private byte[] serialised;

  public ARTMessageDistributer(CiphertextMessage updateMessage) {
    serialised = updateMessage.serialise();
  }

  @Override
  public byte[] getUpdateMessageForParticipantNum(int participantNum) {
    return serialised;
  }

  @Override
  public int totalSize() {
    return serialised.length;
  }
}
