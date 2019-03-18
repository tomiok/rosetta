package me.tomi.rosetta.operations.retry;

import java.util.Optional;
import org.junit.Test;

public class RetryTest {

  @Test
  public void withSupplier() {
    Optional<Integer> p = Retry.<Integer, Integer>create().withSupplier(
        1, () -> 9, false, Retry.Waiting.create());
  }
}