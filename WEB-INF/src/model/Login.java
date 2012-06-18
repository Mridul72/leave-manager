package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DbAccessWithPooling;
import dao.SafeQuery;

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
    SafeQuery query = new SafeQuery();
    query.setPreparedQuery("SELECT * FROM users WHERE login = ? ;");
    query.addArgument(login);
    ResultSet rset = dbaccess.askResultSet(query);
    try {
      if (rset.isBeforeFirst()) rset.next();
      else return -1;
      String pass = rset.getString("password");
      // is password ok ? 
      if (getMD5(password).equals(pass)) {
        return rset.getInt("user_id");
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
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
    /* inspired by http://respawner.fr/blog/index.php?post/2008/09/03/Generation-d-un-MD5-avec-Java */
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
