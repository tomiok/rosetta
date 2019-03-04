package me.tomi.rosetta.Strings;

import me.tomi.rosetta.string.StringUtils;
import org.junit.Test;

public class StringUtilsTest {

  @Test(expected = IllegalArgumentException.class)
  public void testBlank() {
    String s = "   ";
    StringUtils.validateNullOrBlank(s);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    String s = null;
    StringUtils.validateNullOrBlank(s);
  }
}
