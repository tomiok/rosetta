package me.tomi.rosetta.hash;

public enum MessageDigestType {
  MD5("MD5"),
  SHA1("SHA-1"),
  SHA256("SHA-256");

  MessageDigestType(final String type) {
    this.type = type;
  }

  private String type;

  public String getType() {
    return type;
  }
}
