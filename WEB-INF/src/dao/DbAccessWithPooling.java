package dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.SafeQuery;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbAccessWithPooling {

  private DataSource pool;  // Database connection pool
  private Connection conn = null;
  private PreparedStatement stmt = null;
  private ResultSet rset = null; 
  /**
   * Place into the pool attribute a new DataSource from the pool
   */
  private void getDataSource() {
    try {
      InitialContext initial = new InitialContext();
      pool = (DataSource) initial.lookup("java:comp/env/jdbc/leaveManagerDB");
      if (pool == null)
         throw new NamingException("Unknown DataSource 'jdbc/leaveManagerDB'");
    } catch (NamingException ex) {
      System.err.println(ex.getMessage());
    }
  }
  /**
   * Get a String from the database with a query of type: 
   * SELECT target FROM table WHERE source=value
   * @param table  the table in which to search information
   * @param target  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param value the searched value in the source column
   * @return the data to obtain
   */
  public String askString(String table, String target, String source, int val) {
    return askString(table, target, source, String.valueOf(val));
  }
  /**
   * Get a String from the database with a query of type: 
   * SELECT target FROM table WHERE source=value
   * @param table  the table in which to search information
   * @param target  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param value the searched value in the source column
   * @return the data to obtain
   */
  public String askString(String tab, String target, String source, String val){
    if (pool == null) getDataSource();
    String result = null;
    try {
      conn = pool.getConnection();
      stmt = conn.prepareStatement("SELECT ? FROM ? WHERE ? = ?");
      stmt.setString(1, target); 
      stmt.setString(2, tab);
      stmt.setString(3, source);
      stmt.setString(4, val);
      rset = stmt.executeQuery();
      if (rset.next()) result = rset.getString(target);
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
    return result;
  }
  /**
   * Get an int from the database with a query of type: 
   * SELECT target FROM table WHERE source=value
   * @param table  the table in which to search information
   * @param target  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param value the searched value in the source column
   * @return the data to obtain
   */
  public int askInt(String table, String target, String source, int value) {
    return askInt(table, target, source, String.valueOf(value));
  }
  /**
   * Get an int from the database with a query of type: 
   * SELECT target FROM table WHERE source=value
   * @param table  the table in which to search information
   * @param target  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param value the searched value in the source column
   * @return the data to obtain
   */
  public int askInt(String table, String target, String source, String value) {
    if (pool == null) getDataSource();
    int result = 0;
    try {
      conn = pool.getConnection();
      stmt = conn.prepareStatement("SELECT ? FROM ? WHERE ? = ?");
      stmt.setString(1, target);
      stmt.setString(2, table);
      stmt.setString(3, source);
      stmt.setString(4, value);
      rset = stmt.executeQuery();
      if (rset.next()) result = rset.getInt(target);
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
    return result;
  }
  /**
   * Performs a query in the database and returns the result<br>
   * <strong>Warning ! You need to free the resultSet after use</strong>
   * @see {@link #freeResultSet()}
   * @param query a SQL query
   * @return a ResultSet (never null). Use askResultSet(query).isBeforeFirst()
   * to know if the returned ResultSet is empty
   */
  public ResultSet askResultSet(SafeQuery query) {
    if (pool == null) getDataSource();
    try {
      conn = pool.getConnection();
      stmt = conn.prepareStatement(query.getPreparedquery());
      for(int i = 0 ; i < query.getSize() ; i++) {
        if (query.getDataType(i).equals("String"))
          stmt.setString(i+1, (String) query.getData(i));
        else if (query.getDataType(i).equals("int"))
          stmt.setInt(i+1, (Integer) query.getData(i));
        else if (query.getDataType(i).equals("Date"))
          stmt.setDate(i+1, (Date) query.getData(i));
      }
      rset = stmt.executeQuery();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    } catch (NullPointerException ex) {
      System.err.println("echec : pas de connexion");
    }
    return rset;
  }
  /**
   * Free the resultSet after a call to {@link #askResultSet(String)}
   */
  public void freeResultSet() {
    try {
      if (stmt != null) stmt.close();
      if (conn != null) conn.close(); // return to pool
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }
  /**
   * Executes the given SQL query, which may be an INSERT, UPDATE, or DELETE 
   * query or an SQL query that returns nothing, such as an SQL DDL query.
   * @param query the SQL query
   * @return either (1) the row count for SQL Data Manipulation Language (DML) 
   *         statements or (2) 0 for SQL statements that return nothing
   */
  public int executeQuery(SafeQuery query, Boolean withkey) {
    if (pool == null) getDataSource();
    int key = 0;
    int nMaj = 0;
    try {
      conn = pool.getConnection();
      stmt = conn.prepareStatement(query.getPreparedquery());
      for(int i = 0 ; i < query.getSize() ; i++) {
        if (query.getDataType(i).equals("String"))
          stmt.setString(i+1, (String) query.getData(i));
        else if (query.getDataType(i).equals("int"))
          stmt.setInt(i+1, (Integer) query.getData(i));
        else if (query.getDataType(i).equals("Date"))
          stmt.setDate(i+1, (Date) query.getData(i));
      }
      nMaj = stmt.executeUpdate();
      rset = stmt.getGeneratedKeys();
      if (withkey && rset != null) {
        rset.next();
        key = rset.getInt(1);
      }
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
    if (withkey) return key;
    else return nMaj;
  }
  
  public int executeQuery(SafeQuery query) {
    return executeQuery(query, false);
  }
  
}
