package portfolio.controllers.impl;

import java.util.Scanner;
import portfolio.controllers.FrontController;
import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.entities.Portfolio;
import portfolio.services.PortfolioService;
import portfolio.views.MainMenuView;

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
    while (true) {
      pageController.render();
      Page nextPage = pageController.gotCommand(scan.nextLine());

      if (nextPage == null) {
        continue;
      }

      switch (nextPage) {
        case MAINMENU:
          pageController = new MainMenuController(view);
          break;
        case EXIT:
          return;
      }
    }
  }
}
