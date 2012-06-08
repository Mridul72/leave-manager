package model;

import dao.DbAccessWithPooling;

public class Team {
  private int team_id = 1;
  private String team_name;
  private UsersList optmanagerslist = new UsersList();
  private UsersList reqmanagerslist = new UsersList();
  
  public Team() {}
  
  public Team(int id) {
    team_id = id;
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    team_name = dbaccess.askString("teams", "team_name", "team_id", team_id);
    optmanagerslist.addOptManagers(team_id);
    reqmanagerslist.addReqManagers(team_id);
  }
  
  public void addTeamIntoDB() {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    SafeQuery query = new SafeQuery();
    query.setPreparedquery("INSERT INTO teams VALUES (null, ?);");
    query.addArgument(team_name);
    team_id = dbaccess.executeQuery(query, true);
    addManagerIntoDB(query, dbaccess);
  }
  
  public void updateTeamIntoDB() {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    SafeQuery query = new SafeQuery();
    query.setPreparedquery("UPDATE teams SET team_name = ? WHERE team_id = ?;");
    query.addArgument(team_name);
    query.addArgument(team_id);
    dbaccess.executeQuery(query);
    query.reset();
    query.setPreparedquery("DELETE FROM manage WHERE team_sid = ?;");
    query.addArgument(team_id);
    dbaccess.executeQuery(query);
    addManagerIntoDB(query, dbaccess);
  }
  
  private void addManagerIntoDB(SafeQuery query, DbAccessWithPooling dbaccess) {
    int user_id;
    for (int i = 0 ; i < optmanagerslist.getSize() ; i++) {
      user_id = optmanagerslist.getItem(i).getId();
      query.reset();
      query.setPreparedquery("INSERT INTO manage VALUES ( ? , ?, 'O' );");
      query.addArgument(user_id);
      query.addArgument(team_id);
      dbaccess.executeQuery(query);
    }
    for (int i = 0 ; i < reqmanagerslist.getSize() ; i++) {
      user_id = reqmanagerslist.getItem(i).getId();
      query.reset();
      query.setPreparedquery("INSERT INTO manage VALUES ( ? , ?, 'R' );");
      query.addArgument(user_id);
      query.addArgument(team_id);
      dbaccess.executeQuery(query);
    }
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
  
  public void setOptManagers() {
    optmanagerslist = new UsersList();
    optmanagerslist.addOptManagers(team_id);
  }
  
  public void setOptManagers(UsersList managers) {
    optmanagerslist = managers;
  }
  
  public UsersList getOptManagers() {
    return optmanagerslist;
  }
  
  public void setReqManagers() {
    reqmanagerslist = new UsersList();
    reqmanagerslist.addReqManagers(team_id);
  }
  
  public void setReqManagers(UsersList managers) {
    reqmanagerslist = managers;
  }

  public UsersList getReqManagers() {
    return reqmanagerslist;
  }

  public static void delete(int team_id) {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    SafeQuery query = new SafeQuery();
    query.setPreparedquery("DELETE FROM teams WHERE team_id = ? ;");
    query.addArgument(team_id);
    dbaccess.executeQuery(query);
    query.setPreparedquery("DELETE FROM manage WHERE team_sid = ? ;");
    dbaccess.executeQuery(query);
  }
}
