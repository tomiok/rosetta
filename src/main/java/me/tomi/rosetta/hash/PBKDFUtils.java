package me.tomi.rosetta.hash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public class PBKDFUtils {

  private static final String SALT =
      "TjojSxJZlro64CJISc2vcDJzjHBisf+7owg0eBWgDgtWP7ybSdz/tqV2AelN1F49q3jBD404MWp4Dua3x"
      + "+8rZsAYSX9A18mz80BAeXB42FUl1cwGbHysCNfI4Ua+KBk392G9UyuOUHDB9kIr3H0TJwzT7LeVinaYhwAzWeOq"
      + "/QlsUZmZlPsEzjkyvIYlejlOj24jScl7c+iqDlhPP6pysPjFr5DceGaX16Tb44SQ"
      + "/1rtBGdg0pFWvJY73VQW59zT8Medk3LtQHLJTDw2dOA4LF9bSRX3717sAXT67jbbmih5qmgrKFhP3T6PmjjFapH7GVx"
      + "+T7HlQIPXX1SeaDXTCPmjJzpLOtb88pibd8pktWxVIEQR+JpH4pJJ8IuKCJZo0hC1"
      + "/nRmXyPaxKcXHVEhp0PZgL6q9Apb1ivQO3P70FEOP9yMdolLT3w3qokvH/RD9kQdg8MUhJiQymQdnrIcknLVsWsF4qhLzIj"
      + "/5OO6nRRauiWnnShsmdgYa6D6aMujnadWmGciP8BeAJ5hFnDD5ikgbUyMbFdvCZ3"
      + "/kdSuAWMiaBp6oiqdpP7RFAEXlnP8K1yDuPGYEQ5DRgXVd82rIhHHPPMg7Ga6EjqNvJ81QDUSt80WE"
      + "/Q9E2aPXnvXJKA2fgZSwyqiFt6tLRMr03xR0JnaqjolpiU39YmMT8to9ac=";

  private static final int ITERATIONS = 65000;

  private static final int KEY_LENGTH = 512;

  private static final String PBKDF_KEY = "PBKDF2WithHmacSHA512";

  private String password;

  private int iterations;

  private int keyLength;

  private String salt;

  private String passToValidate;

  private String hashToValidate;

  public static PBKDFUtils password(String password) {
    PBKDFUtils pdkf = new PBKDFUtils();
    pdkf.password = password;
    return pdkf;
  }

  public PBKDFUtils iterations(int iterations) {
    this.iterations = iterations;
    return this;
  }

  public PBKDFUtils keyLength(int keyLength) {
    this.keyLength = keyLength;
    return this;
  }

  public PBKDFUtils salt(String salt) {
    this.salt = salt;
    return this;
  }

  public String create() {

    String salted = this.salt != null ? salt : SALT;
    int its = this.iterations != 0 ? iterations : ITERATIONS;
    int key = this.keyLength != 0 ? keyLength : KEY_LENGTH;

    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF_KEY);
      PBEKeySpec spec = new PBEKeySpec(
          password.toCharArray(),
          salted.getBytes(),
          its,
          key);
      SecretKey secretKey = skf.generateSecret(spec);
      return DatatypeConverter.printHexBinary(secretKey.getEncoded());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  public static PBKDFUtils validatePassword(String password, String goodHash) {
    PBKDFUtils u = new PBKDFUtils();
    u.passToValidate = password;
    u.hashToValidate = goodHash;
    return u;
  }

  /**
   * Validates a password using a hash.
   *
   * @return true if the password is correct, false if not
   */
  public boolean doValidate() {
// Decode the hash into its parameters
    int its = this.iterations != 0 ? iterations : ITERATIONS;
    int key = this.keyLength != 0 ? keyLength : KEY_LENGTH;
    String salted = this.salt != null ? this.salt : SALT;

// Compute the hash of the provided password, using the same salt,
// iteration count, and hash len1gth
    String testHash =
        PBKDFUtils
            .password(this.passToValidate)
            .salt(salted)
            .iterations(its)
            .keyLength(key)
            .create();


// Compare the hashes in constant time. The password is correct if
// both hashes match.
    byte[] actual = fromHex(testHash);
    byte[] compare = fromHex(hashToValidate);
    return slowEquals(actual, compare);
  }

  /**
   * Compares two byte arrays in length-constant time. This comparison method
   * is used so that password hashes cannot be extracted from an on-line
   * system using a timing attack and then attacked off-line.
   *
   * @param a the first byte array
   * @param b the second byte array
   *
   * @return true if both byte arrays are the same, false if not
   */
  private static boolean slowEquals(byte[] a, byte[] b) {
    int diff = a.length ^ b.length;
    for (int i = 0; i < a.length && i < b.length; i++) {
      diff |= a[i] ^ b[i];
    }
    return diff == 0;
  }

  private static byte[] fromHex(String hex) {
    byte[] binary = new byte[hex.length() / 2];
    for (int i = 0; i < binary.length; i++) {
      binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return binary;
  }
}
