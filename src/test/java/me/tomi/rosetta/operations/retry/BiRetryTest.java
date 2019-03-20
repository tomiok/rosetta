package me.tomi.rosetta.operations.retry;

import java.util.Optional;
import java.util.function.Function;
import me.tomi.rosetta.operations.Utils;
import org.junit.Assert;
import org.junit.Test;

public class BiRetryTest {

  @Test
  public void withSupplier() {
    Optional<Integer> p = BiRetry.<Integer, Integer>create().withFunction(
        1, r -> r + 1, true, 9, Utils.Waiting.create());

    int result = p.get();
    Assert.assertEquals(10, result);
  }

  @Test
  public void shouldFail() {
    Function<Integer, Integer> f = r -> {
      throw new RuntimeException("failed dude");
    };

    Optional<Integer> p = BiRetry.<Integer, Integer>create().withFunction(
        3, f, true, 9, Utils.Waiting.create());

    Assert.assertFalse(p.isPresent());
  }
}