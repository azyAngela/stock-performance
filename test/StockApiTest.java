

import org.junit.After;
import org.junit.Test;

import java.io.File;

import helper.StockApi;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the CheckDate class.
 */
public class StockApiTest {

  @Test
  public void test1() {

    StockApi.saveFile("test/test.txt", "GOOG");
    File f = new File("test/test.txt");
    Boolean b = f.exists();
    assertEquals(true, b);

  }

  @After
  public void clear() {
    File f = new File("test/test.txt");
    f.delete();
  }

}