package portfolio.controllers.impl;

import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.views.MainMenuView;

public class MainMenuController implements PageController {

  MainMenuView view;
  String errorMessage;

  public MainMenuController(MainMenuView view){
    this.view = view;
  }

  public void render(){
    view.print(errorMessage);
  }

  public Page gotCommand(String command){
    if (command.equals("1")){
      return Page.CREATE_PORTFOLIO;
    }
    else if (command.equals("2")){
      return Page.LOAD_PORTFOLIO;
    } else if (command.equals("3")){
      return Page.PORTFOLIO_INFO;
    } else {
      errorMessage = "error!";
      return null;
    }
  }
}
