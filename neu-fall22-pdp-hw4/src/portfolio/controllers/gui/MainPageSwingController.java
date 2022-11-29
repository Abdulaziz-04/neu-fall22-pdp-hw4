package portfolio.controllers.gui;

import portfolio.controllers.PageController;
import portfolio.controllers.impl.FlexibleCreatePageController;
import portfolio.controllers.impl.InflexibleCreatePageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a controller for handling the first page. It implements PageController.
 * MainPageController handles input from user and is responsible for redirecting user to others
 * PageController.
 */
public class MainPageSwingController implements SwingPageController {

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
  public MainPageSwingController(PortfolioModel portfolioModel, ViewFactory viewFactory) {
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
  public SwingPageController handleInput(String input) {
    errorMessage = null;
    switch (input) {
      case "2":
        return new FlexibleCreatePageSwingController(portfolioModel, viewFactory);
      case "3":
        return new LoadPageSwingController(portfolioModel, viewFactory);
      default:
        errorMessage = "Please enter the correct number!";
        return this;
    }
  }
}
