/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

import com.facebook.research.asynchronousratchetingtree.crypto.SignedDHPubKey;

final public class KeyServer {
  GroupMessagingState[] states;

  public KeyServer(GroupMessagingState[] states) {
    this.states = states;
  }

  public SignedDHPubKey getSignedPreKey(GroupMessagingState state, int peerNum) {
    return states[peerNum].getSignedDHPreKeyFor(state.getPeerNum());
  }
}
