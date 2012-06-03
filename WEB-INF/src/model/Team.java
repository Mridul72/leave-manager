package model;

import dao.DbAccessWithPooling;

public class Team {
  private int team_id = 1;
  private String team_name;
  private UsersList managerslist = new UsersList();
  
  public Team() {}
  
  public Team(int id) {
    team_id = id;
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    team_name = dbaccess.askString("teams", "team_name", "team_id", team_id);
    managerslist.addManagers(team_id);
  }
  
  public int getId() {
    return team_id;
  }
  
  public void setId(int team_id) {
    this.team_id = team_id;
  }
  
  public String getName() {
    return team_name;
  }
  
  public void setName(String team_name) {
    this.team_name = team_name;
  }
  
  public void setManagers() {
    managerslist = new UsersList();
    managerslist.addManagers(team_id);
  }
  
  public void setManagers(UsersList managers) {
    managerslist = managers;
  }
  
  public UsersList getManagers() {
    return managerslist;
  }
  

}
