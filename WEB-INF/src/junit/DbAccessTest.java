package junit;

import junit.framework.TestCase;

import org.junit.Test;

import dao.DbAccessWithPooling;

public class DbAccessTest extends TestCase {
  
  @Test
  public void test() {
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String mdp = dbaccess.askString("users", "password", "login", "admin");
    assertTrue(mdp.equals("21232f297a57a5a743894a0e4a801fc3"));
    
  }
}
