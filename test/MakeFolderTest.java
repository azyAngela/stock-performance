

import org.junit.After;
import org.junit.Test;

import java.io.File;

import helper.MakeFolder;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the MakeFolder class.
 */
public class MakeFolderTest {

  @Test
  public void test() {
    Boolean flag = MakeFolder.makeUser("test");
    assertEquals(true, flag);

  }

  @After
  public void clear() {
    File f = new File("src/resource/UserData/test/portfolioData");
    File f2 = new File("src/resource/UserData/test");
    File f3 = new File("src/resource/UserData/test/portfolioCost");


    f.delete();
    f3.delete();
    f2.delete();

  }

}