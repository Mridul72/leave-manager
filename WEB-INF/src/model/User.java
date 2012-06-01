package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DbAccessWithPooling;

public class User {
  int user_id = -1;
  String user_name = null;
  String surname = null;
  String gender = null;
  String email = null;
  Date taking_office = null;
  Date termination = null;
  int replacement = -1;
  int group = -1;
  int team = -1;
  
  public User() {}
  
  public User(String login) {
    DbAccessWithPooling dbaccess = null;
    
    try {
      dbaccess = new DbAccessWithPooling();
      String query = "SELECT * FROM users WHERE login='"+ login + "'";
      ResultSet rset = dbaccess.askResultSet(query);
      
      user_id = rset.getInt("user_id");
      user_name = rset.getString("user_name");
      surname = rset.getString("surname");
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
