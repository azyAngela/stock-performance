

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.SearchFile;
import model.Portfolio;
import model.SingleStock;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Portfolio class.
 */
public class PortfolioTest {

  @Before
  public void setUp() {
    File f = new File("test/test1");
    f.mkdir();
  }

  @Test
  public void test() {
    SingleStock s1 = new SingleStock("GOOG", "test", "test");
    s1.setCsv();
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);

    Portfolio p = new Portfolio();
    p.setName("test");
    p.setMap(m);
    String name = p.getName();
    p.savePortfolio("test/test1");
    List<String> l = new ArrayList();
    l.add("test_Portfolio.txt");
    List<String> l2 = SearchFile.search("test/test1");
    String value = p.getValue("2020-01-01", "test");
    String valueExcept = "-3.0";


    assertEquals(l, l2);
    assertEquals("test", name);
    assertEquals(valueExcept, value);
  }


  @After
  public void clear() {
    File f = new File("test/GOOGvalue.txt");
    File f2 = new File("test/test1/test_Portfolio.txt");
    File f3 = new File("test/test1");
    f.delete();
    f2.delete();
    f3.delete();

  }

}