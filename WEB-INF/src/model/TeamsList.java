package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DbAccessWithPooling;

public class TeamsList {
  
  private ArrayList<Team> teamslist = new ArrayList<Team>();
  
  public TeamsList() {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    try {
      SafeQuery query = new SafeQuery();
      query.setPreparedquery("SELECT * FROM teams ORDER by team_name;");
      ResultSet rset = dbaccess.askResultSet(query);
      while (rset.next()) {
        Team t = new Team();
        t.setId(rset.getInt("team_id"));
        t.setName(rset.getString("team_name"));
        t.setOptManagers();
        t.setReqManagers();
        teamslist.add(t);
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } finally {
      dbaccess.freeResultSet();
    }
  }
  
  public ArrayList<Team> getList() {
    return teamslist;
  }
  
  public int getSize() {
    return teamslist.size();
  }
  
  public Team getItem(int i) {
    return teamslist.get(i);
  }

  public Team getTeam(int id) {
    for (int i = 0 ; i < teamslist.size() ; i++) {
      if (teamslist.get(i).getId() == id) return teamslist.get(i);
    }
    return null;
  }
}
