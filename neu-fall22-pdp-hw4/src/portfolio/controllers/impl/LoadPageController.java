package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.services.portfolio.PortfolioService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a page controller for the examine page, which is implement the page controller.
 */
public class LoadPageController implements PageController {

  private final PortfolioService portfolioService;
  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private Portfolio portfolio;

  /**
   * This is a constructor that construct a page controller,which is examining the composition
   * of a portfolio.
   *
   * @param portfolioService the service for portfolio
   * @param controllerFactory the controller factory that we will use
   */
  public LoadPageController(PortfolioService portfolioService, PageControllerFactory controllerFactory, ViewFactory viewFactory){
    this.portfolioService = portfolioService;
    this.controllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
  }

  @Override
  public View getView(){
    return viewFactory.newLoadPageView(portfolio, errorMessage);
  }

  @Override
  public PageController handleInput(String input) {
    input = input.trim();
    if (input.equals("back")) {
      return controllerFactory.newMainPageController();
    }
    try {
      if (portfolio == null) {
        //get portfolio
        portfolio = portfolioService.getPortfolio(input +".txt");
        return this;
      } else {
        if (input.equals("yes")) {
          return controllerFactory.newInfoPageController(portfolio);
        } else {
          return controllerFactory.newMainPageController();
        }
      }
    } catch (Exception e) {
      errorMessage = e.getMessage();
      return this;
    }
  }
}
