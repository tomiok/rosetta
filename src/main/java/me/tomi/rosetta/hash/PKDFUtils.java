package me.tomi.rosetta.hash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

public class PKDFUtils {

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

  private static final String PKDF_KEY = "PBKDF2WithHmacSHA512";

  private String password;

  private int iterations;

  private int keyLength;

  private String salt;

  public static PKDFUtils password(String password) {
    PKDFUtils pdkf = new PKDFUtils();
    pdkf.password = password;
    return pdkf;
  }

  public PKDFUtils iterations(int iterations) {
    this.iterations = iterations;
    return this;
  }

  public PKDFUtils keyLength(int keyLength) {
    this.keyLength = keyLength;
    return this;
  }

  public PKDFUtils salt(String salt) {
    this.salt = salt;
    return this;
  }

  public String create() {

    String salted = this.salt != null ? salt : SALT;
    int its = this.iterations != 0 ? iterations : ITERATIONS;
    int key = this.keyLength != 0 ? keyLength : KEY_LENGTH;

    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance(PKDF_KEY);
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
}
