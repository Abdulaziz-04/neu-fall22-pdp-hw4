package portfolio.controllers.impl;

import java.io.InputStream;
import java.util.Scanner;

import portfolio.controllers.FrontController;
import portfolio.controllers.PageController;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is class represent event loop, which will continue to run the program.
 * This class implements the FrontController interface.
 */
public class FrontControllerImpl implements FrontController {

  private final Scanner scan;
  private PageController pageController;

  /**
   * This is a constructor to construct a EventLoopImpl object.
   */
  public FrontControllerImpl(PortfolioModel portfolioModel, ViewFactory viewFactory, InputStream inputStream) {
    this.pageController = new MainPageController(portfolioModel, viewFactory);
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
