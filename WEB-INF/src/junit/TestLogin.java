package junit;


import org.junit.Test;
import junit.framework.TestCase;

import model.Login;

public class TestLogin extends TestCase {
  
  @Test
  public void test() {
    assertTrue( Login.checkPassword("bidon","bidon") == -1 );
    assertTrue( Login.checkPassword("admin","bidon") == -1 );
    assertTrue( Login.checkPassword("admin","admin") == 1 );
  }
}
