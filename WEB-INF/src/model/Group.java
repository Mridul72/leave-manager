package model;

import dao.DbAccessWithPooling;

public class Group {
  private int group_id;
  private String group_name;
  
  public Group() {
    group_id = 1;
    group_name = "Default";
  }
  
  public Group(int id, String name) {
    group_id = id;
    group_name = name;
  }
  
  public Group(String name) {
    group_id = 0;
    group_name = name;
  }
  
  public int getId() {
    return group_id;
  }
  
  public static int getId(String name) {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    int id = dbaccess.askInt("Groups", "group_id", "group_name", name);
    return id;
  }

  public String getName() {
    return group_name;
  }
  
  public static String getName(int id) {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String name = dbaccess.askString("Groups", "group_name", "group_id", id);
    return name;
  }
  
  public void update(String group_name) {
    this.group_name = group_name;
    
  }
}
