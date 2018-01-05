/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public interface GroupMessagingSetupPhase<TState extends GroupMessagingState> {
  void generateNecessaryPreKeys(TState[] states);
  void setupInitiator(GroupMessagingTestImplementation<TState> implementation, TState[] states, DHPubKey[] identities, KeyServer keyServer);
  int getBytesSentByInitiator();
  void setupAllOthers(GroupMessagingTestImplementation<TState> implementation, TState[] states, Integer[] active, DHPubKey[] identities, KeyServer keyServer);
  int getBytesSentByOthers();
  int getBytesReceivedByOthers();
}
