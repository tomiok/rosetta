package me.tomi.rosetta.operations.retry;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import me.tomi.rosetta.operations.Utils;

/**
 * Retry-able operation with {@link Supplier} or a {@link Callable}
 *
 * @param <T> The type to be returned by the supplier or the callable
 */
public class UniRetry<T> {

  public static <T> UniRetry<T> create() {
    return new UniRetry<>();
  }

  public Optional<T> withSupplier(int times, Supplier<T> supplier, boolean keepGoing, Utils.Waiting waiting) {
    return retry(times, supplier, keepGoing, waiting);
  }

  private Optional<T> retry(int times, Supplier<T> supplier, boolean keepGoing, Utils.Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.isShouldWait()) {
          Thread.sleep(waiting.getMillis());
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

  private Optional<T> retry(int times, Callable<T> callable, boolean keepGoing, Utils.Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.isShouldWait()) {
          Thread.sleep(waiting.getMillis());
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
}
