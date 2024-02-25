package view;

import java.util.List;

/**
 * This interface has all the method with signatures for displaying the messages
 * in console.
 */
public interface IView {
  /**
   * Input name of the user to keep track of the portfolio.
   */
  void showName();

  /**
   * Show all the options that user can choose to do with stock portfolios - inflexible.
   */
  void showOptions();

  /**
   * Show the valid name that user entered.
   */
  void showViewName(String name);

  /**
   * Show all the portfolios that users have.
   */
  void showPortfolioList(List<String> list);

  /**
   * Ask of what portfolio want to view.
   */
  void showPortfolioQuestion();

  /**
   * Question of what date want to view.
   */
  void showDateQuestion();

  /**
   * Show the name of portfolio.
   */
  void showPortfolioName(String portName);

  /**
   * Show the date that was entered.
   */
  void showDateChosen(String date);

  /**
   * Show the value of the portfolio.
   */
  void showDTotalValue(String value);

  /**
   * Show the value of the portfolio.
   */
  void showStockComposition();

  /**
   * Crete the portfolio name.
   */
  void createPortName();


  /**
   * Add stock name.
   */
  void addStock();

  /**
   * Add shares of stock owned.
   */
  void addShares();

  /**
   * saved to a file.
   */
  void saveToFile();

  /**
   * question to ask for adding another stock.
   */
  void addAnotherStock();

  /**
   * ask to load the file that user creates.
   */
  void loadFile();

  /**
   * Error shows when user enter a invalid option.
   */
  void showOptionError();

  /**
   * Error shows when user enters an invalid stock ticker symbol.
   */
  void showStockLabelInvalidError();

  /**
   * Error shows when user loads a file in format other than json.
   */
  void showInvalidFileFormatError();

  /**
   * Error shows when user enters a date in wrong format or an invalid date.
   */
  void showDateInvalidError();

  /**
   * Error shows when user enters a date that is not within the range.
   */
  void showDateInvalidError2();

  /**
   * Error shows when user enters a name of portfolio that is wrong or doesn't exist - maybe typo.
   */
  void showPortError();

  /**
   * Error shows when number of shares entered in fractional(not an integer), or is empty.
   */
  void showSharesError();

  /**
   * Shows when the load of file is successful.
   */
  void loadSuccess();

  // newly added for stock(2)

  /**
   * ask if user want to view performance.
   */
  void fViewPerformanceQn();

  /**
   * ask the time range - start.
   */
  void fTimeRangeStart();

  /**
   * ask the time range - end.
   */
  void fTimeRangeEnd();

  /**
   * ask the time range - end.
   */
  void eCostBasis();

  /**
   * ask the time range - end.
   */
  void dPortOperation();

  /**
   * ask if user want to delete or add stock.
   */
  void deleteOrAdd();

  /**
   * ask user what stock they want to delete.
   */
  void dStockDelete();

  /**
   * error message when stock want to delete is not in portfolio.
   */
  void showDeleteError();

  /**
   * status message for delete success.
   */
  void deleteSuccess();

  /**
   * the chart title shown.
   */
  void showChartTitle(String portfolioName, String startDate, String endDate);

  /**
   * ask user which type of portfolio they want to create.
   */
  void enterFI();

  /**
   * error showed when user chose invalid option.
   */
  void errorFirstQn();

  /**
   * error showed when user didn't should to add to delete or quit.
   */
  void errorAddDeleteNone();

  /**
   *  how many shares want to delete.
   */
  void deleteShares();

  /**
   *  how many shares want to delete.
   */
  void deleteSharesInsufficient();

  /**
   *  ask do you want to delete another stock.
   */
  void deleteAnotherStock();

  /**
   *  display the total basis cost.
   */
  void costBasisDisplay(double cost);

  /**
   *  start date is invalid(-2).
   */
  void errorDate1();

  /**
   *  end date is invalid(-3).
   */
  void errorDate2();

  /**
   *  end date is invalid(range not good).
   */
  void errorDate3();

  /**
   *  note for displaying flexible total value.
   */
  void noteFlex();

  /**
   *  note for displaying flexible total value.
   */
  void noteInflexible();
}
