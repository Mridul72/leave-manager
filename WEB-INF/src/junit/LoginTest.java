package junit;

//import static org.junit.Assert.*;
import junit.framework.TestCase;
import model.Login;

import org.junit.Test;

public class LoginTest extends TestCase {
  
  @Test
  public void test() {
    assertTrue( Login.checkPassword("bidon","bidon") == -1);
    assertTrue( Login.checkPassword("admin","bidon") == -1 );
    assertTrue( Login.checkPassword("admin","admin") == 1 );
  }
}
