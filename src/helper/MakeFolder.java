package helper;

import java.io.File;

/**
 * this is a utility class that can check date's format from user inputting is valid or not.
 */
public class MakeFolder {

  /**
   * to create a personal folder and two data store folders inside for user.
   *
   * @param user the username and personal folder name
   * @return true if created successfully, false if failed
   */
  public static Boolean makeUser(String user) {

    File f1 = new File("src/resource/stockData");
    File f2 = new File("src/resource/UserData/" + user + "/" + "portfolioData");
    File f3 = new File("src/resource/UserData/" + user + "/" + "portfolioCost");

    try {
      f1.mkdirs();
      f2.mkdirs();
      f3.mkdirs();
    } catch (Exception e) {
      return false;
    }


    return true;

  }

}
