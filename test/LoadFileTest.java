

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import helper.LoadFile;
import model.Portfolio;
import model.SingleStock;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the CheckDate class.
 */
public class LoadFileTest {

  @Before
  public void setUp() {
    SingleStock s1 = new SingleStock("GOOG", "test", "test");
    s1.setCsv();
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    Portfolio p = new Portfolio(m, "Google");
    p.savePortfolio("test");

  }

  @Test
  public void test() {

    Map<String, Double> m2 = LoadFile.getInfoMap("test/Google_Portfolio.txt");
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    String name = LoadFile.getInfoName("test/Google_Portfolio.txt");

    assertEquals(m, m2);
    assertEquals("Google", name);
  }


  @After
  public void clear() {
    File f = new File("test/GOOGvalue.txt");
    File f2 = new File("test/Google_Portfolio.txt");


    f.delete();
    f2.delete();
  }

}