# Using pooling with mySQL Database ans Tomcat #

To have a clean code and separate the interaction with the database, I created a dedicated class that provides generic methods to obtain data. The private method getDataSource's role is to retrieve a connection from the pool.

For each type of query, I created a special method which requires a connection, build the query and retrieves the result before returning it. For the method to execute various queries, to keep an additional level of security, I created a SafeQuery  object containing the PreparedStatement and a list of arguments to insert into it. The method can thus perform the construction phase of the query at the last moment and make sure that there will be no risk of SQL injection.

It is of course necessary to release the connection when the ResultSet is no longer used, so I created a method freeResultSet which must be called after the use of a method returning a ResultSet.

```
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

  public void freeResultSet() {
    try {
      if (stmt != null) stmt.close();
      if (conn != null) conn.close(); // return to pool
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }
}
```