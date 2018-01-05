/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree.dhratchet;

import com.facebook.research.asynchronousratchetingtree.GroupMessagingSetupPhase;
import com.facebook.research.asynchronousratchetingtree.GroupMessagingTestImplementation;
import com.facebook.research.asynchronousratchetingtree.KeyServer;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;

public class DHRatchetSetupPhase implements GroupMessagingSetupPhase<DHRatchetState> {
  private byte[][] setupMessages;
  private int bytesReceivedByOthers = 0;
  private int bytesSentByOthers = 0;

  @Override
  public void generateNecessaryPreKeys(DHRatchetState[] states) {
    // We need PreKeys from each user to each user with a lower ID than them.
    for (int i = 0; i < states.length; i++) {
      for (int j = i + 1; j < states.length; j++) {
        states[j].getSignedDHPreKeyFor(i);
      }
    }
  }

  @Override
  public void setupInitiator(GroupMessagingTestImplementation<DHRatchetState> implementation, DHRatchetState[] states, DHPubKey[] identities, KeyServer keyServer) {
    int n = states.length;
    setupMessages = new byte[n][];
    for (int i = 1; i < n; i++) {
      setupMessages[i] = implementation.setupMessageForPeer(states[0], identities, keyServer, i);
    }
  }

  @Override
  public int getBytesSentByInitiator() {
    int total = 0;
    for (int i = 1; i < setupMessages.length; i++) {
      total += setupMessages[i].length;
    }
    return total;
  }

  /**
   * Here we aim to scale the total number of key exchanges performed proportionate to the number of active users.
   * This involves ensuring that every active user has done a key exchange for each of the others, and that they have
   * all done an unreciprocated key exchange with all other users.
   */
  @Override
  public void setupAllOthers(GroupMessagingTestImplementation<DHRatchetState> implementation, DHRatchetState[] states, Integer[] active, DHPubKey[] identities, KeyServer keyServer) {
    int n = states.length;

    // We need all active users to create and import each others' setup messages.
    for (int i = 0; i < active.length; i++) {
      int sender = active[i];
      if (sender == 0) {
        continue;
      }
      // First let's import the initiator's state.
      bytesReceivedByOthers += setupMessages[sender].length;
      implementation.processSetupMessage(states[sender], setupMessages[sender], sender);

      for (int j = i + 1; j < active.length; j++) {
        int receiver = active[j];
        if (receiver == 0) {
          continue;
        }
        byte[] setupMessage = implementation.setupMessageForPeer(states[sender], identities, keyServer, receiver);
        bytesSentByOthers += setupMessage.length;
        bytesReceivedByOthers += setupMessage.length;
        implementation.processSetupMessage(states[receiver], setupMessage, receiver);
      }
    }

    // Active users should still create state for inactive ones, it just shouldn't be imported.
    for (int i = 0; i < active.length; i++) {
      int sender = active[i];
      for (int j = 0; j < n; j++) {
        if (states[sender].getIsSetup(j)) {
          continue;
        }
        byte[] setupMessage = implementation.setupMessageForPeer(states[sender], identities, keyServer, j);
        bytesSentByOthers += setupMessage.length;
        bytesReceivedByOthers += setupMessage.length;
        implementation.processSetupMessage(states[j], setupMessage, j);
      }
    }
  }

  @Override
  public int getBytesSentByOthers() {
    return bytesSentByOthers;
  }

  @Override
  public int getBytesReceivedByOthers() {
    return bytesReceivedByOthers;
  }
}
