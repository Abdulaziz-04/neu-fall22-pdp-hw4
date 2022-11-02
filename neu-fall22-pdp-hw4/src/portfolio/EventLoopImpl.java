package portfolio;

import java.util.Scanner;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.views.View;

/**
 * This is class represent event loop, which will continue to run the program.
 */
public class EventLoopImpl implements EventLoop {
  private final Scanner scan = new Scanner(System.in);

  private PageController pageController;

  /**
   * This is a constructor to construct a EventLoopImpl object.
   *
   * @param pageControllerFactory the controller factory to run the program
   */
  public EventLoopImpl(PageControllerFactory pageControllerFactory) {
    this.pageController = pageControllerFactory.newMainPageController();
  }

  @Override
  public void run() {
    while (true) {
      // Render the page
      View view = pageController.getView();
      view.render();

      // Receive for user command and redirect to next page
      pageController = pageController.handleInput(scan.nextLine());
    }
  }
}
