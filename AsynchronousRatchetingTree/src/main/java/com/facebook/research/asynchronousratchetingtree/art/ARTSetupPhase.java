/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.art;

import com.facebook.research.asynchronousratchetingtree.GroupMessagingSetupPhase;
import com.facebook.research.asynchronousratchetingtree.GroupMessagingTestImplementation;
import com.facebook.research.asynchronousratchetingtree.KeyServer;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public class ARTSetupPhase implements GroupMessagingSetupPhase<ARTState> {
  private byte[] setupMessage;
  private int bytesReceivedByOthers = 0;

  @Override
  public void generateNecessaryPreKeys(ARTState[] states) {
    // We only need PreKeys for the initiator.
    for (int i = 1; i < states.length; i++) {
      states[i].getSignedDHPreKeyFor(0);
    }
  }

  @Override
  public void setupInitiator(GroupMessagingTestImplementation<ARTState> implementation, ARTState[] states, DHPubKey[] identities, KeyServer keyServer) {
    int n = states.length;

    // Setup message is the same for all peers, so let's just do it once.
    setupMessage = implementation.setupMessageForPeer(
      states[0],
      identities,
      keyServer,
      1
    );
  }

  @Override
  public int getBytesSentByInitiator() {
    return setupMessage.length;
  }

  @Override
  public void setupAllOthers(GroupMessagingTestImplementation<ARTState> implementation, ARTState[] states, Integer[] active, DHPubKey[] identities, KeyServer keyServer) {
    for (int i = 0; i < active.length; i++) {
      int which = active[i];
      if (which == 0) { // Don't import the setup message to the initiator itself.
        continue;
      }
      bytesReceivedByOthers += setupMessage.length;
      implementation.processSetupMessage(states[which], setupMessage, which);
    }
  }

  @Override
  public int getBytesSentByOthers() {
    return 0;
  }

  @Override
  public int getBytesReceivedByOthers() {
    return bytesReceivedByOthers;
  }
}
