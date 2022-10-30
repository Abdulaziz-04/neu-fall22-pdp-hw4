package portfolio.controllers.impl;

import java.util.Scanner;
import portfolio.controllers.FrontController;
import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.services.PortfolioService;
import portfolio.services.PortfolioValueService;
import portfolio.services.StockQueryService;
import portfolio.services.impl.PortfolioStore;
import portfolio.views.CreatePageView;
import portfolio.views.InfoPageView;
import portfolio.views.LoadPageView;
import portfolio.views.MainPageView;

public class FrontControllerImpl implements FrontController {

  // Views
  MainPageView mainMenuView;
  CreatePageView createMenuView;
  LoadPageView loadPageView;
  InfoPageView infoPageView;

  // Services
  PortfolioService portfolioService;
  StockQueryService stockQueryService;
  PortfolioValueService portfolioValueService;

  // Storage and entities
  PortfolioStore portfolioStore = new PortfolioStore();
  PageController pageController;
  Scanner scan = new Scanner(System.in);

  public FrontControllerImpl(MainPageView mainMenuView, CreatePageView createMenuView,
      LoadPageView loadPageView, InfoPageView infoPageView,
      PortfolioService portfolioService, StockQueryService stockQueryService,
      PortfolioValueService portfolioValueService) {
    this.mainMenuView = mainMenuView;
    this.createMenuView = createMenuView;
    this.loadPageView = loadPageView;
    this.infoPageView = infoPageView;
    this.pageController = new MainPageController(mainMenuView);
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
    this.portfolioValueService = portfolioValueService;
  }

  @Override
  public void run() throws Exception {
    while (true) {
      // Render the page
      pageController.render();

      // Waiting for user command
      Page nextPage = pageController.gotCommand(scan.nextLine());

      // Stay on the same page
      if (nextPage == null) {
        continue;
      }

      // Redirect user to the next page
      switch (nextPage) {
        case MAINMENU:
          pageController = new MainPageController(mainMenuView);
          break;
        case CREATE_PORTFOLIO:
          pageController = new CreatePageController(createMenuView, stockQueryService,
              portfolioStore, portfolioService);
          break;
        case LOAD_PORTFOLIO:
          pageController = new LoadPageController(loadPageView, portfolioService, portfolioStore);
          break;
        case PORTFOLIO_INFO:
          pageController = new InfoPageController(infoPageView, portfolioValueService, portfolioStore);
          break;
        case EXIT:
          return;
      }
    }
  }
}
