package me.tomi.rosetta.string;

public final class StringUtils {

  public static final String EMPTY = "";

  public static final String UNDERSCORE = "_";

  public static final String AT = "@";

  public static final String SLASH = "/";

  public static final String SPACE = " ";

  public static final String COMMA = ",";

  public static final String LEFT_P = "(";

  public static final String RIGHT_P = ")";

  private static final String IS_NUMERIC_REGEX = "[-+]?\\d*\\.?\\d+";

  private static final String WITHE_SPACE_REGEX = "\\s";

  public static void validateNullOrBlank(String s) {
    if (s == null) {
      throw new IllegalArgumentException("The argument is null");
    }

    if (s.trim().isEmpty()) {
      throw new IllegalArgumentException("The argument is blank");
    }
  }

  public static boolean isNumeric(String s) {
    return s.matches(IS_NUMERIC_REGEX);
  }

  public static String replaceSpaces(String s) {
    return s.replaceAll(WITHE_SPACE_REGEX, EMPTY);
  }
}
