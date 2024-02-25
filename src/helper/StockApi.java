package helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * this Interface represent a utility Interface to use API.
 */
public interface StockApi {


  /**
   * To return a string with data.
   *
   * @param symbol Stock symbol
   * @param api    api name
   * @return a string with data
   */
  public static String getData(String symbol, String api) {
    if (api.equals("Alpha")) {
      return StockApi.alphaApi(symbol);
    } else {
      return "invalid api";
    }
  }


  /**
   * To return a string with data with Alpha API.
   *
   * @param symbol Stock symbol
   * @return a string with data
   */
  public static String alphaApi(String symbol) { //GOOG

    String apiKey = "E0O317HEBU1XLPCS";
    String stockSymbol = symbol; //ticker symbol
    URL url = null;

    try {

      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {

      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return output.toString();
  }


  /**
   * To save the data as csv in the selected address.
   *
   * @param address the address you want to store the csv file
   * @param symbol  Stock symbol
   */
  public static int saveFile(String address, String symbol) {

    int flag = 0;
    String outPut = StockApi.getData(symbol, "Alpha");
    byte[] bytes = outPut.getBytes();
    try (OutputStream os = new FileOutputStream(address)) {
      os.write(bytes);
      flag = 1;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return flag;

  }


}


