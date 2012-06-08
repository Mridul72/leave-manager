package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DbAccessWithPooling;

public class UsersList {
  
  private ArrayList<User> userslist = new ArrayList<User>();
  
  public UsersList() {}
  
  public void addUser(User u) {
    userslist.add(u);
  }
  
  public void addAll() {
    Date now = new Date(System.currentTimeMillis());
    String q = "SELECT U.*, T.team_name, G.group_name, R.user_sid_replace " +
                 "FROM users U " +
                   "LEFT OUTER JOIN teams T " +
                     "ON U.team_sid = T.team_id " +
                   "LEFT OUTER JOIN groups G " +
                     "ON U.group_sid = G.group_id " +
                   "LEFT OUTER JOIN replacements R " +
                     "ON R.user_sid_leave = U.user_id " +
                     "AND R.date_from < '" + now + "' " +
                     "AND date_to > '" + now + "' " +
                "ORDER BY U.user_name, U.surname;";
    SafeQuery query = new SafeQuery();
    query.setPreparedquery(q);
    add(query);
  }
  
  public void addOptManagers(int team_id) {
    addManagers(team_id, 'O');
  }
  
  public void addReqManagers(int team_id) {
    addManagers(team_id, 'R');
  }
  
  private void addManagers(int team_id, char status) {
    Date now = new Date(System.currentTimeMillis());
    String q = "SELECT U.*, T.team_name, G.group_name, R.user_sid_replace " +
                 "FROM users U " +
                   "LEFT OUTER JOIN teams T " +
                     "ON U.team_sid = T.team_id " +
                   "LEFT OUTER JOIN groups G " +
                     "ON U.group_sid = G.group_id " +
                   "LEFT OUTER JOIN replacements R " +
                     "ON R.user_sid_leave = U.user_id " +
                     "AND R.date_from < '" + now + "' " +
                     "AND date_to > '" + now + "' " +
                 "WHERE U.user_id IN " +
                   "(SELECT user_sid FROM manage " +
                     "WHERE team_sid = ? AND validation = '" + status + "') " +
                "ORDER BY U.user_name, U.surname;";
    SafeQuery query = new SafeQuery();
    query.setPreparedquery(q);
    query.addArgument(team_id);
    add(query);
  }
  
  private void add(SafeQuery query) {
    DbAccessWithPooling dbaccess = null;
    try {
      dbaccess = new DbAccessWithPooling();
      ResultSet rset = dbaccess.askResultSet(query);
      while (rset.next()) {
        User u = new User();
        u.setId(rset.getInt("user_id"));
        u.setName(rset.getString("user_name"));
        u.setSurname(rset.getString("surname"));
        u.setGender(rset.getString("gender"));
        u.setEmail(rset.getString("email"));
        u.setTakingOffice(rset.getDate("taking_office"));
        u.setTermination(rset.getDate("termination"));
        u.setReplacement(rset.getInt("user_sid_replace"));
        u.setGroupId(rset.getInt("group_sid"));
        u.setGroupName(rset.getString("group_name"));
        u.setTeamId(rset.getInt("team_sid"));
        u.setTeamName(rset.getString("team_name"));
        userslist.add(u);
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } finally {
      dbaccess.freeResultSet();
    }
  }
  
  public ArrayList<User> getList() {
    return userslist;
  }
  
  public int getSize() {
    return userslist.size();
  }
  
  public User getItem(int i) {
    return userslist.get(i);
  }
  
  public User getUser(int id) {
    for (int i = 0 ; i < userslist.size() ; i++) {
      if (userslist.get(i).getId() == id) return userslist.get(i);
    }
    return null;
  }
}
