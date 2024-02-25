import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import helper.AutoInvest;
import helper.MakeFolder;
import model.FlexiblePortfolio;
import model.PortfolioCreator;

import static org.junit.Assert.assertEquals;


/**
 * Test class for AutoInvest.
 */
public class AutoInvestTest {


  @Test
  public void testScanLocal() {
    String path = "src/resource/UserData/testUser/portfolioData";
    String path2 = "src/resource/UserData/investStrategy/Dollar-cost.txt";
    File f = new File(path2);
    if (f.exists()) {
      f.delete();
    }

    AutoInvest.scanLocal();
    MakeFolder.makeUser("testUser");
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
    p.recordInvest("testUser", 1000, m1, m2, "2023-03-30",
            "2023-10-30", 1, "add", 0.02);
    AutoInvest.scanLocal();
    String expect = "testUser,test,2023-03-30,2023-10-30,"
            + "1,2023-03-30,1000.0,add,weight,0.02,GOOG:0.8/";

    try (
            BufferedReader bf = new BufferedReader(new FileReader(path2));
    ) {
      bf.readLine();
      String line = bf.readLine();

      assertEquals(expect, line);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File f3 = new File(path2);
    f3.delete();


  }

}