package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.ViewFactory;
import portfolio.views.View;

import static java.lang.System.exit;

/**
 * This is a controller for handling the first page. It implements PageController.
 * MainPageController handles input from user and is responsible for redirecting user to others
 * PageController.
 */
public class MainPageController implements PageController {

  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean isInitFailed;

  /**
   * This is a constructor that construct a main menu page controller.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public MainPageController(PortfolioModel portfolioModel, ViewFactory viewFactory) {
    this.viewFactory = viewFactory;
    this.portfolioModel = portfolioModel;
    try {
      // Init cache
      portfolioModel.init();
      isInitFailed = false;
    } catch (Exception e) {
      isInitFailed = true;
    }
  }

  @Override
  public View getView() {
    return viewFactory.newMainPageView(errorMessage, isInitFailed);
  }

  @Override
  public PageController handleInput(String input) {
    errorMessage = null;
    switch (input) {
      case "1":
        return new InflexibleCreatePageController(portfolioModel, viewFactory);
      case "2":
        return new FlexibleCreatePageController(portfolioModel, viewFactory);
      case "3":
        return new LoadPageController(portfolioModel, viewFactory);
      default:
        errorMessage = "Please enter the correct number!";
        return this;
    }
  }
}
