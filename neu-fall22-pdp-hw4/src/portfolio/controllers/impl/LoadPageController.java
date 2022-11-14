package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a controller for the retrieving portfolio. It implements PageController.
 * LoadPageController handles input from user and is responsible for retrieving portfolio and
 * creating a view to show portfolio content.
 */
public class LoadPageController implements PageController {
  private final IOService ioService = new FileIOService();
  private final PortfolioModel portfolioModel;
  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private Portfolio portfolio;

  /**
   * This is a constructor that construct a LoadPageController, which is examining the composition
   * of a portfolio.
   *
   * @param portfolioModel  the service for portfolio
   * @param controllerFactory PageControllerFactory for creating PageController
   * @param viewFactory       ViewFactor for creating a view
   */
  public LoadPageController(PortfolioModel portfolioModel,
      PageControllerFactory controllerFactory, ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
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
      if (portfolioModel.getPortfolio() == null) {
        //get portfolio
        String str = ioService.read(input + ".txt");
        portfolioModel.load(input, str);
        portfolio = portfolioModel.getPortfolio();
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
