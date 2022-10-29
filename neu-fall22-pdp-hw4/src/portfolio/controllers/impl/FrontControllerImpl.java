package portfolio.controllers.impl;

import java.util.Scanner;
import portfolio.controllers.FrontController;
import portfolio.entities.Portfolio;
import portfolio.services.PortfolioService;
import portfolio.views.View;

public class FrontControllerImpl implements FrontController {

  View view;
  PortfolioService portfolioService;
  Portfolio portfolio;

  public FrontControllerImpl(View view, PortfolioService portfolioService) {
    this.view = view;
    this.portfolioService = portfolioService;
  }

  @Override
  public void run() {
    while (true) {
      view.showMenu();
      Scanner scan = new Scanner(System.in);
      int option;
      option = scan.nextInt();
      if(option == 1) {


      } else if (option == 2){

      } else if (option == 3) {

      } else if (option == 4) {
        view.showGetPortfolio(); // Please input file name
        Scanner s2 = new Scanner(System.in);
        String str = s2.nextLine();
        portfolio = portfolioService.getPortfolio(str);
        view.showPortfolio(portfolio);
      } else {
        view.showError();
      }
    }
  }
}
