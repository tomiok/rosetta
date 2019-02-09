package me.tomi.rosetta.string.query;


import me.tomi.rosetta.string.StringUtils;

public final class Expression {

  private static final String EQUALS = "=";

  private static final String PERCENTAGE = "%";

  private static final String LIKE = "like";

  private String val;

  /**
   * The SQL expression: Like(like %v%), equals (=), greater than (>), less than (<)
   * greater or equals(>=), less or equals(<=)
   * begin like(%v), end like( like v%)
   */
  private String exp;

  private Expression(final String val, final String exp) {
    this.val = val;
    this.exp = exp;
  }

  public static Expression isEqualsTo(final String value) {
    return new Expression(value, EQUALS);
  }

  public static Expression isEqualsTo(final Number value) {
    return new Expression(value.toString(), EQUALS);
  }

  public static Expression like(final String target) {
    String val = String.format("%s%s%s", PERCENTAGE, target, PERCENTAGE);
    return new Expression(val, LIKE);
  }

  @Override
  public String toString() {
    return StringUtils.SPACE  + exp + StringUtils.SPACE + this.val;
  }
}
