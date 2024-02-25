

import org.junit.Test;

import helper.CheckDate;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the CheckDate class.
 */
public class CheckDateTest {


  @Test
  public void testDate() {
    String date = "2022-01-05";
    assertEquals(true, CheckDate.validDate(date));
  }

  @Test
  public void testDate2() {
    String date = "2022-40-05";
    assertEquals(false, CheckDate.validDate(date));
  }

  @Test
  public void testDate3() {
    String date = "2022/01/05";
    assertEquals(false, CheckDate.validDate(date));
  }

  @Test
  public void testInt() {
    String d = "2.0";
    assertEquals(false, CheckDate.validInt(d));
  }

  @Test
  public void testPastDate() {
    String date = "2022-01-05";
    assertEquals(true, CheckDate.isPastDate(date));
  }

}