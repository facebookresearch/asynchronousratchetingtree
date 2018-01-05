/**
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
*/

package com.facebook.research.asynchronousratchetingtree;

public class Stopwatch {
  private long nanos;
  private long intervalStart;

  public Stopwatch() {
    reset();
  }

  public void startInterval() {
    intervalStart = System.nanoTime();
  }

  public void endInterval() {
    long intervalEnd = System.nanoTime();
    nanos += intervalEnd - intervalStart;
  }

  public long getTotal() {
    return nanos;
  }

  public void reset() {
    nanos = 0;
    intervalStart = -1;
  }
}
