package model;


import java.util.Map;


/**
 * This class represents a Portfolio.
 */
public class Portfolio extends AbstractPortfolio {

  /**
   * Construct a Portfolio with given data.
   *
   * @param stocks a map of stocks' symbols and numbers of them
   * @param name   a name if this Portfolio
   */
  public Portfolio(Map<String, Double> stocks, String name) {
    super(stocks,name);

  }

  /**
   * Construct a Portfolio with given data.
   */
  public Portfolio() {
  // empty constructor
  }


}
