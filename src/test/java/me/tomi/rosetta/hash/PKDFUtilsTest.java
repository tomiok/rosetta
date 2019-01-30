package me.tomi.rosetta.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PKDFUtilsTest {

  @Test
  public void create() {
    String pass = "welcome";
    String hash = PBKDFUtils.password(pass).create();

    assertFalse(hash.isEmpty());
  }

  @Test
  public void createWithIterations() {
    String pass = "welcome";
    int iterations = 9000;
    String hash = PBKDFUtils.password(pass).iterations(iterations).create();

    assertFalse(hash.isEmpty());

    String withoutIterations = PBKDFUtils.password(pass).create();

    assertFalse(hash.equalsIgnoreCase(withoutIterations));
    assertEquals(hash.length(), withoutIterations.length());

    String withSalt = PBKDFUtils.password(pass).salt("SALTY").create();

    assertFalse(withSalt.isEmpty());
    assertEquals(withoutIterations.length(), withSalt.length());

    String withKeyLength = PBKDFUtils.password(pass).keyLength(1024).create();

    assertFalse(withKeyLength.isEmpty());
    assertNotEquals(withKeyLength.length(), hash.length());
  }

  @Test
  public void verify() {
    String pass = "welcome";
    String hash = PBKDFUtils.password(pass).create();
    System.out.println(hash);
  }
}