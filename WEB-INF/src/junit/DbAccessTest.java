package junit;

import junit.framework.TestCase;

import model.User;

import org.junit.Test;

import dao.DbAccessWithPooling;

public class DbAccessTest extends TestCase {
  
  @Test
  public void test() {
    User u = new User();
    
    DbAccessWithPooling dbaccess = new DbAccessWithPooling();
    String mdp = dbaccess.askString("users", "password", "login", "admin");
    assertTrue(mdp.equals("21232f297a57a5a743894a0e4a801fc3"));
  }
}
