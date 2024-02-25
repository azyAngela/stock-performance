package model;

import java.util.Map;

/**
 * This class represent a PortfolioCreator to create a portfolio object.
 */
public class PortfolioCreator {

  /**
   * constructor of PortfolioCreator.
   */
  public PortfolioCreator() {
    // empty constructor
  }

  /**
   * To build a portfolio object.
   *
   * @param m    the map of stock
   * @param name name of portfolio
   * @param type portfolio type
   * @return a portfolio object
   */
  public PortfolioModel createPortfolio(Map m, String name, String type) {

    try {
      if (type.equals("flexible")) {
        PortfolioModel p = new FlexiblePortfolio(m, name);
        return (PortfolioModel) p;
      } else if (type.equals("inflexible")) {
        PortfolioModel p = new Portfolio(m, name);
        return (PortfolioModel) p;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return null;
  }


}
