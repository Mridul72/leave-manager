package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DbAccessWithPooling;

public class UserRights {
  private int user_id;
  private ArrayList<Integer> rights = new ArrayList<Integer>();
  
  /**
   * Create an Object which contains the different rights of a user in form of
   * as numeric integers identifiers
   * @param user_id the user identifier
   * @throws UnknownUserError if this user id doesn't exist
   */
  public UserRights(int id) {
    this.user_id = id;
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String query = "SELECT * FROM users_rights WHERE user_sid='"+ user_id + "';";
    ResultSet rset = dbaccess.askResultSet(query);
    try {
      while(rset.next()) {
        rights.add(new Integer(rset.getInt("right_sid")));
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } finally {
      dbaccess.freeResultSet();
    }
  }
  /**
   * Test if the object UserRights contains this right_id (if the database has
   * been altered in the meantime, this value does not reflect its real state)
   * @param right_id the id of the right to add
   * @return         true if correctly added, false if rights_id doesn't exist
   *                 or in case of failure
   */
  public boolean hasRight(int right_id) {
    if (rights.contains(right_id)) return true;
    return false;
  }
  /**
   * Add the right to the user, alter database
   * @param right_id the id of the right to add
   * @return         true if correctly added, false if rights_id doesn't exist
   *                 or in case of failure
   */
  public boolean addRight(int right_id) throws UnknownUserError {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    if (dbaccess.askInt("rights", "right_id", "right_id", right_id) < 0 ||
        dbaccess.askString("users", "login", "user_id", user_id) == null) {
      return false;
    } else if (dbaccess.askInt("users_rights", "right_sid", "user_sid", user_id) < 0) {
      String query = "INSERT INTO users_rights " +
                     "VALUES (" + user_id + "," + right_id + ");";
      if (dbaccess.executeQuery(query) == 0) return false;
      else rights.add(right_id);
    }
    return true;
  }
  /**
   * Remove the right to the user, alter database
   * @param right_id the id of the right to remove
   * @return         true if correctly removed, false if rights_id doesn't exist
   *                 or in case of failure
   */
  public boolean removeRight(int right_id) {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String query = "DELETE * INTO users_rights WHERE user_sid=" + user_id + "AND right_sid=" + right_id + ";";
    if (dbaccess.executeQuery(query) == 0) return false;
    return true;
  }
  /**
   * Get the memorized user id
   * @return the user id
   */
  public int getUserId() {
    return user_id;
  }
}
