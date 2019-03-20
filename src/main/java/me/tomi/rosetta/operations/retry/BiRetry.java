package me.tomi.rosetta.operations.retry;

import java.util.Optional;
import java.util.function.Function;
import me.tomi.rosetta.operations.Utils;

/**
 * @param <T> The input type.
 * @param <R> The return type
 */
public class BiRetry<T, R> {

  public static <T, R> BiRetry<T, R> create() {
    return new BiRetry<>();
  }

  public Optional<R> withFunction(int times, Function<T, R> function, boolean keepGoing, T t, Utils.Waiting w) {
    return this.retry(times, function, keepGoing, t, w);
  }

  private Optional<R> retry(int times, Function<T, R> function, boolean keepGoing, T t, Utils.Waiting waiting) {
    for (int i = 0; i < times; i++) {
      try {
        if (i != 0 && waiting.isShouldWait()) {
          Thread.sleep(waiting.getMillis());
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
}
