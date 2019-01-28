package me.tomi.rosetta.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HashUtils {

  private static final Logger log = Logger.getAnonymousLogger();

  public static Optional<MessageDigest> getDigestInstance(MessageDigestType type) {
    try {
      return Optional.of(MessageDigest.getInstance(type.getType()));
    } catch (NoSuchAlgorithmException e) {
      log.log(Level.SEVERE, e, () -> "Cannot create the algorithm");
      return Optional.empty();
    }
  }
}
