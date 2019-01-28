package me.tomi.rosetta.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import me.tomi.rosetta.Strings.StringUtils;

public final class HashUtils {

  private static final Logger log = Logger.getAnonymousLogger();

  private static Optional<MessageDigest> getDigestInstance(MessageDigestType type) {
    try {
      return Optional.of(MessageDigest.getInstance(type.getType()));
    } catch (NoSuchAlgorithmException e) {
      log.log(Level.SEVERE, e, () -> "Cannot create the algorithm");
      return Optional.empty();
    }
  }

  public String hash(MessageDigestType type, String s) {
    Optional<MessageDigest> instanceOpt = getDigestInstance(type);

    if (instanceOpt.isPresent()) {
      MessageDigest md = instanceOpt.get();
      md.update(s.getBytes());
      return DatatypeConverter.printHexBinary(md.digest());
    }
    
    return StringUtils.EMPTY;
  }
}
