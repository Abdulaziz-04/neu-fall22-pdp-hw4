package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a page controller for the main menu page, which is implement the page controller.
 */
public class MainPageController implements PageController {

  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;

  /**
   * This is a constructor that construct a main menu page controller.
   *
   * @param controllerFactory the controller factory that we will use
   */
  public MainPageController(PageControllerFactory controllerFactory, ViewFactory viewFactory) {
    this.controllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
  }

  @Override
  public View getView() {
    return viewFactory.newMainPageView(errorMessage);
  }

  @Override
  public PageController handleCommand(String command) {
    errorMessage = null;
    switch (command) {
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
