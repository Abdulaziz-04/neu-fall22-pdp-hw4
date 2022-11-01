package portfolio.controllers.impl;

import java.util.ArrayList;
import java.util.List;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntry;
import portfolio.entities.StockListEntry;
import portfolio.services.portfolio.PortfolioService;
import portfolio.views.View;
import portfolio.views.impl.LoadPageView;

/**
 * This is a page controller for the examine page, which is implement the page controller.
 */
public class LoadPageController implements PageController {

  private final PortfolioService portfolioService;
  private final PageControllerFactory controllerFactory;
  private String errorMessage;
  private Portfolio portfolio;

  /**
   * This is a constructor that construct a page controller,which is examining the composition
   * of a portfolio.
   *
   * @param portfolioService the service for portfolio
   * @param controllerFactory the controller factory that we will use
   */
  public LoadPageController(PortfolioService portfolioService, PageControllerFactory controllerFactory){
    this.portfolioService = portfolioService;
    this.controllerFactory = controllerFactory;
  }
  @Override
  public View getView(){
    return new LoadPageView(portfolio, errorMessage);
  }

  @Override
  public PageController handleCommand(String command) throws Exception {
    command = command.trim();
    if (command.equals("back")) {
      return controllerFactory.newMainPageController();
    }
    try {
      if (portfolio == null) {
        //get portfolio
        portfolio = portfolioService.getPortfolio(command+".txt");
        return this;
      } else {
        if (command.equals("yes")) {
          return controllerFactory.newInfoPageController(portfolio);
        } else {
          return controllerFactory.newMainPageController();
        }
      }
    } catch (Exception e) {
      errorMessage = "Error! Cannot load file. Please try again.";
      return this;
    }
  }
}
