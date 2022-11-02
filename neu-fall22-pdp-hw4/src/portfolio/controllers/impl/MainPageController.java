package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a page controller for the main menu page, which is implement the page controller.
 */
public class MainPageController implements PageController {

  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean isInitFailed;

  /**
   * This is a constructor that construct a main menu page controller.
   *
   * @param controllerFactory the controller factory that we will use
   */
  public MainPageController(StockQueryService stockQueryService, PageControllerFactory controllerFactory, ViewFactory viewFactory) {
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

    switch (input) {
      case "1":
        return controllerFactory.newCreatePageController();
      case "2":
        return controllerFactory.newLoadPageController();
      default:
        errorMessage = "Please enter the correct number!";
        return this;
    }
  }
}
