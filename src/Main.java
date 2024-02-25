import controller.Controller;
import controller.IController;
import model.Portfolio;
import view.IView;
import view.View;

/**
 * this is the main class to run the program.
 */
public class Main {
  /**
   * This is the main method to execute the program.
   * @param args args parameter for executing
   */
  public static void main(String[] args) {
    Portfolio model = new Portfolio();
    IView view = new View(System.out);
    IController controller = new Controller(model, System.in, view, System.out);
    controller.goMain();
  }
}
