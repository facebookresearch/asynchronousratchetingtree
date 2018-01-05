/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public interface GroupMessagingTestImplementation<TThreadState extends GroupMessagingState> {
  byte[] setupMessageForPeer(TThreadState state, DHPubKey[] peers, KeyServer keyServer, int peer);
  void processSetupMessage(TThreadState state, byte[] serialisedMessage, int participantNum);
  MessageDistributer sendMessage(TThreadState state, byte[] plaintext);
  byte[] receiveMessage(TThreadState state, byte[] serialisedMessage);
}
