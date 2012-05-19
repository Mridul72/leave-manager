package dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbAccessWithPooling {

  private DataSource pool;  // Database connection pool
  
  public void getDataSource() {
    try {
      InitialContext initial = new InitialContext();
      pool = (DataSource) initial.lookup("java:comp/env/jdbc/leaveManagerDB");
      if (pool == null)
         throw new NamingException("Unknown DataSource 'jdbc/leaveManagerDB'");
    } catch (NamingException ex) {
      System.err.println(ex.getMessage());
    }
  }
  
  public String getPassword(String login) {
    if (pool == null) getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rset = null; 
    String query = "SELECT password FROM users WHERE login='" + login + "'";
    String password = new String("");
    try {
      conn = pool.getConnection();
      stmt = conn.createStatement();
      rset = stmt.executeQuery(query);
      rset.next();
      password = rset.getString("password");
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } catch (NullPointerException ex) {
      System.err.println("echec : pas de connexion");
    } finally {
      try {
        if (stmt != null) stmt.close();
        if (conn != null) conn.close(); // return to pool
      } catch (SQLException ex) {
        System.err.println(ex.getMessage());
      }
    }
    
    return password;
  }
  

}
