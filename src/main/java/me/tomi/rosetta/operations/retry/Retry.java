package me.tomi.rosetta.operations.retry;

import me.tomi.rosetta.operations.Utils;

/**
 * Retry-able class that accept a {@link Runnable} to do the job.
 */
public class Retry {

  public static Retry create() {
    return new Retry();
  }

  public void withRunnable(int times, Runnable r, boolean keepGoing, Utils.Waiting w) {
    this.retry(times, r, keepGoing, w);
  }

  private void retry(int times, Runnable runnable, boolean keepGoing, Utils.Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.isShouldWait()) {
          Thread.sleep(waiting.getMillis());
        }
        runnable.run();
      } catch (Exception ex) {
        if (i == times - 1 && !keepGoing) {
          throw new RuntimeException(ex.getMessage());
        }
      }
    }
  }
}
