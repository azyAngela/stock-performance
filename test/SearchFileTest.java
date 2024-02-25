

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import helper.SearchFile;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the CsvParser class.
 */
public class SearchFileTest {

  @Before
  public void setUp() {
    File f = new File("test/test1/1.txt");
    f.mkdirs();
  }


  @Test(expected = Exception.class)
  public void testSearch() {

    SearchFile.search("src/123");
  }

  @Test
  public void testSearch2() {

    List<String> l = new ArrayList();
    l.add("1.txt");

    List<String> l2 = SearchFile.search("test/test1");
    assertEquals(l, l2);
  }

  @After
  public void clear() {
    File f = new File("test/test1/1.txt");
    File f2 = new File("test/test1");

    f.delete();
    f2.delete();
  }


}