package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import helper.CheckDate;


/**
 * This class represents a flexible Portfolio.
 */
public class FlexiblePortfolio extends AbstractPortfolio {


  /**
   * Construct a Portfolio with given data.
   *
   * @param stocks a map of stocks' symbols and numbers of them
   * @param name   a name if this Portfolio
   */
  public FlexiblePortfolio(Map<String, Double> stocks, String name) {
    super(stocks, name);
  }

  /**
   * Construct a Portfolio with given data.
   */
  public FlexiblePortfolio() {
    // empty constructor

  }


  /**
   * To record the reservation action like add or sell or add Dollar-cost in the local file.
   *
   * @param user         username
   * @param invest       invest money
   * @param weight       map of weight of each stock
   * @param simplyChange map of add action or sell action of each stock
   * @param startDate    start date of this investigation
   * @param endDate      end date of this investigation
   * @param intervalDays intervalDays
   * @param action       add or delete
   * @param fee          commission fee
   * @throws IllegalArgumentException if input arguments are invalid
   */
  public int recordInvest(String user, double invest, Map<String, Double> weight,
                          Map<String, Double> simplyChange, String startDate, String endDate,
                          int intervalDays, String action, double fee) {


    if (fee < 0) {
      throw new IllegalArgumentException("invalid fee!!!");
    }


    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    String nextInvestDate = startDate;
    boolean da1 = CheckDate.validDate(startDate);
    Date startDay = null;

    try {
      startDay = sdf.parse(startDate);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }


    if (!da1) {
      throw new IllegalArgumentException("invalid date!!!");
    }

    if (today.equals(startDate)) {
      for (Map.Entry<String, Double> entry : weight.entrySet()) {
        double num = this.addByWeight(entry.getKey(), entry.getValue(), user, invest);
        this.updateCost(entry.getKey(), num, "add", user, fee);
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.DATE, intervalDays);
        Date d2 = now.getTime();
        nextInvestDate = sdf.format(d2);
      }
    }

    String start = "1970-01-01";
    if (today.compareTo(startDate) > 0) {
      if (today.compareTo(endDate) >= 0) {
        for (Map.Entry<String, Double> entry : weight.entrySet()) {
          while (start.compareTo(endDate) < 0) {
            String symbol = entry.getKey();
            Double weight1 = entry.getValue();
            SingleStock s = new SingleStock(symbol, "src/resource/stockData",
                    "src/resource/stockData");
            s.setCsv();
            start = sdf.format(startDay);
            double unitPrice = getUnitPrice(symbol, start);
            double share = (invest * weight1) / unitPrice;
            this.addStock(symbol, share, user);
            this.updateCostOld(symbol, share, "add", user, fee, start);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(startDay);
            rightNow.add(Calendar.DATE, intervalDays);
            startDay = rightNow.getTime();
          }

        }
        return 1;
      } else {
        for (Map.Entry<String, Double> entry : weight.entrySet()) {
          while (start.compareTo(today) < 0) {
            String symbol = entry.getKey();
            Double weight1 = entry.getValue();
            SingleStock s = new SingleStock(symbol, "src/resource/stockData",
                    "src/resource/stockData");
            s.setCsv();
            start = sdf.format(startDay);
            double unitPrice = getUnitPrice(symbol, start);
            double share = (invest * weight1) / unitPrice;
            this.addStock(symbol, share, user);
            this.updateCostOld(symbol, share, "add", user, fee, start);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(startDay);
            rightNow.add(Calendar.DATE, intervalDays);
            startDay = rightNow.getTime();
          }

        }
        startDate = sdf.format(startDay);
        nextInvestDate = startDate;
      }


    }

    String path = "src/resource/UserData/investStrategy/Dollar-cost.txt";
    String line;

