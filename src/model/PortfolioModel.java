package model;

import java.util.List;
import java.util.Map;

/**
 * This interface represents PortfolioModel which consist by several single stocks.
 */
public interface PortfolioModel {

  /**
   * to set stocks.
   *
   * @param map map to set
   */
  public void setMap(Map<String, Double> map);

  /**
   * To get the map of stocks.
   *
   * @return map
   */
  public Map<String, Double> getMap();


  /**
   * to set name.
   *
   * @param name name to set
   */
  public void setName(String name);


  /**
   * Return the name of this Portfolio.
   *
   * @return the name of this Portfolio
   */
  public String getName();

  /**
   * Print the information of Portfolio. Include name, symbol, date and value.
   */
  String getInfo();


  /**
   * to save this portfolio in target folder.
   *
   * @param address the folder you want to save portfolio
   */
  void savePortfolio(String address);


  /**
   * to get the value of this portfolio, four decimal places.
   *
   * @param date    the date you want know the value
   * @param address address with target file
   * @return value of this portfolio
   */
  String getValue(String date, String address);


  /**
   * to determine whether the portfolio is exists.
   *
   * @param target the portfolio path
   * @return list of string with existing portfolio
   */
  List<String> checkPortfolio(String target);


  /**
   * to duplicate target file to our default data file.
   *
   * @param address the address of target file
   * @param user    the Username
   * @param name    the stock symbol of target file
   */
  public void loadPortfolio(String address, String user, String name);


}
