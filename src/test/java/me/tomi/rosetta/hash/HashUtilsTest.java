package me.tomi.rosetta.hash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class HashUtilsTest {

  @Test
  public void hash() {
    String s = "hello";
    String hash1 = HashUtils.hash(MessageDigestType.SHA1, s);
    String hash256 = HashUtils.hash(MessageDigestType.SHA256, s);
    String hash512 = HashUtils.hash(MessageDigestType.SHA512, s);

    assertFalse(hash1.equalsIgnoreCase(hash256));
    assertFalse(hash1.equalsIgnoreCase(hash512));
    assertFalse(hash256.equalsIgnoreCase(hash512));

    assertNotEquals(hash1.length(), hash256.length());
    assertNotEquals(hash1.length(), hash512.length());
    assertNotEquals(hash256.length(), hash512.length());
  }
}