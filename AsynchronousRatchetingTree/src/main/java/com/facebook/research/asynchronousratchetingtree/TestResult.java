/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

public class TestResult {
  private String type;
  private int groupSize;
  private int activeUsers;
  private long initiatorSetupTime;
  private int initiatorSetupBytes;
  private long othersSetupTime;
  private int othersSetupBytesSent;
  private int othersSetupBytesReceived;
  private long sendingTime;
  private long receivingTime;
  private int bytesSent;
  private int bytesReceived;

  public TestResult(String type, int groupSize, int activeUsers) {
    this.type = type;
    this.groupSize = groupSize;
    this.activeUsers = activeUsers;
  }

  public void setInitiatorSetupTime(long initiatorSetupTime) {
    this.initiatorSetupTime = initiatorSetupTime;
  }

  public void setInitiatorSetupBytes(int initiatorSetupBytes) {
    this.initiatorSetupBytes = initiatorSetupBytes;
  }

  public void setOthersSetupTime(long othersSetupTime) {
    this.othersSetupTime = othersSetupTime;
  }

  public void setOthersSetupBytesSent(int othersSetupBytesSent) {
    this.othersSetupBytesSent = othersSetupBytesSent;
  }

  public void setOthersSetupBytesReceived(int othersSetupBytesReceived) {
    this.othersSetupBytesReceived = othersSetupBytesReceived;
  }

  public void setSendingTime(long sendingTime) {
    this.sendingTime = sendingTime;
  }

  public void setReceivingTime(long receivingTime) {
    this.receivingTime = receivingTime;
  }

  public void setBytesSent(int bytesSent) {
    this.bytesSent = bytesSent;
  }

  public void setBytesReceived(int bytesReceived) {
    this.bytesReceived = bytesReceived;
  }

  public static void outputHeaderLine() {
    String[] columns = new String[] {
      "type",
      "participants",
      "active_participants",
      "init_setup_time",
      "init_setup_bytes",
      "others_setup_time",
      "others_setup_bytes_sent",
      "others_setup_bytes_received",
      "send_time",
      "receive_time",
      "bytes_sent",
      "bytes_received"
    };
    System.out.println(String.join(",", columns));
  }

  public void output() {
    String[] columns = new String[] {
      type,
      String.valueOf(groupSize),
      String.valueOf(activeUsers),
      String.valueOf(initiatorSetupTime),
      String.valueOf(initiatorSetupBytes),
      String.valueOf(othersSetupTime),
      String.valueOf(othersSetupBytesSent),
      String.valueOf(othersSetupBytesReceived),
      String.valueOf(sendingTime),
      String.valueOf(receivingTime),
      String.valueOf(bytesSent),
      String.valueOf(bytesReceived)
    };
    System.out.println(String.join(",", columns));
  }
}
