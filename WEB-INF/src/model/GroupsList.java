package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DbAccessWithPooling;

public class GroupsList {
  
  private ArrayList<Group> groupslist = new ArrayList<Group>();
  
  public GroupsList() {
    DbAccessWithPooling dbaccess = null;
    try {
      dbaccess = new DbAccessWithPooling();
      SafeQuery query = new SafeQuery();
      query.setPreparedquery("SELECT * FROM groups;");
      ResultSet rset = dbaccess.askResultSet(query);
      while (rset.next()) {
        Group g = new Group();
        g.setId(rset.getInt("group_id"));
        g.setName(rset.getString("group_name"));
        groupslist.add(g);
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } finally {
      dbaccess.freeResultSet();
    }
  }
  
  public ArrayList<Group> getList() {
    return groupslist;
  }

  public Group getGroup(int id) {
    for (int i = 0 ; i < groupslist.size() ; i++) {
      if (groupslist.get(i).getId() == id) return groupslist.get(i);
    }
    return null;
  }
}
