/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.dhratchet.message;

import com.facebook.research.asynchronousratchetingtree.MessageDistributer;

public class DHRatchetMessageDistributer implements MessageDistributer {
  private byte[][] updateMessages;

  public DHRatchetMessageDistributer(byte[][] updateMessages) {
    this.updateMessages = updateMessages;
  }

  @Override
  public byte[] getUpdateMessageForParticipantNum(int participantNum) {
    return updateMessages[participantNum];
  }

  @Override
  public int totalSize() {
    int size = 0;
    for (int i = 0; i < updateMessages.length; i++) {
      if (updateMessages[i] == null) {
        continue;
      }
      size += updateMessages[i].length;
    }
    return size;
  }
}
