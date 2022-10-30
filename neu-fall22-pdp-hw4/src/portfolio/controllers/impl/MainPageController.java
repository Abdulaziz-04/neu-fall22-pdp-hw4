package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.views.MainPageView;

public class MainPageController implements PageController {

  MainPageView view;
  String errorMessage;

  public MainPageController(MainPageView view) {
    this.view = view;
  }

  public void render() {
    view.print(errorMessage);
  }

  public Page gotCommand(String command) {
    switch (command) {
      case "1":
        return Page.CREATE_PORTFOLIO;
      case "2":
        return Page.LOAD_PORTFOLIO;
      case "3":
        return Page.PORTFOLIO_INFO;
      default:
        errorMessage = "error!";
        return null;
    }
  }
}
