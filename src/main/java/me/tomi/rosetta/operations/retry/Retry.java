package me.tomi.rosetta.operations.retry;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public class Retry<T, R> {

  public static <T, R> Retry<T, R> create() {
    return new Retry<>();
  }

  public Optional<R> withSupplier(int times, Supplier<R> supplier, boolean keepGoing, Waiting waiting) {
    return retry(times, supplier, keepGoing, waiting);
  }

  private Optional<R> retry(int times, Supplier<R> supplier, boolean keepGoing, Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.shouldWait) {
          Thread.sleep(waiting.millis);
        }
        return Optional.of(supplier.get());
      } catch (Exception ex) {
        if (i == times - 1 && !keepGoing) {
          throw new RuntimeException(ex.getMessage());
        }
      }
    }

    return Optional.empty();
  }

  private Optional<R> retry(int times, Function<T, R> function, boolean keepGoing, T t, Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.shouldWait) {
          Thread.sleep(waiting.millis);
        }
        return Optional.of(function.apply(t));
      } catch (Exception ex) {
        if (i == times - 1 && !keepGoing) {
          throw new RuntimeException(ex.getMessage());
        }
      }
    }

    return Optional.empty();
  }

  private void retry(int times, Runnable runnable, boolean keepGoing, Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.shouldWait) {
          Thread.sleep(waiting.millis);
        }
        runnable.run();
      } catch (Exception ex) {
        if (i == times - 1 && !keepGoing) {
          throw new RuntimeException(ex.getMessage());
        }
      }
    }
  }

  private Optional<T> retry(int times, Callable<T> callable, boolean keepGoing, Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.shouldWait) {
          Thread.sleep(waiting.millis);
        }
        return Optional.of(callable.call());
      } catch (Exception ex) {
        if (i == times - 1 && !keepGoing) {
          throw new RuntimeException(ex.getMessage());
        }
      }
    }

    return Optional.empty();
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
