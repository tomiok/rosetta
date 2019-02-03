package me.tomi.rosetta.hash;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDFUtils {

  public static final int DEFAULT_SALT = 24;

  public static final int DEFAULT_ITERATIONS = 65000;

  public static final int DEFAULT_KEY_LENGTH = 512;

  private static final String PBKDF_KEY = "PBKDF2WithHmacSHA512";

  private static final String COLON = ":";

  private String password;

  private int iterations;

  private int keyLength;

  private int salt;

  private String passToValidate;

  private String hashToValidate;

  private boolean isValidation;

  private byte[] saltForValidation;

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

  public PBKDFUtils salt(int salt) {
    this.salt = salt;
    return this;
  }

  public PBKDFUtils salt(byte[] salt) {
    // flag in true
    this.isValidation = true;
    this.saltForValidation = salt;
    return this;
  }

  public String create() {

    int salted = this.salt != 0 ? salt : DEFAULT_SALT;
    int its = this.iterations != 0 ? iterations : DEFAULT_ITERATIONS;
    int key = this.keyLength != 0 ? keyLength : DEFAULT_KEY_LENGTH;
    byte[] r;

    if (!isValidation) {
      SecureRandom random = new SecureRandom();
      r = new byte[salted];
      random.nextBytes(r);
    } else {
      r = this.saltForValidation;
    }

    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF_KEY);
      PBEKeySpec spec = new PBEKeySpec(
          password.toCharArray(),
          r,
          its,
          key);
      SecretKey secretKey = skf.generateSecret(spec);
      return toHex(secretKey.getEncoded())
             + ":"
             + toHex(r);

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
  public boolean doValidate(int iterations, int keyLength) {
    // Decode the hash into its parameters
    String[] passWithSaltArray = this.hashToValidate.split(COLON);
    String hashForValidation = passWithSaltArray[0];
    String salted = passWithSaltArray[1];

    // Compute the hash of the provided password, using the same salt,
    // iteration count, and hash length
    String testHash =
        PBKDFUtils
            .password(this.passToValidate)
            .salt(fromHex(salted))
            .iterations(iterations)
            .keyLength(keyLength)
            .create();

    //split the hash and the salt in different Strings
    String actualHash = testHash.split(COLON)[0];

    // Compare the hashes in constant time. The password is correct if
    // both hashes match.
    byte[] actual = fromHex(actualHash);
    byte[] compare = fromHex(hashForValidation);
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

  /**
   * Converts a byte array into a hexadecimal string.
   *
   * @param array the byte array to convert
   *
   * @return a length*2 character string encoding the byte array
   */
  private static String toHex(byte[] array) {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    if (paddingLength > 0) {
      return String.format("%0" + paddingLength + "d", 0) + hex;
    } else {
      return hex;
    }
  }
}
