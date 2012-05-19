package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dao.DbAccessWithPooling;

public class User {
  
  public static boolean checkPassword(String login, String password) {
    // acces database to check identifiers
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String pass = dbaccess.getPassword(login);
    // if login/pass found, return true
    if (getMD5(password).equals(pass)) return true;
    else return false;
  }
  
  /**
   * Give MD5 encoding of a string.
   *
   * Use {@link #getMD5(String)} to encode.
   *
   * @param password string to be encoded
   * @return         the encoded string as a string
   */
  private static String getMD5(String password) {
    byte[] uniqueKey = password.getBytes();
    byte[] hash      = null;
    
    try {
      hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
    } catch (NoSuchAlgorithmException e) {
      throw new Error("No MD5 support in this VM.");
    }
    
    StringBuilder hashString = new StringBuilder();
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(hash[i]);
      if (hex.length() == 1) {
        hashString.append('0');
        hashString.append(hex.charAt(hex.length() - 1));
      } else hashString.append(hex.substring(hex.length() - 2));
    }
    
    return hashString.toString();
  }
  
}
