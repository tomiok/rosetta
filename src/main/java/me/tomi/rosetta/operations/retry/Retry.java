package me.tomi.rosetta.operations.retry;

import java.util.concurrent.Callable;

public class Retry<T, R> {

  public T retry(int times, Callable<T> callable, boolean keepGoing, Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.shouldWait) {
          Thread.sleep(waiting.millis);
        }
        return callable.call();
      } catch (Exception ex) {
        if (i == times - 1 && !keepGoing) {
          throw new RuntimeException(ex.getMessage());
        }
      }
    }

    throw new RuntimeException("Callable fails due to unknown error");
  }

  public static class Waiting {

    private long millis;
    private boolean shouldWait;

    private Waiting(final long millis, final boolean shouldWait) {
      this.millis = millis;
      this.shouldWait = shouldWait;
    }

    private Waiting() {
      this.millis = 0L;
      this.shouldWait = false;
    }

    public static Waiting create(long millis, boolean shouldWait) {
      return new Waiting(millis, shouldWait);
    }

    public static Waiting create() {
      return new Waiting();
    }
  }
}
