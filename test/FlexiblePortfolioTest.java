import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import helper.AutoInvest;
import helper.LoadFile;
import helper.MakeFolder;
import model.FlexiblePortfolio;
import model.PortfolioCreator;
import model.SingleStock;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the FlexiblePortfolio class.
 */
public class FlexiblePortfolioTest {
  @Before
  public void setUp() {
    AutoInvest.scanLocal();
    MakeFolder.makeUser("testUser");
  }


  @Test
  public void test() {
    String path = "src/resource/UserData/testUser/portfolioData";
    String path2 = "src/resource/stockData";
    SingleStock s1 = new SingleStock("GOOG", path2, path2);
    SingleStock s2 = new SingleStock("AMZN", path2, path2);
    SingleStock s3 = new SingleStock("J", path2, path2);
    s1.setCsv();
    s2.setCsv();
    s3.setCsv();
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    String name = p.getName();
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);
    Double value = p.getValueNew("2020-01-01", "testUser");
    Double valueExcept = 0.0;


    assertEquals("test", name);
    assertEquals(valueExcept, value);
  }


  @Test
  public void testCost() {
    String path = "src/resource/UserData/testUser/portfolioData";
    String path2 = "src/resource/stockData";
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    SingleStock s1 = new SingleStock("GOOG", path2, path2);
    s1.setCsv();

    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);
    Double value = p.getCost(today, "testUser");

    try (
            BufferedReader bf = new BufferedReader(new FileReader(path2 + "/GOOGvalue.txt"));
    ) {
      String line = bf.readLine();
      String[] result = line.split(",");
      double cost = Double.parseDouble(result[1]) * 2.0 * 1.02;
      Double valueExcept = (double) Math.round(cost * 10000) / 10000;

      assertEquals(valueExcept, value);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testRecordInvest() {
    String path = "src/resource/UserData/testUser/portfolioData";
    String path2 = "src/resource/UserData/investStrategy/Dollar-cost.txt";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);

    Map<String, Double> m1 = new HashMap<>();
    Map<String, Double> m2 = new HashMap<>();
    m1.put("GOOG", 0.8);
    p.recordInvest("testUser", 1000, m1, m2, "2023-01-11",
            "2023-03-30", 2, "add", 0.02);

    try (
            BufferedReader bf = new BufferedReader(new FileReader(path2));
    ) {
      bf.readLine();
      String line = bf.readLine();
      String expect = "testUser,test,2023-01-11,2023-03-30,2,2023-01-11,"
              + "1000.0,add,weight,0.02,GOOG:0.8/";
      assertEquals(expect, line);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  @Test
  public void testAddByWeight() {
    String path = "src/resource/UserData/testUser/portfolioData";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);
    double d = p.addByWeight("GOOG", 0.8, "testUser", 1000);
    Map<String, Double> m1 = LoadFile.getInfoMap(path + "/test_Portfolio.txt");
    double d1 = 0.0;
    for (Map.Entry<String, Double> entry : m1.entrySet()) {
      d1 = entry.getValue();
      if (d1 != 0.0) {
        break;
      }
    }
    double result = 2.0 + d;
    assertEquals(d1, result, 0.1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDate() {
    String path = "src/resource/UserData/testUser/portfolioData";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);

    Map<String, Double> m1 = new HashMap<>();
    Map<String, Double> m2 = new HashMap<>();
    m1.put("GOOG", 0.8);

    p.recordInvest("testUser", 1000, m1, m2, "2021-12-01",
            "2022-12-30", 2, "add", 0.02);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test2() {
    String path = "src/resource/UserData/testUser/portfolioData";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);
    p.addStock("GOOG", 3.5, "testUser");
  }


  @Test(expected = IllegalArgumentException.class)
  public void test3() {
    String path = "src/resource/UserData/testUser/portfolioData";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);
    p.deleteStock("GOOG", 3.5, "testUser");
  }


  @Test(expected = IllegalArgumentException.class)
  public void test4() {
    String path = "src/resource/UserData/testUser/portfolioData";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", 0.02);

    p.addByWeight("GOOG", 0.8, "1", 1000);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeFee() {
    String path = "src/resource/UserData/testUser/portfolioData";
    Map<String, Double> m = new HashMap<>();
    m.put("GOOG", 2.0);
    m.put("AMZN", 10.0);
    m.put("J", 12.0);

    PortfolioCreator c = new PortfolioCreator();
    FlexiblePortfolio p = (FlexiblePortfolio) c.createPortfolio(m, "test", "flexible");
    p.savePortfolio(path);
    p.initCost("testUser", -0.02);
  }

  @After
  public void clear() {
    String path = "src/resource/UserData/testUser/portfolioData";
    String path2 = "src/resource/UserData/testUser/portfolioCost";
    String path3 = "src/resource/UserData/investStrategy/Dollar-cost.txt";
    File f = new File(path + "/test_Portfolio.txt");
    File f2 = new File(path2 + "/test_PortfolioCost.txt");
    File f3 = new File(path3);
    f.delete();
    f2.delete();
    f3.delete();

  }

}