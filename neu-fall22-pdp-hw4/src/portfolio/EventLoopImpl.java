package portfolio;

import java.util.Scanner;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.impl.MainPageController;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.View;

public class EventLoopImpl implements EventLoop {

  // Services
  PortfolioService portfolioService;
  StockQueryService stockQueryService;

  PageControllerFactory pageControllerFactory;

  PageController pageController;
  Scanner scan = new Scanner(System.in);

  public EventLoopImpl(
      PortfolioService portfolioService, StockQueryService stockQueryService, PageControllerFactory pageControllerFactory) {
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
    this.pageControllerFactory = pageControllerFactory;
    this.pageController = new MainPageController(pageControllerFactory);
  }

  @Override
  public void run() throws Exception {
    while (true) {
      // Render the page
      View view = pageController.getView();
      view.render();

      // Waiting for user command
      pageController = pageController.handleCommand(scan.nextLine());
    }
  }
}
