package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.entities.Portfolio;
import portfolio.services.PortfolioService;
import portfolio.services.impl.PortfolioStore;
import portfolio.views.LoadPageView;

public class LoadPageController implements PageController {

  private PortfolioStore portfolioStore;
  private PortfolioService portfolioService;
  private LoadPageView loadPageView;
  private String errorMessage;

  public LoadPageController(LoadPageView loadPageView, PortfolioService portfolioService, PortfolioStore portfolioStore){
    this.portfolioService = portfolioService;
    this.loadPageView = loadPageView;
    this.portfolioStore = portfolioStore;
  }
  @Override
  public void render() {

  }

  @Override
  public Page gotCommand(String command) throws Exception {
    try {
      Portfolio portfolio = portfolioService.getPortfolio(command);
      portfolioStore.setPortfolio(portfolio);
      return Page.PORTFOLIO_INFO;
    }
    catch (Exception e){
      errorMessage = "Error! Cannot load file. Please try again.";
      return null;
    }
  }
}
