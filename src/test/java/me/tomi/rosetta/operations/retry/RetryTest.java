package me.tomi.rosetta.operations.retry;

import static me.tomi.rosetta.operations.retry.Retry.create;

import org.junit.Test;

public class RetryTest {

  @Test
  public void withSupplier() {
    Retry<String, String> r = Retry.create();
  }
}