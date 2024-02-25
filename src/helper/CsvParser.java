package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this is a utility class that can parse a csv file.
 */
public class CsvParser {


  /**
   * to build a file only include date and close price.
   *
   * @param address       the file that you want to get data from, build by the Alpha Vantage API
   * @param outPutAddress the folder you want to store output file
   */
  public static void getValueCsv(String address, String outPutAddress) {
    try (
            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outPutAddress));
    ) {
      String line;
      bf.readLine();
      while ((line = bf.readLine()) != null) {
        String[] result = line.split(",");
        String date = result[0];
        String value = result[4];
        bw.write(date);
        bw.write(',');
        bw.write(value);
        bw.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    File file = new File(address);
    file.delete();


  }


}
