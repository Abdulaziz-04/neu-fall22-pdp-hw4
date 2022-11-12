package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.models.portfolio.InflexiblePortfolio;
import portfolio.models.portfolio.PortfolioService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a controller for the retrieving portfolio. It implements PageController.
 * LoadPageController handles input from user and is responsible for retrieving portfolio and
 * creating a view to show portfolio content.
 */
public class LoadPageController implements PageController {

  private final PortfolioService portfolioService;
  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private InflexiblePortfolio portfolio;

  /**
   * This is a constructor that construct a LoadPageController, which is examining the composition
   * of a portfolio.
   *
   * @param portfolioService  the service for portfolio
   * @param controllerFactory PageControllerFactory for creating PageController
   * @param viewFactory       ViewFactor for creating a view
   */
  public LoadPageController(PortfolioService portfolioService,
      PageControllerFactory controllerFactory, ViewFactory viewFactory) {
    this.portfolioService = portfolioService;
    this.controllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
  }

  @Override
  public View getView() {
    return viewFactory.newLoadPageView(portfolio, errorMessage);
  }

  /**
   * Handle user input for loading portfolio. User can enter portfolio name. The method return the
   * next page controller that user should be navigated to.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  @Override
  public PageController handleInput(String input) {
    input = input.trim();
    errorMessage = null;
    if (input.equals("back")) {
      return controllerFactory.newMainPageController();
    }
    try {
      if (portfolio == null) {
        //get portfolio
        portfolio = portfolioService.getPortfolio(input + ".txt");
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
