import controller.ControllerNew;
import model.FlexiblePortfolio;
import model.PortfolioModel;
import view.JFrameView;

/**
 * This is the main class for running the updated model with new GUI view.
 */
public class MainNew {

  /**
   * Main method for the class to run the program.
   * @param args args parameter that is required for main
   */
  public static void main(String[] args) {
    PortfolioModel model = new FlexiblePortfolio();
    JFrameView view = new JFrameView("Portfolio management");
    ControllerNew controller = new ControllerNew(model, view);
  }
}
