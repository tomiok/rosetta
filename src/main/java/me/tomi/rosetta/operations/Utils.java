package me.tomi.rosetta.operations;

public class Utils {

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

    public boolean isShouldWait() {
      return shouldWait;
    }

    public long getMillis() {
      return millis;
    }
  }
}
