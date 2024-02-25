package helper;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * this is a utility class that can load files.
 */
public class LoadFile {

  /**
   * to get a Map with stock symbol and number.
   *
   * @param target represent target address
   * @return a Map with stock symbol and number.
   */
  public static Map<String, Double> getInfoMap(String target) {
    Map<String, Double> p = new HashMap<>();

    String line;
    try (
            BufferedReader bf = new BufferedReader(new FileReader(target));
    ) {
      bf.readLine();
      line = bf.readLine();
      String[] result = line.split(",");
      for (String first : result) {
        String[] result2 = first.split(":");
        String symbol = result2[0];
        Double num = Double.valueOf(result2[1]);
        p.put(symbol, num);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return p;
  }

  /**
   * to get the name of portfolio.
   *
   * @param target represent target address
   * @return the name of portfolio.
   */
  public static String getInfoName(String target) {
    String name;
    try (
            BufferedReader bf = new BufferedReader(new FileReader(target));
    ) {
      name = bf.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return name;
  }


  /**
   * to print the whole information of portfolio.
   *
   * @param target represent target address
   * @return String of message
   */
  public static String getInfo(String target) {
    StringJoiner s = new StringJoiner("\n");
    String result;
    Map<String, Double> p = LoadFile.getInfoMap(target);
    String name = LoadFile.getInfoName(target);
    s.add("Portfolio name is : " + name);
    for (Map.Entry<String, Double> entry : p.entrySet()) {
      s.add("stock ticker symbol is : " + entry.getKey());
      s.add("stock quantity is : " + entry.getValue());
    }
    result = s.toString();
    return result;
  }


  /**
   * to check target file is valid or not.
   *
   * @param target represent target address
   */
  public static int checkFile(String target) {
    int flag = 0;
    String line;
    try (
            BufferedReader bf = new BufferedReader(new FileReader("src/" + target));
    ) {
      bf.readLine();
      line = bf.readLine();
      String[] result = line.split(",");
      String first = result[0];
      String[] result2 = first.split(":");
      String symbol = result2[0];
      StockApi.saveFile("src/test.txt", symbol);
      BufferedReader bf2 = new BufferedReader(new FileReader("src/test.txt"));
      if (bf2.readLine().equals("{")) {
        flag = 1;
        bf2.close();
        File file = new File("src/test.txt");
        file.delete();
        // check if the symbol is valid.
      }
      bf2.close();


    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File file = new File("src/test.txt");
    Boolean i = file.delete();
    System.out.println(i);
    return flag;
  }


  /**
   * to change the original value to the price that user entered.
   *
   * @param symbol the symbol of stock which user want to change
   * @param date   the date user want to change
   * @param value  user want to change to this value
   * @param target target folder
   */
  public static void changeValue(String symbol, String date, String value, String target) {

    String line;
    try (BufferedReader bf = new BufferedReader(new FileReader(target
            + "/" + symbol + "value.txt"));
         BufferedWriter bw = new BufferedWriter(new FileWriter(target
                 + "/" + symbol + "value2.txt"));
    ) {
      while ((line = bf.readLine()) != null) {

        String[] result = line.split(",");

        if (result[0].equals(date)) {
          line = line.replace(result[1], value);
        }
        bw.write(line);
        bw.newLine();
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    File file = new File(target + "/" + symbol + "value.txt");
    file.delete();
    // delete original file

    // change the name of new file same to the delete one
    String filePath = target + "/" + symbol + "value2.txt";
    try {
      File src = new File(filePath);
      filePath = target + "/" + symbol + "value.txt";
      File des = new File(filePath);
      if (des.exists()) {
        boolean res = des.delete();
        if (!res) {
          System.out.println("Failed to delete file");
        }
      }
      if (!src.renameTo(des)) {
        System.out.println("Failed to renameTo file");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * to print target file's address.
   *
   * @param dir  the folder you want to search in
   * @param name the name of target file
   */
  public static void searchFile(File dir, String name) {

    if (dir.isDirectory()) {
      File[] files = dir.listFiles();
      if (files != null && files.length > 0) {

        for (File file : files) {
          if (file.isFile()) {
            if (file.getName().equals(name)) {
              System.out.println("Your file address is " + file.getAbsolutePath());
            }
          } else {
            searchFile(file, name);
          }
        }
      }
    } else {
      System.out.println("not a folder");
    }

  }

  /**
   * to duplicate target file to our default data file.
   *
   * @param address the address of target file
   * @param user    the Username
   * @param name    the portfolio name of target file
   */
  public static void duplicateFile(String address, String user, String name) {

    String line;
    try (BufferedReader bf = new BufferedReader(new FileReader("src/" + address));
         BufferedWriter bw = new BufferedWriter(new FileWriter("src/resource/UserData/"
                 + user + "/" + "portfolioData/" + name + ".txt"));

    ) {
      while ((line = bf.readLine()) != null) {
        bw.write(line);
        bw.newLine();


      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


}
