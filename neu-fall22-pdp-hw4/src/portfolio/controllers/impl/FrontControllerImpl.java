package portfolio.controllers.impl;

import java.io.InputStream;
import java.util.Scanner;
import portfolio.controllers.FrontController;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.views.View;

/**
 * This is class represent event loop, which will continue to run the program.
 */
public class FrontControllerImpl implements FrontController {
  private final Scanner scan;
  private PageController pageController;

  /**
   * This is a constructor to construct a EventLoopImpl object.
   *
   * @param pageControllerFactory the controller factory to run the program
   */
  public FrontControllerImpl(PageControllerFactory pageControllerFactory, InputStream inputStream) {
    this.pageController = pageControllerFactory.newMainPageController();
    this.scan = new Scanner(inputStream);
  }

  /**
   * Receive action from the user, render the page and then redirect user to the next page.
   */
  @Override
  public void run() {
    while (true) {
      // Render the page
      View view = pageController.getView();
      view.render();

      // Receive for user command and redirect to next page
      String input = scan.nextLine();
      if (input.equals("exit")) {
        break;
      }
      pageController = pageController.handleInput(input);
    }
  }
}
