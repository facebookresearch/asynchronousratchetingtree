/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

import com.facebook.research.asynchronousratchetingtree.art.ARTTestImplementation;
import com.facebook.research.asynchronousratchetingtree.art.ARTSetupPhase;
import com.facebook.research.asynchronousratchetingtree.art.ARTState;
import com.facebook.research.asynchronousratchetingtree.crypto.DHPubKey;
import com.facebook.research.asynchronousratchetingtree.dhratchet.DHRatchet;
import com.facebook.research.asynchronousratchetingtree.dhratchet.DHRatchetSetupPhase;
import com.facebook.research.asynchronousratchetingtree.dhratchet.DHRatchetState;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import javax.crypto.Cipher;

public class Main {
  private static boolean debug = true;

  public static void main(String[] args) {
    checkForUnlimitedStrengthCrypto();

    // Run a test run first to warm up the JIT
    artTestRun(8, 8);
    dhTestRun(8, 8);

    List<TestResult> artResults = new LinkedList<>();
    List<TestResult> dhResults = new LinkedList<>();

    int test_size_limit = 1000;
    double multiplier = 1.5;

    // First run ART tests. Warm up the JIT with a few smallish values, and then start recording results.
    for (int i = 2; i < 20; i += 2) {
      artTestRun(i, i);
    }
    int lastNumber = 1;
    for (double i = 2.0; i <= test_size_limit; i *= multiplier) {
      int n = (int) i;
      if (n == lastNumber) {
        continue;
      }
      lastNumber = n;
      System.gc();
      artResults.add(artTestRun(n, n));
    }

    // Now run DH tests. Again, warm up the JIT with a few smallish values, and then start recording results.
    for (int i = 2; i < 20; i += 2) {
      dhTestRun(i, i);
    }
    lastNumber = 1;
    for (double i = 2.0; i <= test_size_limit; i *= multiplier) {
      int n = (int) i;
      if (n == lastNumber) {
        continue;
      }
      lastNumber = n;
      System.gc();
      dhResults.add(dhTestRun(n, n));
    }

    TestResult.outputHeaderLine();
    Iterator<TestResult> artIterator = artResults.iterator();
    while (artIterator.hasNext()) {
      TestResult r = artIterator.next();
      r.output();
    }

    Iterator<TestResult> dhIterator = dhResults.iterator();
    while (dhIterator.hasNext()) {
      TestResult r = dhIterator.next();
      r.output();
    }
  }

  private static TestResult artTestRun(int n, int activePeers) {
    ARTState[] states = new ARTState[n];

    for (int i = 0; i < n; i++) {
      states[i] = new ARTState(i, n);
    }

    return testRun(
      n,
      activePeers,
      states,
      new ARTSetupPhase(),
      new ARTTestImplementation()
    );
  }

  private static void checkForUnlimitedStrengthCrypto() {
    boolean hasEnoughCrypto = true;
    try {
      if (Cipher.getMaxAllowedKeyLength("AES") < 256) {
        hasEnoughCrypto = false;
      }
    } catch (NoSuchAlgorithmException e) {
      hasEnoughCrypto = false;
    }
    if (!hasEnoughCrypto) {
      Utils.except("Your JRE does not support unlimited-strength " +
		   "cryptography and thus cannot run ART. See README.md " +
		   "for Java Cryptography Extension (JCE) Unlimited " +
		   "Strength Jurisdiction Policy Files installation " +
		   "instructions.");
    }
  }

  private static TestResult dhTestRun(int n, int activePeers) {
    DHRatchetState[] states = new DHRatchetState[n];

    for (int i = 0; i < n; i++) {
      states[i] = new DHRatchetState(i, n);
    }

    return testRun(
      n,
      activePeers,
      states,
      new DHRatchetSetupPhase(),
      new DHRatchet()
    );
  }

