package junit;


import org.junit.Test;
import junit.framework.TestCase;

import model.User;

public class TestUser extends TestCase {
  
  @Test
  public void test() {
    User u = new User();
    assertTrue( u != null );
    
    User a = new User("admin");
    assertTrue( a.getId() == 1 );
  }
}
