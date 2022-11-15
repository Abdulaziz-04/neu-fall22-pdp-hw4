package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
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
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean showModifyMenu = false;

  /**
   * This is a constructor that construct a LoadPageController, which is examining the composition
   * of a portfolio.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public LoadPageController(PortfolioModel portfolioModel, ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
    showModifyMenu =
            portfolioModel.getPortfolio() != null && !portfolioModel.getPortfolio().isReadOnly();
  }

  @Override
  public View getView() {
    return viewFactory.newLoadPageView(portfolioModel.getPortfolio(),
            showModifyMenu, errorMessage);
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
      return new MainPageController(portfolioModel, viewFactory);
    }
    try {
      if (portfolioModel.getPortfolio() == null) {
        //get portfolio
        String str = ioService.read(input + ".txt");
        portfolioModel.load(input, str);
        showModifyMenu = !portfolioModel.getPortfolio().isReadOnly();
        return this;
      } else {
        if (!showModifyMenu && input.equals("3")) {
          errorMessage = "Please input valid number.";
          return this;
        }
        switch (input) {
          case "1":
            return new InfoPageController(portfolioModel, viewFactory);
          case "2":
            return new PerformancePageController(portfolioModel, viewFactory);
          case "3":
            return new FlexibleCreatePageController(portfolioModel, viewFactory);
          default:
            errorMessage = "Please input valid number.";
            return this;
        }
      }
    } catch (Exception e) {
      errorMessage = e.getMessage();
      return this;
    }
  }
}
