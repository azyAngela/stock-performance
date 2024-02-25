package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this is a utility class that can check some input.
 */
public class CheckDate {

  /**
   * to check input date string is valid or not.
   *
   * @param date the date string you want to check
   * @return true if date string is valid, false if invalid
   */
  public static Boolean validDate(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      String[] s = date.split("-");
    } catch (Exception e) {
      return false;
    }
    try {
      sdf.setLenient(false);
      sdf.parse(date);

    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * to check input string is int or not.
   *
   * @param input input string
   * @return true if is int, false if not
   */
  public static Boolean validInt(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  /**
   * To check the date is past date or not.
   *
   * @return True if is pastDate
   */
  public static Boolean isPastDate(String date) {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    if (today.compareTo(date) > 0) {
      return true;
    } else {
      return false;
    }
  }


}
