package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import helper.LoadFile;
import helper.SearchFile;

/**
 * This abstract class represents a Portfolio.
 */
public abstract class AbstractPortfolio implements PortfolioModel {

  private Map<String, Double> stocks;

  private String name;


  /**
   * Construct a Portfolio with given data.
   *
   * @param stocks a map of stocks' symbols and numbers of them
   * @param name   a name if this Portfolio
   */
  public AbstractPortfolio(Map<String, Double> stocks, String name) {
    this.stocks = stocks;
    this.name = name;
  }

  /**
   * Construct a Portfolio with given data.
   */
  public AbstractPortfolio() {
    // empty constructor
  }

  @Override
  public Map<String, Double> getMap() {
    return stocks;
  }

  @Override
  public void setMap(Map<String, Double> map) {
    this.stocks = map;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String getName() {
    return name;
  }


  @Override
  public String getInfo() {
    StringJoiner s = new StringJoiner("\n");
    String result;
    s.add("Portfolio name is : " + name);
    // System.out.println("Portfolio name is : " + name);
    for (Map.Entry<String, Double> entry : stocks.entrySet()) {
      s.add("stock ticker symbol is : " + entry.getKey());
      s.add("stock quantity is : " + entry.getValue());
      // System.out.println("stock ticker symbol is ： " + entry.getKey());
      // System.out.println("stock quantity is ： " + entry.getValue());
    }
    result = s.toString();
    return result;
  }

  @Override
  public void savePortfolio(String address) {
    try (
            Writer fw = new FileWriter(address + "/" + name + "_Portfolio.txt");
            BufferedWriter bw = new BufferedWriter(fw);
    ) {
      bw.write(name);
      bw.newLine();
      for (Map.Entry<String, Double> entry : stocks.entrySet()) {
        bw.write(entry.getKey());
        bw.write(":");
        bw.write(String.valueOf(entry.getValue()));
        bw.write(",");
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public String getValue(String date, String address) {
    double finalValue = 0;
    for (Map.Entry<String, Double> entry : stocks.entrySet()) {
      double value;
      SingleStock s = new SingleStock(entry.getKey(), address, address);
      s.setCsv();
      value = s.getValue(date, entry.getValue());
      if (value == -1.0 || value == -2.0 || value == -3.0) {
        String v = String.valueOf(value);
        return v;
      } else {
        finalValue = finalValue + value;
      }
    }
    String f = String.format("%.4f", finalValue);
    return f;
  }

  @Override
  public List<String> checkPortfolio(String target) {
    return SearchFile.search(target);
  }


  @Override
  public void loadPortfolio(String address, String user, String name) {
    LoadFile.duplicateFile(address, user, name);
  }
}
