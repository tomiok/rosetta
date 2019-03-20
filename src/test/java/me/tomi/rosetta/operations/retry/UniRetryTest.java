package me.tomi.rosetta.operations.retry;

import java.util.Optional;
import java.util.function.Supplier;
import me.tomi.rosetta.operations.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UniRetryTest {

  @Test
  public void withSupplier() {

    Optional<Integer> res = UniRetry.<Integer>create()
        .withSupplier(2, () -> 9, true, Utils.Waiting.create());

    int i = res.orElseThrow(RuntimeException::new);
    Assert.assertEquals(9, i);
  }

  @Test(expected = RuntimeException.class)
  public void testExceptionInRetry() {
    Supplier<Integer> supplier = () -> {
      throw new RuntimeException("Failed!");
    };
    UniRetry.<Integer>create()
        .withSupplier(2, supplier, false, Utils.Waiting.create());
  }
}