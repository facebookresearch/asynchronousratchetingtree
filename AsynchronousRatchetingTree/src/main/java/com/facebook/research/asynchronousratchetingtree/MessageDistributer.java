/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

public interface MessageDistributer {
  public byte[] getUpdateMessageForParticipantNum(int participantNum);
  public int totalSize();
}
