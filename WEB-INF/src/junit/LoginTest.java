package junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;
import model.Login;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoginTest extends TestCase {
  
  @BeforeClass
  public static void setUpClass() throws Exception {
      // https://blogs.oracle.com/randystuph/entry/injecting_jndi_datasources_for_junit
      // rcarver - setup the jndi context and the datasource
      try {
          // Create initial context
          System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
              "org.apache.naming.java.javaURLContextFactory");
          System.setProperty(Context.URL_PKG_PREFIXES, 
              "org.apache.naming");            
          InitialContext ic = new InitialContext();

          ic.createSubcontext("java:");
          ic.createSubcontext("java:/comp");
          ic.createSubcontext("java:/comp/env");
          ic.createSubcontext("java:/comp/env/jdbc");
         
          // Construct DataSource
          Connection c = null;
          String DBurl = "jdbc:mysql://127.0.0.1/leaveManagerDB";
          Properties p = new Properties();
          p.put("user","alinehuf");
          p.put("password","secret");
          c = DriverManager.getConnection(DBurl, p);
          
          
//          OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
//          ds.setURL("jdbc:mysql://127.0.0.1/leaveManagerDB");
//          ds.setUser("alinehuf");
//          ds.setPassword("secret");
          
          ic.bind("java:comp/env/jdbc/leaveManagerDB", c);
      } catch (NamingException ex) {
          Logger.getLogger(LoginTest.class.getName()).log(Level.SEVERE, null, ex);
      }
      
  }
  
  @Test
  public void test() {
    
    assertTrue( Login.checkPassword("bidon","bidon") == -1 );
    assertTrue( Login.checkPassword("admin","bidon") == -1 );
    assertTrue( Login.checkPassword("admin","admin") == 1 );
  }
}
