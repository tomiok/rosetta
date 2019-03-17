package me.tomi.rosetta.operations.retry;

import java.util.concurrent.Callable;

public class Retry<T, R> {

  public void retry(int times, Runnable runnable,) {

  }

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

    /**
     * This flag is for the ops if should wait some time untile run the next iteration,
     * default value is {@code true}
     */
    private boolean shouldWait;

    /**
     * This is for how much time the next operation will wait, in milli seconds. Default value is 1 sec.
     */
    private long millis;

    private Waiting(final long millis, final boolean shouldWait) {
      this.millis = millis;
      this.shouldWait = shouldWait;
    }

    private Waiting() {
      this.millis = 1_000L;
      this.shouldWait = true;
    }

    public static Waiting create(long millis, boolean shouldWait) {
      return new Waiting(millis, shouldWait);
    }

    public static Waiting create() {
      return new Waiting();
    }
  }
}
