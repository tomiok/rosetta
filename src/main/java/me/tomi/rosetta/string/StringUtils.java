package me.tomi.rosetta.string;

public final class StringUtils {

  public static final String EMPTY = "";

  public static final String UNDERSCORE = "_";

  public static final String AT = "@";

  public static final String SLASH = "@";

  public static final String SPACE = " ";

  public static void isNullOrBlank(String s) {
    if (s == null) {
      throw new IllegalArgumentException("The argument is null");
    }

    String actual = s.trim();
    if (actual.isEmpty()) {
      throw new IllegalArgumentException("The argument is blank");
    }
  }
}