    try (
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
    ) {
      if (weight.isEmpty()) {
        line = user + "," + this.getName() + "," + startDate + "," + endDate + ","
                + intervalDays + "," + nextInvestDate + "," + invest + "," + action + ","
                + "simple" + "," + fee + ",";
        for (Map.Entry<String, Double> entry : simplyChange.entrySet()) {

          line = line + entry.getKey() + ":" + entry.getValue() + "/";

        }
        bw.newLine();
        bw.write(line);
      } else {
        line = user + "," + this.getName() + "," + startDate + "," + endDate + ","
                + intervalDays + "," + nextInvestDate + "," + invest + "," + "add"
                + "," + "weight" + "," + fee + ",";
        for (Map.Entry<String, Double> entry : weight.entrySet()) {
          line = line + entry.getKey() + ":" + entry.getValue() + "/";
        }
        bw.newLine();
        bw.write(line);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return -1;
  }


  /**
   * To get the newest unit price of select stock.
   *
   * @param symbol stock symbol
   * @return the price
   */
  private double getUnitPrice(String symbol, String date) {
    String path = "src/resource/stockData/" + symbol + "value.txt";

    try (
            BufferedReader bf = new BufferedReader(new FileReader(path));
    ) {
      String line = bf.readLine();
      String[] result = line.split(",");
      String price = result[1];
      return Double.parseDouble(price);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * To add the stock by weight and return the number of share changes.
   *
   * @param symbol stock symbol
   * @param weight weight for select stock
   * @param user   username
   * @param invest invest money
   * @return the number of share changes
   * @throws IllegalArgumentException if the arguments are invalid
   */
  public double addByWeight(String symbol, double weight, String user, double invest) {

    Date d1 = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d1);


    try {
      boolean flag = false;
      String path = "src/resource/stockData";
      SingleStock s = new SingleStock(symbol, path, path);
      s.setCsv();
      double unitPrice = getUnitPrice(symbol, today);
      double share = (invest * weight) / unitPrice;
      BigDecimal b = new BigDecimal(share);
      double num = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
      String line;
      String value;
      String address = "src/resource/UserData/" + user
              + "/portfolioData/" + this.getName() + "_Portfolio.txt";
      String addressTemp = "src/resource/UserData/" + user
              + "/portfolioData/" + this.getName() + "Temp_Portfolio.txt";
      CharArrayWriter tempStream = new CharArrayWriter();
      try (

              BufferedReader bf = new BufferedReader(new FileReader(address));
              BufferedWriter bw = new BufferedWriter(new FileWriter(addressTemp));
      ) {
        line = bf.readLine();
        tempStream.write(line);
        tempStream.append(System.getProperty("line.separator"));
        line = bf.readLine();
        value = bf.readLine();
        String[] result = line.split(",");
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
          StringBuilder sb2 = new StringBuilder();
          String first = result[i];
          String[] result2 = first.split(":");
          String symbolOld = result2[0];
          double numOld = Double.parseDouble(result2[1]);
          sb2.append(result2[0]);
          sb2.append(":");
          sb2.append(result2[1]);
          if (symbolOld.equals(symbol)) {
            flag = true;
            int length = result2[1].length();
            double d = numOld + num;
            d = (double) Math.round(d * 10000) / 10000;
            String numNew = String.valueOf(d);
            int index1 = sb2.length() - length;
            int index2 = sb2.length();
            sb2.delete(index1, index2);
            sb2.append(numNew);
          }
          sb1.append(sb2.toString()).append(",");

        }
        if (!flag) {
          sb1.append(symbol).append(":").append(num).append(",");
        }
        tempStream.write(sb1.toString());
        tempStream.append(System.getProperty("line.separator"));
        tempStream.write(value);

        tempStream.writeTo(bw);

      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      File file = new File(address);
      file.delete();
      File fileNew = new File(address);
      File newFile = new File(addressTemp);
      newFile.renameTo(fileNew);
      return num;
    } catch (Exception e) {
      throw new IllegalArgumentException("invalid input arguments");
    }
  }


  /**
   * To initialize the cost and value of a portfolio when it created.
   * Include the Commission Fees 0.02%.
   *
   * @param user username of user
   * @return True if success initialize, false if the date is invalid
   * @throws IllegalArgumentException if commission fee is negative
   */
  public Boolean initCost(String user, double fee) {
    if (fee < 0) {
      throw new IllegalArgumentException("commission fee can not be negative");
    }
    Boolean flag = false;
    String date = setDate();
    String value1;
    String address = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "_Portfolio.txt";
    String address2 = "src/resource/stockData";
    String address3 = "src/resource/UserData/" + user
            + "/portfolioCost/" + this.getName() + "_PortfolioCost.txt";

    try (
            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedWriter bw = new BufferedWriter(new FileWriter(address, true));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(address3));
    ) {

      bf.readLine();
      bf.readLine();
      value1 = this.getValue(date, address2);
      double finalCost = Double.parseDouble(value1) * (1 + fee);
      double finalValue = Double.parseDouble(value1);


      if (Objects.equals(value1, "-3.0")) {
        return flag;
        // which means date is invalid, the market might be close today or yesterday
      }
      String fc = String.format("%.4f", finalCost);
      String fv = String.format("%.4f", finalValue);
      bw.newLine();
      bw.write(fc + "," + fv);
      bw2.write(date + "," + fc + "," + fv);
      flag = true;


    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return flag;
  }

  /**
   * To update the cost of portfolio.
   *
   * @param symbol stock symbol
   * @param num    number of shares
   * @param action add or delete
   * @param user   portfolio owner
   * @throws IllegalArgumentException if commission fee is negative
   */
  public void updateCost(String symbol, double num, String action, String user, double fee) {
    if (fee < 0) {
      throw new IllegalArgumentException("commission fee can not be negative");
    }
    String date = setDate();
    double costNew = 0;
    double valueNew = 0;
    String address = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "_Portfolio.txt";
    String addressTemp = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "Temp_Portfolio.txt";
    String address2 = "src/resource/stockData";
    String address3 = "src/resource/UserData/" + user
            + "/portfolioCost/" + this.getName() + "_PortfolioCost.txt";
    CharArrayWriter tempStream = new CharArrayWriter();
    try (
            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedWriter bw = new BufferedWriter(new FileWriter(addressTemp));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(address3, true));
    ) {
      String line = bf.readLine();
      tempStream.write(line);
      tempStream.append(System.getProperty("line.separator"));
      line = bf.readLine();
      tempStream.write(line);
      tempStream.append(System.getProperty("line.separator"));
      line = bf.readLine();
      String[] result = line.split(",");
      double costOld = Double.parseDouble(result[0]);
      double valuetOld = Double.parseDouble(result[1]);

      SingleStock s = new SingleStock(symbol, address2, address2);
      s.setCsv();
      double costChange = s.getValue(date, num) * (1.0 + fee);
      double valueChange = s.getValue(date, num);
      if (action.equals("add")) {
        costNew = costOld + costChange;
        valueNew = valuetOld + valueChange;
        bw2.newLine();
        bw2.write(date + "," + String.format("%.4f", costNew)
                + "," + String.format("%.4f", valueNew));
      } else {
        costNew = costOld - valueChange + (valueChange * fee);
        valueNew = valuetOld - valueChange;
        bw2.newLine();
        bw2.write(date + "," + String.format("%.4f", costNew)
                + "," + String.format("%.4f", valueNew));
      }
      line = String.format("%.4f", costNew) + "," + String.format("%.4f", valueNew);
      tempStream.write(line);
      tempStream.writeTo(bw);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File file = new File(address);
    file.delete();
    File fileNew = new File(address);
    File newFile = new File(addressTemp);
    newFile.renameTo(fileNew);
  }


  /**
   * To add stock in exited portfolio.
   *
   * @param symbol the stock symbol
   * @param num    num of the shares, should be like 2.0, 10.0 ... etc
   * @param user   username of this portfolio
   * @return 0 if add successfully, otherwise return 1
   * @throws IllegalArgumentException if the number of shares are invalid
   */
  public int addStock(String symbol, double num, String user) {


    boolean flag = false;
    String line;
    String value;
    String address = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "_Portfolio.txt";
    String addressTemp = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "Temp_Portfolio.txt";
    CharArrayWriter tempStream = new CharArrayWriter();
    try (

            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedWriter bw = new BufferedWriter(new FileWriter(addressTemp));
    ) {
      line = bf.readLine();
      tempStream.write(line);
      tempStream.append(System.getProperty("line.separator"));
      line = bf.readLine();
      value = bf.readLine();
      String[] result = line.split(",");
      StringBuilder sb1 = new StringBuilder();
      for (int i = 0; i < result.length; i++) {
        StringBuilder sb2 = new StringBuilder();
        String first = result[i];
        String[] result2 = first.split(":");
        String symbolOld = result2[0];
        double numOld = Double.parseDouble(result2[1]);
        sb2.append(result2[0]);
        sb2.append(":");
        sb2.append(result2[1]);
        if (symbolOld.equals(symbol)) {
          flag = true;
          int length = result2[1].length();
          double d = numOld + num;
          d = (double) Math.round(d * 10000) / 10000;
          String numNew = String.valueOf(d);
          int index1 = sb2.length() - length;
          int index2 = sb2.length();
          sb2.delete(index1, index2);
          sb2.append(numNew);
        }
        sb1.append(sb2.toString()).append(",");

      }
      if (!flag) {
        sb1.append(symbol).append(":").append(num).append(",");
      }
      tempStream.write(sb1.toString());
      tempStream.append(System.getProperty("line.separator"));
      tempStream.write(value);

      tempStream.writeTo(bw);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    File file = new File(address);
    file.delete();
    File fileNew = new File(address);
    File newFile = new File(addressTemp);
    newFile.renameTo(fileNew);
    return 0;

  }


  /**
   * To delete stock in exited portfolio.
   *
   * @param symbol the stock symbol
   * @param num    num of the shares, should be like 2.0, 10.0 ... etc
   * @param user   username of this portfolio
   * @return 0 if success, 1 and 2 if fail
   * @throws IllegalArgumentException if the arguments are invalid
   */
  public int deleteStock(String symbol, double num, String user) {
    double de = (num * 10) % 10;
    if (de != 0.0) {
      throw new IllegalArgumentException("invalid number");
    }
    boolean flag = false;
    String line;
    String address = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "_Portfolio.txt";
    String addressTemp = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "Temp_Portfolio.txt";
    CharArrayWriter tempStream = new CharArrayWriter();
    try (
            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedWriter bw = new BufferedWriter(new FileWriter(addressTemp));
    ) {
      line = bf.readLine();
      tempStream.write(line);
      tempStream.append(System.getProperty("line.separator"));
      line = bf.readLine();
      String value = bf.readLine();
      String[] result = line.split(",");
      StringBuilder sb1 = new StringBuilder();
      for (int i = 0; i < result.length; i++) {
        StringBuilder sb2 = new StringBuilder();
        String first = result[i];
        String[] result2 = first.split(":");
        String symbolOld = result2[0];
        double numOld = Double.parseDouble(result2[1]);
        sb2.append(result2[0]);
        sb2.append(":");
        sb2.append(result2[1]);
        if (symbolOld.equals(symbol)) {
          flag = true;
          int length = result2[1].length();
          if (numOld < num) {
            bw.close();
            File file = new File(addressTemp);
            file.delete();
            return 1;
            // return 1 means don't have enough share to sell
          }
          double d = numOld - num;
          d = (double) Math.round(d * 10000) / 10000;
          String numNew = String.valueOf(d);
          int index1 = sb2.length() - length;
          int index2 = sb2.length();
          sb2.delete(index1, index2);
          sb2.append(numNew);
        }
        sb1.append(sb2.toString()).append(",");
      }
      tempStream.write(sb1.toString());
      tempStream.append(System.getProperty("line.separator"));
      tempStream.write(value);
      tempStream.writeTo(bw);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    File file = new File(address);
    file.delete();
    File fileNew = new File(address);
    File newFile = new File(addressTemp);
    newFile.renameTo(fileNew);
    if (!flag) {
      return 2;
      //means there is no such stock in the portfolio
    } else {
      return 0;
    }

  }


  /**
   * to get the value of this portfolio, four decimal places.
   * If the date before the stock's created date, value is 0.
   *
   * @param date the date you want know the value
   * @param user portfolio user name
   * @return value of this portfolio, -2.0 and -1.0 if invalid date
   */
  public Double getValueNew(String date, String user) {
    String type = "value";
    double result;
    result = getMoney(date, user, type);
    return result;
  }


  /**
   * Return the cost of portfolio in certain date.
   *
   * @param date the date you want know the cost
   * @param user the portfolio owner
   * @return the cost, -2.0 -1.0 means invalid date
   */
  public double getCost(String date, String user) {
    String type = "cost";
    double result;
    result = getMoney(date, user, type);
    return result;
  }


  /**
   * To draw a bar chart by a date range.
   *
   * @param dateS date want to start
   * @param dateE date want to end
   * @param user  portfolio username
   * @return 0 if success, otherwise in valid date
   */
  public int drawChart(String dateS, String dateE, String user) {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    if (dateS.compareTo(today) > 0) {
      return -2;
      // startDay is a future date, invalid
    } else if (dateE.compareTo(today) > 0) {
      return -3;
      // endDay is a future date, invalid
    }
    double value = this.getValueNew(dateE, user);
    int scale;

    try {
      Date dateStart = sdf.parse(dateS);
      Date dateEnd = sdf.parse(dateE);
      long diff = dateEnd.getTime() - dateStart.getTime();
      int diffDays = Long.valueOf(diff / (24 * 60 * 60 * 1000)).intValue();
      if (diffDays < 5) {
        System.out.println("time range shorter than 5 day, can not draw chart!");
        return -1;
      }
      if (value >= 25000 && value <= 50000) {
        scale = 1000;
        System.out.println("This absolute type scale: * = " + scale);
      } else if (value < 25000) {
        scale = 500;
        System.out.println("This absolute type scale: * = " + scale);
      } else {
        scale = 2000;
        System.out.println("This absolute type scale: * = " + scale);
      }
      if (diffDays > 1800) {
        // means date range greater the 5 years, so can use 1 year as timestamp
        System.out.println("timestamp is 1 year");
        System.out.println(" ");
        drawChart1("year", user, dateEnd, dateStart, diffDays, scale);
        return 0;

      } else if (diffDays > 900) {
        // means date range in 30 months to 60 months, so use 2 months as timestamp
        System.out.println("timestamp is 2 months");
        System.out.println(" ");
        drawChart1("2 months", user, dateEnd, dateStart, diffDays, scale);
        return 0;
      } else if (diffDays > 150) {
        // means date range in 1 months to 30 months, so use 1 month as timestamp
        System.out.println("timestamp is 1 month");
        System.out.println(" ");
        drawChart1("month", user, dateEnd, dateStart, diffDays, scale);
        return 0;
      } else if (diffDays > 30) {
        // means date range in 30 days to 150 days, so use 2 day as timestamp
        System.out.println("timestamp is 2 days");
        System.out.println(" ");
        drawChart1("2 days", user, dateEnd, dateStart, diffDays, scale);
        return 0;
      } else {
        // means date range in 5 days to 30 days, so use 1 day as timestamp
        System.out.println("timestamp is 1 day");
        System.out.println(" ");
        drawChart1("day", user, dateEnd, dateStart, diffDays, scale);
        return 0;
      }
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * To draw a horizontal bar chart of this portfolio in the time.
   *
   * @param type     timestamp type
   * @param user     username
   * @param endDay   date want to end
   * @param startDay date want to start
   * @param diffDays days between startDay and endDay
   * @param scale    scale of *
   */
  private void drawChart1(String type, String user, Date endDay, Date startDay,
                          int diffDays, int scale) {
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    switch (type) {
      case "year": {
        int m = diffDays / (30 * 12) + 1;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(startDay);
        Date dt1 = rightNow.getTime();
        String startDateYear = sdf1.format(dt1);
        String todayYear = sdf1.format(endDay);
        System.out.println("                Performance of portfolio " + this.getName()
                + " from " + startDateYear + " to " + todayYear);
        System.out.println(" ");
        for (int count = 0; count < m; count++) {
          Calendar now = Calendar.getInstance();
          now.setTime(startDay);
          now.add(Calendar.MONTH, count * 12);
          Date dt2 = now.getTime();
          String year = sdf1.format(dt2);
          String endDate = sdf1.format(dt2) + "-12-31";
          double value = this.getValueNew(endDate, user);
          if (value == -2.0) {
            value = this.getValueNew(today, user);
          }
          int valueInt = (int) value;
          int starCount = valueInt / scale;
          if (starCount > 50) {
            starCount = 50;
          }
          StringBuilder star = new StringBuilder();
          for (int i = 0; i < starCount; i++) {
            star.append("*");
          }
          System.out.println(year + ":" + star);
        }
        break;
      }
      case "2 months": {
        int m = diffDays / 60 + 1;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(startDay);
        Date dt1 = rightNow.getTime();
        String startDateMonth = sdf1.format(dt1);
        String todayMonth = sdf1.format(endDay);
        System.out.println("                Performance of portfolio " + this.getName()
                + " from " + startDateMonth + " to " + todayMonth);
        System.out.println(" ");
        for (int count = 0; count < m + 1; count++) {
          Calendar now = Calendar.getInstance();
          now.setTime(startDay);
          now.add(Calendar.MONTH, count * 2);
          Date dt2 = now.getTime();
          String month = sdf1.format(dt2);
          if (month.compareTo(todayMonth) > 0) {
            now.add(Calendar.MONTH, -1);
            dt2 = now.getTime();
            month = sdf1.format(dt2);
          }
          String endDate = sdf1.format(dt2) + "-31";
          double value = this.getValueNew(endDate, user);
          if (value == -2.0) {
            value = this.getValueNew(today, user);
          }
          int valueInt = (int) value;
          int starCount = valueInt / scale;
          if (starCount > 50) {
            starCount = 50;
          }
          StringBuilder star = new StringBuilder();
          for (int i = 0; i < starCount; i++) {
            star.append("*");
          }
          System.out.println(month + ":" + star);
          if (month.equals(todayMonth)) {
            break;
          }
        }

        break;
      }
      case "month": {
        int m = diffDays / 30 + 1;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(startDay);
        Date dt1 = rightNow.getTime();
        String startDateMonth = sdf1.format(dt1);
        String todayMonth = sdf1.format(endDay);
        System.out.println("                Performance of portfolio " + this.getName()
                + " from " + startDateMonth + " to " + todayMonth);
        System.out.println(" ");
        for (int count = 0; count < m; count++) {
          Calendar now = Calendar.getInstance();
          now.setTime(startDay);
          now.add(Calendar.MONTH, count);
          Date dt2 = now.getTime();
          String month = sdf1.format(dt2);
          String endDate = sdf1.format(dt2) + "-31";
          double value = this.getValueNew(endDate, user);
          if (value == -2.0) {
            value = this.getValueNew(today, user);
          }
          int valueInt = (int) value;
          int starCount = valueInt / scale;
          if (starCount > 50) {
            starCount = 50;
          }
          StringBuilder star = new StringBuilder();
          for (int i = 0; i < starCount; i++) {
            star.append("*");
          }
          System.out.println(month + ":" + star);
        }
        break;
      }
      case "2 days": {
        int m = diffDays;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(startDay);
        Date dt1 = rightNow.getTime();
        String startDate = sdf1.format(dt1);
        String todayDay = sdf1.format(endDay);
        System.out.println("                Performance of portfolio " + this.getName()
                + " from " + startDate + " to " + todayDay);
        System.out.println(" ");
        for (int count = 0; count < m + 1; count++) {
          Calendar now = Calendar.getInstance();
          now.setTime(startDay);
          now.add(Calendar.DATE, 2 * count);
          Date dt2 = now.getTime();
          String day = sdf1.format(dt2);
          if (day.compareTo(todayDay) > 0) {
            now.add(Calendar.DATE, -1);
            dt2 = now.getTime();
            day = sdf1.format(dt2);
          }
          String endDate = sdf1.format(dt2);
          double value = this.getValueNew(endDate, user);
          if (value == -2.0) {
            value = this.getValueNew(today, user);
          }
          int valueInt = (int) value;
          int starCount = valueInt / scale;
          if (starCount > 50) {
            starCount = 50;
          }
          StringBuilder star = new StringBuilder();
          for (int i = 0; i < starCount; i++) {
            star.append("*");
          }
          System.out.println(day + ":" + star);
          if (day.equals(todayDay)) {
            break;
          }
        }
        break;
      }
      case "day": {
        int m = diffDays;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(startDay);
        Date dt1 = rightNow.getTime();
        String startDate = sdf1.format(dt1);
        String todayDay = sdf1.format(endDay);
        System.out.println("                Performance of portfolio " + this.getName()
                + " from " + startDate + " to " + todayDay);
        System.out.println(" ");
        for (int count = 0; count < m + 1; count++) {
          Calendar now = Calendar.getInstance();
          now.setTime(startDay);
          now.add(Calendar.DATE, count);
          Date dt2 = now.getTime();
          String day = sdf1.format(dt2);
          String endDate = sdf1.format(dt2);
          double value = this.getValueNew(endDate, user);
          if (value == -2.0) {
            value = this.getValueNew(today, user);
          }
          int valueInt = (int) value;
          int starCount = valueInt / scale;
          if (starCount > 50) {
            starCount = 50;
          }
          StringBuilder star = new StringBuilder();
          for (int i = 0; i < starCount; i++) {
            star.append("*");
          }
          System.out.println(day + ":" + star);
        }
        break;
      }
      default:
        System.out.println("default");
    }
  }


  /**
   * Helper method to set the right date if the stock market is not closed.
   *
   * @return a format date string
   */
  private String setDate() {
    LocalDateTime now = LocalDateTime.now();
    int hourNow = now.getHour();
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date = sdf.format(d);
    if (hourNow < 16) {
      // The stock market has not closed today, so use yesterday as the date
      Date dBefore = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(d);
      calendar.add(Calendar.DAY_OF_MONTH, -1);
      dBefore = calendar.getTime();
      date = sdf.format(dBefore);
    }

    return date;
  }

  /**
   * Helper method to get the cost or value data from local file.
   *
   * @param date the date you want know the value
   * @param user portfolio user name
   * @param type value or cost you want to get
   * @return data of this portfolio, -2.0 and -1.0 if invalid date
   * @throws IllegalArgumentException if the date is invalid
   */
  private double getMoney(String date, String user, String type) {
    boolean da = CheckDate.validDate(date);
    if (!da) {
      throw new IllegalArgumentException("invalid date");
    }

    String lastData = "";
    String line;
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    if (today.compareTo(date) < 0) {
      return -2.0;
      // input date is a future date
    }

    String address = "src/resource/UserData/" + user
            + "/portfolioCost/" + this.getName() + "_PortfolioCost.txt";
    String address2 = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "_Portfolio.txt";
    try (
            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedReader bf2 = new BufferedReader(new FileReader(address2));
    ) {
      if (date.equals(today)) {
        bf2.readLine();
        bf2.readLine();
        line = bf2.readLine();
        String cost;
        String[] result = line.split(",");
        if (type.equals("value")) {
          cost = result[1];
          return Double.parseDouble(cost);
        } else {
          cost = result[0];
          return Double.parseDouble(cost);
        }
      } else {
        int count = 1;
        String dayBefore = null;
        String costBefore = null;
        while ((line = bf.readLine()) != null) {
          String[] result = line.split(",");
          String dateCheck = result[0];
          double cost;
          if (type.equals("value")) {
            cost = Double.parseDouble(result[2]);
          } else {
            cost = Double.parseDouble(result[1]);
          }
          if (dateCheck.compareTo(date) > 0 && count == 1) {
            return 0.0;
            // input date before the portfolio created date, so return 0 as cost
          } else if (dateCheck.compareTo(date) > 0 && dayBefore.compareTo(date) == 0) {
            return Double.parseDouble(costBefore);
          } else if (dateCheck.compareTo(date) > 0 && dayBefore.compareTo(date) < 0) {
            return Double.parseDouble(costBefore);
          }
          dayBefore = result[0];
          if (type.equals("value")) {
            costBefore = result[2];
            lastData = result[2];
          } else {
            costBefore = result[1];
            lastData = result[1];
          }
          count++;
        }
        if ((line = bf.readLine()) == null) {
          return Double.parseDouble(lastData);
        }

      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return -1.0;

  }


  /**
   * To update the cost of portfolio.
   *
   * @param symbol stock symbol
   * @param num    number of shares
   * @param action add or delete
   * @param user   portfolio owner
   * @throws IllegalArgumentException if commission fee is negative
   */
  private void updateCostOld(String symbol, double num, String action,
                             String user, double fee, String date) {
    if (fee < 0) {
      throw new IllegalArgumentException("commission fee can not be negative");
    }
    double costNew = 0;
    double valueNew = 0;
    String address = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "_Portfolio.txt";
    String addressTemp = "src/resource/UserData/" + user
            + "/portfolioData/" + this.getName() + "Temp_Portfolio.txt";
    String address2 = "src/resource/stockData";
    String address3 = "src/resource/UserData/" + user
            + "/portfolioCost/" + this.getName() + "_PortfolioCost.txt";
    CharArrayWriter tempStream = new CharArrayWriter();
    try (
            BufferedReader bf = new BufferedReader(new FileReader(address));
            BufferedWriter bw = new BufferedWriter(new FileWriter(addressTemp));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(address3, true));
    ) {
      String line = bf.readLine();
      tempStream.write(line);
      tempStream.append(System.getProperty("line.separator"));
      line = bf.readLine();
      tempStream.write(line);
      tempStream.append(System.getProperty("line.separator"));
      line = bf.readLine();
      String[] result = line.split(",");
      double costOld = Double.parseDouble(result[0]);
      double valuetOld = Double.parseDouble(result[1]);

      SingleStock s = new SingleStock(symbol, address2, address2);
      s.setCsv();
      double costChange = s.getValue(date, num) * (1.0 + fee);
      double valueChange = s.getValue(date, num);
      if (action.equals("add")) {
        costNew = costOld + costChange;
        valueNew = valuetOld + valueChange;
        bw2.newLine();
        bw2.write(date + "," + String.format("%.4f", costNew)
                + "," + String.format("%.4f", valueNew));
      } else {
        costNew = costOld - valueChange + (valueChange * fee);
        valueNew = valuetOld - valueChange;
        bw2.newLine();
        bw2.write(date + "," + String.format("%.4f", costNew)
                + "," + String.format("%.4f", valueNew));
      }
      line = String.format("%.4f", costNew) + "," + String.format("%.4f", valueNew);
      tempStream.write(line);
      tempStream.writeTo(bw);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    File file = new File(address);
    file.delete();
    File fileNew = new File(address);
    File newFile = new File(addressTemp);
    newFile.renameTo(fileNew);
  }


}
