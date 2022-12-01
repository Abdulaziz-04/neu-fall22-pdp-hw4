package portfolio.controllers.gui;

import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;

import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a controller for the retrieving portfolio. It implements SwingPageController.
 * LoadPageController handles input from user and is responsible for retrieving portfolio and
 * creating a view to show portfolio content.
 */
public class LoadPageSwingController implements SwingPageController {

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
  public LoadPageSwingController(PortfolioModel portfolioModel, ViewFactory viewFactory) {
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
   * Handle user input for loading portfolio. First, get the name of portfolio from GUI text filed.
   * Second, return different page according to the action command send to controller. (different:
   * add a switch to return new feature page)
   *
   * @param input the action command send from GUI
   * @return PageController as a next page to be redirected
   */
  @Override
  public SwingPageController handleInput(String input) {
    input = input.trim();
    errorMessage = null;
    if (input.equals("back")) {
      return new MainPageSwingController(portfolioModel, viewFactory);
    }
    if (input.contains("view_schedule_name,")) {
      String[] cmd = input.split(",");
      return new ScheduleInfoSwingController(cmd[1], portfolioModel, viewFactory);
    }
    try {
      if (portfolioModel.getPortfolio() == null) {
        //get portfolio
        String str = ioService.read(input + ".txt");
        portfolioModel.load(input, str);
        ioService.saveTo(portfolioModel.getString(), input + ".txt", true);
        showModifyMenu = !portfolioModel.getPortfolio().isReadOnly();
        return this;
      } else {
        if (!showModifyMenu && input.equals("3")) {
          errorMessage = "Please input valid number.";
          return this;
        }
        switch (input) {
          case "1":
            return new InfoPageSwingController(portfolioModel, viewFactory);
          case "2":
            return new PerformancePageSwingController(portfolioModel, viewFactory);
          case "3":
            return new FlexibleCreatePageSwingController(portfolioModel, viewFactory);
          case "4":
            return new ScheduleCreatePageSwingController(portfolioModel, viewFactory);
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
