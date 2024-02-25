package view;

import java.io.PrintStream;
import java.util.List;

/**
 * This is the view class that implements the IView interface that will display the messages
 * in console.
 */
public class View implements IView {
  private PrintStream out;

  public View(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showName() {
    //    out.println("(NOTE! PLease note that after you enter your name, at " +
    //            "any time in the process you can enter Q to quit the program.)");
    out.println("\nPlease input your name: ");
  }

  @Override
  public void showOptions() {
    out.println("\n" + "What do you want to do with portfolio? ");
    out.println("(Note that if you chose inflexible portfolio, choice D, E, F "
            +
            "are excluded, please choose from the rest options.)");
    out.println("Choice:");
    out.println("A: View existing");
    out.println("B: Create new");
    out.println("C: Load own file");
    out.println("D: Add/Delete from existing");
    out.println("E: View cost basis");
    out.println("F: View portfolio performance chart");
    out.println("Q: Quit the program");
    out.print("Enter your choice: ");
  }

  @Override
  public void showViewName(String name) {
    out.println("\n" + "Name: " + name);
  }

  @Override
  public void showPortfolioList(List<String> list) {
    out.println("\nExisting portfolio: " + list);
  }

  @Override
  public void showPortfolioQuestion() {
    out.println("\nPlease enter the name of portfolio you want to view: ");
  }

  @Override
  public void showDateQuestion() {
    out.println("\n" + "Please enter the date that you want to view the total value for this "
            +
            "portfolio (Please enter the date in the format: 2022-01-13, wrong format "
            +
            "will lead to error): ");
  }

  @Override
  public void showPortfolioName(String portName) {
    out.println("\n" + "Portfolio name: " + portName);
  }

  @Override
  public void showDateChosen(String date) {
    out.println("Date chosen: " + date);
  }

  @Override
  public void showDTotalValue(String value) {
    out.println("Total value: " + value);
  }

  @Override
  public void showStockComposition() {
    out.println("\nStock: ");
    // out.println the stock list that is created in the controller class, in list form?
  }

  @Override
  public void createPortName() {
    out.println("\nPlease enter the name of the portfolio you want to create: ");
  }

  @Override
  public void addStock() {
    out.println("\nWhat stock you want to add into the portfolio? ");
  }

  @Override
  public void addShares() {
    out.println("\nShares you owned? ");
  }

  @Override
  public void saveToFile() {
    out.println("\nSaved to file!");
  }

  @Override
  public void loadSuccess() {
    out.println("\nSuccessfully loaded!");
  }

  @Override
  public void addAnotherStock() {
    out.println("\nDo you want to add another stock? ");
    out.println("Y: yes ");
    out.println("N: No ");
    out.println("Your choice: ");
  }

  @Override
  public void deleteAnotherStock() {
    out.println("\nDo you want to delete another stock? ");
    out.println("Y: yes ");
    out.println("N: No ");
    out.println("Your choice: ");
  }


  @Override
  public void loadFile() {
    out.println("\nFile name to load in (Please enter the address of the file you want "
            +
            "to load in, "
            +
            "under src.) like 'src/...': ");
  }

  @Override
  public void showOptionError() {
    out.print("\nInvalid option. Please try again." + "\n");
  }

  @Override
  public void showStockLabelInvalidError() {
    out.print("\nStock not exist. Please try again." + "\n");
  }

  @Override
  public void showInvalidFileFormatError() {
    out.print("\nUploaded file format is invalid. Please try again." + "\n");
  }

  @Override
  public void showDateInvalidError() {
    out.print("\nEntered date is invalid or wrong format. Please try again." + "\n");
  }

  @Override
  public void showDateInvalidError2() {
    out.print("\nEntered date is the right format, but not within the time range and not able "
            +
            "to check the value. Please change a date." + "\n");
  }

  @Override
  public void errorDate1() {
    out.print("\nStart date invalid." + "\n");
  }

  @Override
  public void errorDate2() {
    out.print("\nEnd date invalid." + "\n");
  }

  @Override
  public void errorDate3() {
    out.print("\nTime range invalid." + "\n");
  }

  @Override
  public void showPortError() {
    out.print("\nEntered portfolio name is wrong or doesn't exist. Please try again." + "\n");
  }

  @Override
  public void showSharesError() {
    out.print("\nEntered shares number should not be fractional. Please try again." + "\n");
  }

  @Override
  public void fViewPerformanceQn() {
    out.print("\nWhich portfolio do you want to view the performance?");
  }

  @Override
  public void fTimeRangeStart() {
    out.println("\nPlease enter the time range you want to view(Please enter the date in the "
            +
            "format: 2022-01-13, wrong format will lead to error).");
    out.println("\nStart date?");
  }

  @Override
  public void fTimeRangeEnd() {
    out.print("\nEnd date?");
  }

  @Override
  public void eCostBasis() {
    out.print("\nBy which date do you want to view the cost basis?");
  }

  @Override
  public void dPortOperation() {
    out.print("\nWhich portfolio do you want to do operation on?");
  }

  @Override
  public void deleteOrAdd() {
    out.println("\nDo you want to add or delete a stock from portfolio? ");
    out.println("ADD: add");
    out.println("DE: delete");
    out.println("Your choice: ");
  }

  @Override
  public void dStockDelete() {
    out.print("\nWhat stock you want to delete from the portfolio?");
  }

  @Override
  public void showDeleteError() {
    out.print("\nDelete failed. Please check if the stock is actually in the portfolio or "
            +
            "symbol spells correctly.");
  }

  @Override
  public void deleteSharesInsufficient() {
    out.print("\nDelete failed. Not enough shares to be deleted. Please try again." + "\n");
  }

  @Override
  public void deleteSuccess() {
    out.print("\nSuccessfully deleted! ");
  }

  @Override
  public void showChartTitle(String portfolioName, String startDate, String endDate) {
    out.print("\nPerformance of portfolio " + portfolioName + " from " + startDate + " to "
            + endDate);
  }

  @Override
  public void enterFI() {
    out.println("\nDo you want to create a flexible or inflexible portfolio? ");
    out.println("A: Flexible");
    out.println("B: Inflexible");
    out.println("Q: Quit the program");
    out.println("Your choice: ");
  }

  @Override
  public void errorFirstQn() {
    out.print("\nPlease choose another option or exit the program to change to flexible "
            +
            "portfolio, because now you chose to operate on inflexible portfolios." + "\n");
  }

  @Override
  public void errorAddDeleteNone() {
    out.print("\nPlease try again. Input not valid." + "\n");
  }


  @Override
  public void deleteShares() {
    out.print("\nHow many shares of this stock do you want to delete?" + "\n");
  }

  @Override
  public void costBasisDisplay(double cost) {
    out.print("\nTotal cost basis(included 2% commission fee): " + cost + "\n");
  }

  @Override
  public void noteFlex() {
    out.print("\nBelow is the value for flexible portfolio - before certain date set to 0. "
            + "\n");
  }

  @Override
  public void noteInflexible() {
    out.print("\nBelow is the value for inflexible portfolio - without considering date added.  "
            + "\n");
  }


}