  private static <TState extends GroupMessagingState> TestResult testRun(
    int n,
    int activeCount,
    TState[] states,
    GroupMessagingSetupPhase<TState> setupPhase,
    GroupMessagingTestImplementation<TState> implementation
  ) {
    String testName = implementation.getClass().getSimpleName();
    if (debug) Utils.print("\nStarting " + testName + " test run with " + n + " participants, of which " + activeCount  + " active.\n");
    TestResult result = new TestResult(testName, n, activeCount);

    if (debug) Utils.print("Creating random messages and senders.");
    SecureRandom random = new SecureRandom();
    Set<Integer> activeSet = new HashSet<>();
    activeSet.add(0); // We always want the initiator in the set.
    while (activeSet.size() < activeCount) {
      activeSet.add(random.nextInt(n));
    }
    Integer[] active = activeSet.toArray(new Integer[0]);

    int messagesToSend = 100;
    int messageLength = 32; // Message length 32 bytes, so we can compare as if we were just sending a symmetric key.
    int[] messageSenders = new int[messagesToSend];
    byte[][] messages = new byte[messagesToSend][messageLength];
    for (int i = 0; i < messagesToSend; i++) {
      // We're only interested in ratcheting events, so senders should always be
      // different from the previous sender.
      messageSenders[i] = active[random.nextInt(activeCount)];
      while (i != 0 && messageSenders[i] == messageSenders[i-1]) {
        messageSenders[i] = active[random.nextInt(activeCount)];
      }
      random.nextBytes(messages[i]);
    }


    // Create the necessary setup tooling in advance.
    DHPubKey[] identities = new DHPubKey[n];
    for (int i = 0; i < n ; i++) {
      identities[i] = states[i].getIdentityKeyPair().getPubKey();
    }
    KeyServer keyServer = new KeyServer(states);

    // Create and cache the necessary PreKeys in advance.
    setupPhase.generateNecessaryPreKeys(states);

    // We'll need two timers; as some interleaving events are counted.
    Stopwatch stopwatch1 = new Stopwatch();
    Stopwatch stopwatch2 = new Stopwatch();

    if (debug) Utils.print("Setting up session for initiator.");
    stopwatch1.startInterval();
    setupPhase.setupInitiator(implementation, states, identities, keyServer);
    stopwatch1.endInterval();
    if (debug) Utils.print("Took " + stopwatch1.getTotal() + " nanoseconds.");
    if (debug) Utils.print("Initiator sent " + setupPhase.getBytesSentByInitiator() + " bytes.");
    result.setInitiatorSetupTime(stopwatch1.getTotal());
    result.setInitiatorSetupBytes(setupPhase.getBytesSentByInitiator());
    stopwatch1.reset();

    if (debug) Utils.print("Setting up session for " + activeCount + " peers.");
    stopwatch1.startInterval();
    setupPhase.setupAllOthers(implementation, states, active, identities, keyServer);
    stopwatch1.endInterval();
    if (debug) Utils.print("Took " + stopwatch1.getTotal() + " nanoseconds.");
    if (debug) Utils.print("Others received " + setupPhase.getBytesReceivedByOthers() + " bytes.");
    if (debug) Utils.print("Others sent " + setupPhase.getBytesSentByOthers() + " bytes.");
    result.setOthersSetupTime(stopwatch1.getTotal());
    result.setOthersSetupBytesReceived(setupPhase.getBytesReceivedByOthers());
    result.setOthersSetupBytesSent(setupPhase.getBytesSentByOthers());
    stopwatch1.reset();

    // Use stopwatch1 for sender, stopwatch2 for receiving.
    if (debug) Utils.print("Sending " + messagesToSend + " messages.");
    int totalSendSizes = 0;
    int totalReceiveSizes = 0;
    for (int i = 0; i < messagesToSend; i++) {
      int sender = messageSenders[i];
      byte[] message = messages[i];
      stopwatch1.startInterval();
      MessageDistributer messageDistributer = implementation.sendMessage(states[sender], message);
      stopwatch1.endInterval();

      totalSendSizes += messageDistributer.totalSize();

      for (int j = 0; j < activeCount; j++) {
        int receiver = active[j];
        if (receiver == sender) {
          continue;
        }
        byte[] received = messageDistributer.getUpdateMessageForParticipantNum(receiver);
        totalReceiveSizes += received.length;
        stopwatch2.startInterval();
        byte[] decrypted = implementation.receiveMessage(states[receiver], received);
        stopwatch2.endInterval();
        if (!Arrays.equals(message, decrypted)) {
          Utils.except("Message doesn't match.");
        }
      }
    }
    if (debug) Utils.print("Total sending time " + stopwatch1.getTotal() + " nanoseconds.");
    if (debug) Utils.print("Total receiving time " + stopwatch2.getTotal() + " nanoseconds.");
    if (debug) Utils.print("Total bytes sent: " + totalSendSizes + ".");
    if (debug) Utils.print("Total bytes received: " + totalReceiveSizes + ".");
    result.setSendingTime(stopwatch1.getTotal());
    result.setReceivingTime(stopwatch2.getTotal());
    result.setBytesSent(totalSendSizes);
    result.setBytesReceived(totalReceiveSizes);

    if (debug) Utils.print("Ended test  run with " + n + " participants.\n---------------\n");

    return result;
  }
}
