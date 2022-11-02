package portfolio;

import java.util.Scanner;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.impl.MainPageController;
import portfolio.views.View;
import portfolio.views.ViewFactory;

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
   * @param viewFactory the view factory to run the program
   */
  public EventLoopImpl(PageControllerFactory pageControllerFactory, ViewFactory viewFactory) {
    this.pageController = new MainPageController(pageControllerFactory, viewFactory);
  }

  @Override
  public void run() throws Exception {
    while (true) {
      // Render the page
      View view = pageController.getView();
      view.render();

      // Receive for user command and redirect to next page
      pageController = pageController.handleCommand(scan.nextLine());
    }
  }
}
