package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.services.portfolio.PortfolioService;
import portfolio.views.View;
import portfolio.views.impl.LoadPageView;

public class LoadPageController implements PageController {

  private final PortfolioService portfolioService;
  private final PageControllerFactory controllerFactory;
  private String errorMessage;

  public LoadPageController(PortfolioService portfolioService, PageControllerFactory controllerFactory){
    this.portfolioService = portfolioService;
    this.controllerFactory = controllerFactory;
  }
  @Override
  public View getView(){
    return new LoadPageView(errorMessage);
  }

  @Override
  public PageController handleCommand(String command) throws Exception {
    try {
      Portfolio portfolio = portfolioService.getPortfolio(command);
      return controllerFactory.newInfoPageController(portfolio);
    }
    catch (Exception e){
      errorMessage = "Error! Cannot load file. Please try again.";
      return this;
    }
  }
}
