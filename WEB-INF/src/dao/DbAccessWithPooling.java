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
  private Connection conn = null;
  private Statement stmt = null;
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
   * SELECT cible FROM table WHERE source=valeur
   * @param table  the table in which to search information
   * @param cible  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param valeur the searched value in the source column
   * @return the data to obtain
   */
  public String askString(String table, String cible, String source, int valeur) {
    String query = "SELECT " + cible + " FROM " + table + " WHERE " + source + "='" + valeur + "'";
    return stringFromDb(query, cible);
  }
  /**
   * Get a String from the database with a query of type: 
   * SELECT cible FROM table WHERE source=valeur
   * @param table  the table in which to search information
   * @param cible  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param valeur the searched value in the source column
   * @return the data to obtain
   */
  public String askString(String table, String cible, String source, String valeur) {
    String query = "SELECT " + cible + " FROM " + table + " WHERE " + source + "='" + valeur + "'";
    return stringFromDb(query, cible);
  }

  private String stringFromDb(String query, String cible) {
    if (pool == null) getDataSource();
    String result = null;
    try {
      conn = pool.getConnection();
      stmt = conn.createStatement();
      rset = stmt.executeQuery(query);
      if (rset.next()) result = rset.getString(cible);
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
   * SELECT cible FROM table WHERE source=valeur
   * @param table  the table in which to search information
   * @param cible  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param valeur the searched value in the source column
   * @return the data to obtain
   */
  public int askInt(String table, String cible, String source, int valeur) {
    String query = "SELECT " + cible + " FROM " + table + " WHERE " + source + "='" + valeur + "'";
    return intFromDb(query, cible);
  }
  /**
   * Get an int from the database with a query of type: 
   * SELECT cible FROM table WHERE source=valeur
   * @param table  the table in which to search information
   * @param cible  the name of the column where is the data to obtain
   * @param source the name of the column containing the value to search
   * @param valeur the searched value in the source column
   * @return the data to obtain
   */
  public int askInt(String table, String cible, String source, String valeur) {
    String query = "SELECT " + cible + " FROM " + table + " WHERE " + source + "='" + valeur + "'";
    return intFromDb(query, cible);
  }

  public int intFromDb(String query, String cible) {
    if (pool == null) getDataSource();
    int result = -1;
    try {
      conn = pool.getConnection();
      stmt = conn.createStatement();
      rset = stmt.executeQuery(query);
      if (rset.next()) result = rset.getInt(cible);
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
  public ResultSet askResultSet(String query) {
    if (pool == null) getDataSource();
    try {
      conn = pool.getConnection();
      stmt = conn.createStatement();
      rset = stmt.executeQuery(query);
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
  public int executeQuery(String query) {
    if (pool == null) getDataSource();
    int nMaj = 0;
    try {
      conn = pool.getConnection();
      stmt = conn.createStatement();
      nMaj = stmt.executeUpdate(query);
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
    return nMaj;
  }
}
