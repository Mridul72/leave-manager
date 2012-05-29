package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DbAccessWithPooling;

public class User {
  String user_id;
  String user_name;
  String user_surname;
  String gender;
  String email;
  Date taking_office;
  Date termination;
  int replacement;
  int group;
  int team;
  
  public User(String login) throws SQLException {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String query = "SELECT * FROM users WHERE login='"+ login + "'";
    ResultSet rset = dbaccess.askResultSet(query);
    try {
      user_id = rset.getString("user_id");
      user_name = rset.getString("user_name");
      user_surname = rset.getString("surname");
      gender = rset.getString("gender");
      email = rset.getString("email");
      taking_office = rset.getDate("taking_office");
      termination = rset.getDate("termination");
      replacement = rset.getInt("replacement_sid");
      group = rset.getInt("group_sid");
      team = rset.getInt("team_sid");
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } finally {
      dbaccess.freeResultSet();
    }
  }
  
  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getUser_surname() {
    return user_surname;
  }

  public void setUser_surname(String user_surname) {
    this.user_surname = user_surname;
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

  public Date getTaking_office() {
    return taking_office;
  }

  public void setTaking_office(Date taking_office) {
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

  public int getGroup() {
    return group;
  }

  public void setGroup(int group) {
    this.group = group;
  }

  public int getTeam() {
    return team;
  }

  public void setTeam(int team) {
    this.team = team;
  }

}
