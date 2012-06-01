package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DbAccessWithPooling;

public class UsersList {
  private ArrayList<User> userslist = new ArrayList<User>();
  
  public UsersList() {
    DbAccessWithPooling dbaccess = null;
    try {
      dbaccess = new DbAccessWithPooling();
      String query = "SELECT * FROM users;";
      ResultSet rset = dbaccess.askResultSet(query);
      while (rset.next()) {
        User u = new User();
        u.setId(rset.getInt("user_id"));
        u.setName(rset.getString("user_name"));
        u.setSurname(rset.getString("surname"));
        u.setGender(rset.getString("gender"));
        u.setEmail(rset.getString("email"));
        u.setTaking_office(rset.getDate("taking_office"));
        u.setTermination(rset.getDate("termination"));
        u.setReplacement(rset.getInt("replacement_sid"));
        u.setGroup(rset.getInt("group_sid"));
        u.setTeam(rset.getInt("team_sid"));
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

  public User getUser(int id) {
    for (int i = 0 ; i < userslist.size() ; i++) {
      if (userslist.get(i).getId() == id) return userslist.get(i);
    }
    return null;
  }
}
