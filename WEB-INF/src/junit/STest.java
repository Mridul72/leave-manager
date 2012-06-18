package junit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DbAccessWithPooling;
import dao.SafeQuery;

import model.Login;


public class STest extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    // Login
    testEqual("test Login admin/admin", out,
        Login.checkPassword("admin", "admin") == 1);
    testEqual("test Login myrtlem/myrtlem", out,
        Login.checkPassword("myrtlem", "myrtlem") == 6);
    testEqual("test Login admin/truc", out,
        Login.checkPassword("admin", "truc") == -1);
    // DbAccessWithPooling
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    testEqual("test DbAccessWithPooling askString from int", out,
        dbaccess.askString("users", "login", "user_id", 4).equals("hermioneg"));
    testEqual("test DbAccessWithPooling askString from string", out,
        dbaccess.askString("users", "user_name", "login", "hermioneg").equals("Granger"));
    testEqual("test DbAccessWithPooling askInt from int", out,
        dbaccess.askInt("manage", "user_sid", "team_sid", 1) == 1);
    testEqual("test DbAccessWithPooling askInt from string", out,
        dbaccess.askInt("users", "user_id", "login", "hermioneg") == 4);
    // SafeQuery
    SafeQuery query = new SafeQuery();
    query.setPreparedQuery("test");
    testEqual("test SafeQuery set/get PreparedQuery", out,
        query.getPreparedQuery().equals("test"));
    query.addArgument(10);
    query.addArgument("chaine");
    Date date = new Date(100);
    query.addArgument(date);
    testEqual("test SafeQuery getDataType int", out,
        query.getDataType(0).equals("int"));
    testEqual("test SafeQuery getDataType String", out,
        query.getDataType(1).equals("String"));
    testEqual("test SafeQuery getDataType Date", out,
        query.getDataType(2).equals("Date"));
    testEqual("test SafeQuery getData (int)", out,
        query.getData(0).toString().equals("10"));
    testEqual("test SafeQuery getData (String)", out,
        query.getData(1).toString().equals("chaine"));
    testEqual("test SafeQuery getData (Date)", out,
        query.getData(2).toString().equals("1970-01-01"));
    testEqual("test SafeQuery size", out,
        query.getSize() == 3);
    
    query.reset();
    testEqual("test SafeQuery reset size", out,
        query.getSize() == 0);
    testEqual("test SafeQuery reset getDataType", out,
        query.getDataType(0) == null);
    testEqual("test SafeQuery reset getData", out,
        query.getData(0) == null);
    // DbAccessWithPooling
    query.setPreparedQuery("SELECT * FROM users WHERE user_id = ? ");
    query.addArgument(1);
    ResultSet rset = dbaccess.askResultSet(query);
    try {
      rset.next();
      testEqual("test DbAccessWithPooling askResultSet", out,
          rset.getString("login").equals("admin"));
      dbaccess.freeResultSet();
      testEqual("test DbAccessWithPooling freeResultSet", out,
          rset.isClosed());
    } catch (SQLException ex) {
      out.println("<font color='red'>" + ex.getMessage() + "</font><br />");
    }
    
    query.reset();
    query.setPreparedQuery("INSERT INTO teams VALUES (null, ?);");
    query.addArgument("équipe de test");
    int i = dbaccess.executeQuery(query, true);
    testEqual("test DbAccessWithPooling executeQuery (true)", out, i > 1);
    
    query.reset();
    query.setPreparedQuery("UPDATE teams SET team_name = ? WHERE team_id = ?;");
    query.addArgument("équipe à évaluer");
    query.addArgument(i);
    testEqual("test DbAccessWithPooling executeQuery (false)", out,
        dbaccess.executeQuery(query, false) == 1);
    
    query.reset();
    query.setPreparedQuery("DELETE FROM teams WHERE team_id = ?;");
    query.addArgument(i);
    testEqual("test DbAccessWithPooling executeQuery ()", out,
        dbaccess.executeQuery(query) == 1);
    query.reset();
    
    //Team
    

  }

  void testEqual(String message, PrintWriter out, boolean test) {
    out.print(message + " : ");
    if (test) out.println ("ok<br />");
    else  out.println ("<font color='red'>echec</font><br />");
  }
  
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
  
    doGet(req, resp);
  }
}