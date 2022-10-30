package portfolio;

import java.util.Scanner;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.impl.MainPageController;
import portfolio.views.View;

public class EventLoopImpl implements EventLoop {
  private final Scanner scan = new Scanner(System.in);

  private PageController pageController;

  public EventLoopImpl(PageControllerFactory pageControllerFactory) {
    this.pageController = new MainPageController(pageControllerFactory);
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
