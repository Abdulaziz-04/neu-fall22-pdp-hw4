package portfolio.controllers.gui;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a controller for the retrieving portfolio. It implements SwingPageController.
 * LoadPageController handles input from user and is responsible for retrieving portfolio and
 * creating a view to show portfolio content.
 */
public class ScheduleInfoSwingController implements SwingPageController {

  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  private String errorMessage;

  private BuySchedule schedule;

  /**
   * This is a constructor that construct a LoadPageController, which is examining the composition
   * of a portfolio.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public ScheduleInfoSwingController(String name, PortfolioModel portfolioModel, ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
    List<BuySchedule> schedules = portfolioModel.getPortfolio().getBuySchedules();
    Optional<BuySchedule> option = schedules.stream().filter(x -> Objects.equals(x.getName(), name)).findFirst();
    if (option.isPresent()) {
      this.schedule = option.get();
    }
    else {
      errorMessage = "No schedule name: " + name;
    }

  }

  @Override
  public View getView() {
    return viewFactory.newScheduleInfoPageView(schedule, errorMessage);
  }

  /**
   * Handle user input for loading portfolio.
   * First, get the name of portfolio from GUI text filed.
   * Second, return different page according to the action command send to controller.
   * (diifferent: add a switch to return new feature page)
   *
   * @param input the action command send from GUI
   * @return PageController as a next page to be redirected
   */
  @Override
  public SwingPageController handleInput(String input) {
    input = input.trim();
    errorMessage = null;
    if (input.equals("back")) {
      return new LoadPageSwingController(portfolioModel, viewFactory);
    }
    return this;
  }
}
