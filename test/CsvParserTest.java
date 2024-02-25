

import org.junit.Test;

import helper.CsvParser;


/**
 * A JUnit test class for the CsvParser class.
 */
public class CsvParserTest {

  @Test(expected = Exception.class)
  public void testScv() {

    CsvParser.getValueCsv("/src/portfolio.txt", "/src/data.txt");
  }


}