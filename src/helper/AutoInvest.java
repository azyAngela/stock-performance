package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import model.FlexiblePortfolio;

/**
 * this is a utility class that can scan the local investment file.
 */
public class AutoInvest {


  /**
   * To scan the local investment file, and check if it is the right date to implemented.
   */
  public static void scanLocal() {
    int count = 1;
    int lineNum;
    int lineNow = 2;
    File f4 = new File("src/resource/UserData/" + "investStrategy");
    f4.mkdirs();
    String path1 = "src/resource/UserData/investStrategy/Dollar-cost.txt";
    File f = new File(path1);
    String line;
    CharArrayWriter tempStream = new CharArrayWriter();
    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(d);
    try {
      if (!f.exists()) {
        f.createNewFile();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (
            LineNumberReader l1 = new LineNumberReader(new FileReader(path1))
    ) {
      l1.skip(Long.MAX_VALUE);
      lineNum = l1.getLineNumber() + 1;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (
            BufferedReader bf = new BufferedReader(new FileReader(path1));

    ) {
      line = bf.readLine();
      if (line == null) {
        line = "Dollar-cost";
        tempStream.write(line);
      } else if (line.equals("Dollar-cost")) {
        tempStream.write(line);
      }
      while ((line = bf.readLine()) != null) {

        String[] result = line.split(",");
        String user = result[0];
        String name = result[1];
        String start = result[2];
        String end = result[3];
        String intervalDays = result[4];
        int intercalDaysTemp = Integer.parseInt(intervalDays);
        String nextInvestDate = result[5];
        String investTemp = result[6];
        double invest = Double.parseDouble(result[6]);
        String action = result[7];
        String type = result[8];
        String feeTemp = result[9];
        double fee = Double.parseDouble(result[9]);
        String content = result[10];
        String path2 = "src/resource/UserData/" + user + "/portfolioData/" + name
                + "_Portfolio.txt";
        String path3 = "src/resource/UserData/" + user + "/portfolioCost/" + name
                + "_PortfolioCost.txt";
        if (type.equals("simple") && nextInvestDate.equals(today)) {
          String[] result2 = content.split("/");
          String content1 = result2[0];
          String[] result3 = content1.split(":");
          String symbol = result3[0];
          String num1 = result3[1];
          double num = Double.parseDouble(num1);
          Map<String, Double> m = LoadFile.getInfoMap(path2);
          FlexiblePortfolio flex = new FlexiblePortfolio(m, name);
          if (action.equals("add")) {
            flex.addStock(symbol, num, user);

          } else if (action.equals("delete")) {
            flex.deleteStock(symbol, num, user);
          }
          flex.updateCost(symbol, num, action, user, fee);
        } else if (type.equals("weight") && nextInvestDate.equals(today) && start.equals(end)) {
          String[] result2 = content.split("/");
          for (int i = 0; i < result2.length; i++) {
            String content1 = result2[i];
            String[] result3 = content1.split(":");
            String symbol = result3[0];
            String num1 = result3[1];
            double weight = Double.parseDouble(num1);
            Map<String, Double> m = LoadFile.getInfoMap(path2);
            FlexiblePortfolio flex = new FlexiblePortfolio(m, name);
            double num = flex.addByWeight(symbol, weight, user, invest);
            flex.updateCost(symbol, num, action, user, fee);

          }
        } else if (type.equals("weight") && !start.equals(end)) {

          if (nextInvestDate.equals(today)) {
            String[] result2 = content.split("/");
            for (int i = 0; i < result2.length; i++) {
              String content1 = result2[i];
              String[] result3 = content1.split(":");
              String symbol = result3[0];
              String num1 = result3[1];
              double weight = Double.parseDouble(num1);
              Map<String, Double> m = LoadFile.getInfoMap(path2);
              FlexiblePortfolio flex = new FlexiblePortfolio(m, name);
              double num = flex.addByWeight(symbol, weight, user, invest);
              flex.updateCost(symbol, num, action, user, fee);
            }
            Calendar now = Calendar.getInstance();
            now.setTime(d);
            now.add(Calendar.DATE, intercalDaysTemp);
            Date d2 = now.getTime();
            String nextInvestDateNew = sdf.format(d2);
            line = user + "," + name + "," + start + "," + end + "," + intervalDays + ","
                    + nextInvestDateNew + ","
                    + investTemp + "," + action + "," + type + "," + feeTemp + "," + content;
            if (end.compareTo(nextInvestDate) > 0 && end.compareTo(nextInvestDateNew) > 0) {
              if (lineNow < lineNum) {
                tempStream.append(System.getProperty("line.separator"));
              }
              tempStream.write(line);

            }

          } else {
            if (lineNow <= lineNum) {
              tempStream.append(System.getProperty("line.separator"));
            }
            tempStream.write(line);
          }
        }
        else {
          if (lineNow <= lineNum) {
            tempStream.append(System.getProperty("line.separator"));
          }
          tempStream.write(line);

        }

        lineNow++;

      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (
            BufferedWriter wf = new BufferedWriter(new FileWriter(path1));
    ) {
      tempStream.writeTo(wf);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


}
