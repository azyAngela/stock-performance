

import org.junit.After;
import org.junit.Test;

import java.io.File;

import model.SingleStock;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for SingleStock class.
 */
public class SingleStockTest {

  @Test
  public void test() {
    SingleStock s1 = new SingleStock("GOOG", "test", "test");
    s1.setCsv();
    double b = s1.getValue("2020-01-01", 2);
    double b1 = -3;
    String s = s1.getSymbol();
    assertEquals(b1, b, 0.01);
    assertEquals("GOOG", s);
  }


  @After
  public void clear() {
    File f = new File("test/GOOGvalue.txt");
    f.delete();

  }
}