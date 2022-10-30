package portfolio.controllers.impl;

import java.util.Scanner;
import portfolio.controllers.FrontController;
import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.entities.Portfolio;
import portfolio.services.PortfolioService;
import portfolio.views.CreateMenuView;
import portfolio.views.MainMenuView;
import portfolio.views.impl.CreateMenuViewImpl;

public class FrontControllerImpl implements FrontController {

  MainMenuView view;
  PageController pageController;
  PortfolioService portfolioService;
  Portfolio portfolio;
  Scanner scan = new Scanner(System.in);

  public FrontControllerImpl(MainMenuView view, PortfolioService portfolioService) {
    this.view = view;
    this.portfolioService = portfolioService;
  }

  @Override
  public void run() throws Exception {
    pageController = new MainMenuController(view);
    while (true) {
      //what this means
      pageController.render();
      Page nextPage = pageController.gotCommand(scan.nextLine());

      if (nextPage == null) {
        continue;
      }

      switch (nextPage) {
        case MAINMENU:
          pageController = new MainMenuController(view);
          break;
        case CREATE_PORTFOLIO:
          pageController = new CreateMenuController(new CreateMenuViewImpl());
          break;
        case LOAD_PORTFOLIO:
          pageController = new (view);
          break;
        case PORTFOLIO_INFO:
          pageController = new MainMenuController(view);
          break;
        case EXIT:
          return;
      }
    }
  }
}
