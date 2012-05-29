package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DbAccessWithPooling;

public class Login {
  /**
   * Look in the database ('users' table) to check if the couple 
   * 'login'/'password' exist, the password stored in the database is assumed 
   * to be encoded with the MD5 algorithm
   * @param login
   * @param password
   * @return the user_id if the couple login/password is found, -1 otherwise 
   */
  public static int checkPassword(String login, String password) {
    // acces database to check identifiers
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String query = "SELECT * FROM users WHERE login='" + login + "';";
    ResultSet rset = dbaccess.askResultSet(query);
    try  {
      rset.next();
      String pass = rset.getString("password");
      // is password ok ? 
      if (getMD5(password).equals(pass)) {
        return rset.getInt("user_id");
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      dbaccess.freeResultSet();
    }
    return -1;
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
