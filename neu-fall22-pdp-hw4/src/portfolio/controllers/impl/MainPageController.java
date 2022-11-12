package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.models.stockprice.StockQueryService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a controller for handling the first page. It implements PageController.
 * MainPageController handles input from user and is responsible for redirecting user to others
 * PageController.
 */
public class MainPageController implements PageController {

  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean isInitFailed;

  /**
   * This is a constructor that construct a main menu page controller.
   *
   * @param stockQueryService StockQueryService
   * @param controllerFactory PageControllerFactory for creating PageController
   * @param viewFactory       ViewFactor for creating a view
   */
  public MainPageController(StockQueryService stockQueryService,
      PageControllerFactory controllerFactory, ViewFactory viewFactory) {
    this.controllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
    try {
      // Init cache
      stockQueryService.getStockList();
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
        return controllerFactory.newInflexibleCreatePageController();
      case "2":
        return controllerFactory.newFlexibleCreatePageController();
      case "3":
        return controllerFactory.newLoadPageController();
      default:
        errorMessage = "Please enter the correct number!";
        return this;
    }
  }
}
