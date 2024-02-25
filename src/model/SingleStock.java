package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import helper.CsvParser;
import helper.StockApi;

/**
 * This class represents a singleStock.
 */

public class SingleStock implements StockModel {

  private final String symbol;
  private String address1;
  private String address2;

  /**
   * Construct a SingleStock with given data.
   *
   * @param address1 the address you save file when you call Alpha Vantage API
   * @param address2 target csv file address
   * @param symbol   a ticker symbol that represent one stock
   */
  public SingleStock(String symbol, String address1, String address2) {

    this.symbol = symbol;
    this.address1 = address1 + "/" + symbol + "api.txt";
    this.address2 = address2 + "/" + symbol + "value.txt";

  }


  public void setAddress(String address1, String address2) {
    this.address1 = address1;
    this.address2 = address2;
  }

  /**
   * get the store path of stock.
   *
   * @return the address
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * get the store path of stock.
   *
   * @return the address
   */
  public String getAddress2() {
    return address2;
  }


  /**
   * Return the symbol of this SingleStock.
   *
   * @return the symbol of this SingleStock
   */
  public String getSymbol() {
    return symbol;
  }


  /**
   * get the value of this stock by giving date.
   *
   * @param date the date you want to get value
   * @param num  stock number
   * @return the value of this stock by giving date
   */
  public double getValue(String date, double num) {
    this.setCsv();
    double value;
    LocalDateTime now = LocalDateTime.now();
    int hourNow = now.getHour();
    Date date1 = new Date();
    String line;
    try (
            BufferedReader bf = new BufferedReader(new FileReader(address2));
    ) {
      String firstLine = bf.readLine();
      String[] result = firstLine.split(",");
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dateSave = sdf.parse(result[0]);
      Date dateGave = sdf.parse(date);
      String today = sdf.format(date1);
      if (hourNow < 16 && today.equals(date)) {
        return -1;
        // return -1.0 means The stock market has not closed today, can not get today's value.
      } else if (dateGave.after(date1)) {
        return -2;
        // return -2.0 means given date is a future date, can not get value.
      } else if (dateSave.equals(dateGave)) {
        value = Double.parseDouble(result[1]) * num;
        return value;
      } else if (dateSave.after(dateGave)) {
        while ((line = bf.readLine()) != null) {
          String[] result2 = line.split(",");
          if (result2[0].equals(date)) {
            value = (Double.parseDouble(result2[1])) * num;
            return value;
          }
        }
        return -3;
        // return -3.0 means date out of range 100 days from today, can not get value.
      } else if (dateSave.before(dateGave)) {
        value = Double.parseDouble(result[1]) * num;
        return value;
      }

    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
    return 1;
  }

  /**
   * Build one csv file in local if it not exists to save stock's date
   * and value and return its address.
   *
   * @return address to save stock's date and value,
   *         or invalid warring message when symbol is invalid.
   */
  public String setCsv() {

    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(d);
    LocalDateTime now = LocalDateTime.now();
    int hourNow = now.getHour();
    if (hourNow < 16) {
      Date dBefore = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(d);
      calendar.add(Calendar.DAY_OF_MONTH, -1);
      dBefore = calendar.getTime();
      date = sdf.format(dBefore);
    }
    // if hour now early that 16:00, means stock market not close, can not get today's data
    // so set date to yesterday
    boolean b1 = new File(address2).exists();

    if (b1) {
      // if this file already exist, read the newest date to make sure if it needs to be updated
      try (
              BufferedReader bf = new BufferedReader(new FileReader(address2));
      ) {
        String line = bf.readLine();
        String[] result = line.split(",");

        if (!result[0].equals(date)) {
          bf.close();
          File f = new File(address2);
          Boolean b  = f.delete();
        } else {
          return address2;
        }

      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

    StockApi.saveFile(address1, symbol);

    try (
            BufferedReader bf = new BufferedReader(new FileReader(address1));
    ) {
      String result = bf.readLine();
      if (result.equals("{")) {
        bf.close();
        File file = new File(address1);
        file.delete();
        return "invalid symbol";
        // check if the symbol is valid.
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    CsvParser.getValueCsv(address1, address2);

    return address2;
  }


  @Override
  public void getInfo() {
    System.out.println("Symbol is " + symbol);
  }
}
