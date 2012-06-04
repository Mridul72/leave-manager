package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DbAccessWithPooling;

public class User {
  int user_id = 0;
  String user_name = null;
  String surname = null;
  String gender = null;
  String email = null;
  Date taking_office = null;
  Date termination = null;
  int replacement = 0;
  int group_id = 0;
  String group_name = null;
  int team_id = 0;
  String team_name = null;
  
  public User() {}
  
  public User(String login) {
    DbAccessWithPooling dbaccess = null;
    Date now = new Date(System.currentTimeMillis());
    try {
      dbaccess = new DbAccessWithPooling();
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
                     " WHERE U.login = ?;";
      SafeQuery query = new SafeQuery();
      query.setPreparedquery(q);
      query.addArgument(login);
      ResultSet rset = dbaccess.askResultSet(query);
      rset.next();
      user_id = rset.getInt("user_id");
      user_name = rset.getString("user_name");
      surname = rset.getString("surname");
      gender = rset.getString("gender");
      email = rset.getString("email");
      taking_office = rset.getDate("taking_office");
      termination = rset.getDate("termination");
      replacement = rset.getInt("user_sid_replace");
      group_id = rset.getInt("group_sid");
      group_name = rset.getString("group_name");
      team_id = rset.getInt("team_sid");
      team_name = rset.getString("team_name");
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } finally {
      dbaccess.freeResultSet();
    }
  }
  
//  public User(String id, String name, String surname, String gender,
//              String email, Date taking_office, Date termination, 
//              int replacement, int group, int team) {
//    
//  }
  

  public int getId() {
    return user_id;
  }

  public void setId(int user_id) {
    this.user_id = user_id;
  }

  public String getName() {
    return user_name;
  }

  public void setName(String user_name) {
    this.user_name = user_name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getTakingOffice() {
    return taking_office;
  }

  public void setTakingOffice(Date taking_office) {
    this.taking_office = taking_office;
  }

  public Date getTermination() {
    return termination;
  }

  public void setTermination(Date termination) {
    this.termination = termination;
  }

  public int getReplacement() {
    return replacement;
  }

  public void setReplacement(int replacement) {
    this.replacement = replacement;
  }

  public int getGroupId() {
    return group_id;
  }

  public void setGroupId(int group_id) {
    this.group_id = group_id;
  }

  public String getGroupName() {
    return group_name;
  }

  public void setGroupName(String group_name) {
    this.group_name = group_name;
  }

  public int getTeamId() {
    return team_id;
  }

  public void setTeamId(int team_id) {
    this.team_id = team_id;
  }

  public String getTeamName() {
    return team_name;
  }

  public void setTeamName(String team_name) {
    this.team_name = team_name;
  }

}
