package me.tomi.rosetta.hash;

import org.junit.Test;

public class HashUtilsTest {

  @Test
  public void hash() {
    String s = "hello";
    String hash1 = HashUtils.hash(MessageDigestType.SHA1, s);
    String hash256 = HashUtils.hash(MessageDigestType.SHA256, s);
    String hash512 = HashUtils.hash(MessageDigestType.SHA512, s);

    System.out.println("1: " + hash1);
    System.out.println("256: " + hash256);
    System.out.println("512: " + hash512);
  }
}