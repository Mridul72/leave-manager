package junit;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import model.User;

import org.junit.Test;

public class UserTest extends TestCase {
  
  @Test
  public void test() {
    assertFalse( User.checkPassword("bidon","bidon") );
    assertFalse( User.checkPassword("admin","bidon") );
    assertTrue( User.checkPassword("admin","admin") );
  }
}
