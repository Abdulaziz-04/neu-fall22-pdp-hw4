package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.views.View;
import portfolio.views.impl.MainPageView;

/**
 * This is a page controller for the main menu page, which is implement the page controller.
 */
public class MainPageController implements PageController {

  private final PageControllerFactory controllerFactory;
  private String errorMessage;

  /**
   * This is a constructor that construct a main menu page controller
   *
   * @param controllerFactory the controller factory that we will use
   */
  public MainPageController(PageControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  @Override
  public View getView() {
    return new MainPageView(errorMessage);
  }

  public PageController handleCommand(String command) {
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
