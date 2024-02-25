package helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * this is a utility class that can search all files.
 */
public class SearchFile {

  /**
   * to collect all file name in a list.
   *
   * @param target represent target folder
   * @return a list with all file name in target folder
   */
  public static List<String> search(String target) {
    List<String> result = new ArrayList<>();
    String[] result1;
    File f1 = new File(target);
    result1 = f1.list();
    for (String name : result1) {
      result.add(name);
    }
    return result;
  }

}
