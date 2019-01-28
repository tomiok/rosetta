package me.tomi.rosetta.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import me.tomi.rosetta.Strings.StringUtils;

/**
 * Utility class with simple hash operations.
 * Supported algs:
 * <ul>
 *   <li>MD5</li>
 *   <li>SHA-1</li>
 *   <li>SHA-256</li>
 *   <li>SHA-512</li>
 * </ul>
 */
public final class HashUtils {

  private static final Logger log = Logger.getAnonymousLogger();

  private static final int ITERATIONS = 12345;

  private static Optional<MessageDigest> getDigestInstance(MessageDigestType type) {
    try {
      return Optional.of(MessageDigest.getInstance(type.getType()));
    } catch (NoSuchAlgorithmException e) {
      log.log(Level.SEVERE, e, () -> "Cannot create the algorithm");
      return Optional.empty();
    }
  }

  public static String hash(MessageDigestType type, String s) {
    Optional<MessageDigest> instanceOpt = getDigestInstance(type);

    if (instanceOpt.isPresent()) {
      MessageDigest md = instanceOpt.get();
      md.reset();
      md.update(s.getBytes(StandardCharsets.UTF_8));
      byte[] digest = md.digest();
      for (int i = 0; i < ITERATIONS; i++) {
        md.reset();
        md.update(digest);
        digest = md.digest();
      }
      return DatatypeConverter.printHexBinary(digest);
    }

    return StringUtils.EMPTY;
  }
}
/*
1: DA39A3EE5E6B4B0D3255BFEF95601890AFD80709
256: E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855
512: CF83E1357EEFB8BDF1542850D66D8007D620E4050B5715DC83F4A921D36CE9CE47D0D13C5D85F2B0FF8318D2877EEC2F63B931BD47417A81A538327AF927DA3E
 */