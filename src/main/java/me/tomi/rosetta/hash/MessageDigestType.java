package me.tomi.rosetta.hash;

public enum MessageDigestType {
  MD5("MD5"),
  SHA1("SHA-1"),
  SHA256("SHA-256"),
  SHA512("SHA-512");

  private String type;

  MessageDigestType(final String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
