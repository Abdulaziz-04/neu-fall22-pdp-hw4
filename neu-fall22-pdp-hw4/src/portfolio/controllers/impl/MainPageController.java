package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.views.View;
import portfolio.views.impl.MainPageView;

public class MainPageController implements PageController {

  private final PageControllerFactory controllerFactory;
  private String errorMessage;

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
        errorMessage = "error!";
        return null;
    }
  }

}
