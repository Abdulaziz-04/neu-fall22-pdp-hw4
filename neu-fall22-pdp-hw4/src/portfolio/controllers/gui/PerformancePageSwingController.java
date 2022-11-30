package portfolio.controllers.gui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import portfolio.controllers.PageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.models.entities.PortfolioPerformance;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a class that represent the performance over time page controller, which implement the
 * PageController interface. PerformancePageController handles input from user and is responsible
 * for performing the performance of a portfolio in a timespan and creating a view to show it
 * performance.
 */
public class PerformancePageSwingController implements SwingPageController {

  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private LocalDate startDate = null;
  private LocalDate endDate = null;

  private PortfolioPerformance portfolioPerformance = new PortfolioPerformance(new HashMap<>(),
      null, Double.parseDouble("0"),Double.parseDouble("0"));
  private boolean isFinish;


  /**
   * This is a constructor that construct a performance page controller.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public PerformancePageSwingController(PortfolioModel portfolioModel, ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
  }

  @Override
  public View getView() {
    return viewFactory.newPerformacePageView(
        portfolioModel.getPortfolio().getName(),
        startDate,
        endDate,
        portfolioPerformance.getPerformance(),
        portfolioPerformance.getScale(),
        isFinish,
        errorMessage);
  }

  /**
   * Handle user input for loading portfolio. User can enter the start date and the end date. The
   * method return the next page controller that user should be navigated to.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  @Override
  public SwingPageController handleInput(String input) {

    input = input.trim();
    errorMessage = null;
    Map<LocalDate, Double> map;

    if (input.equals("back")) {
      return new LoadPageSwingController(portfolioModel, viewFactory);
    }

    if (isFinish) {
      startDate = null;
      endDate = null;
      portfolioPerformance = null;
    }
    isFinish = false;
    if (startDate == null || endDate == null) {
      System.out.println(input);
      String []date = input.split(",");
      try {
        startDate = LocalDate.parse(date[0]);
        System.out.println(startDate);
        map = portfolioModel.getValues(startDate, startDate);
        if (!map.containsKey(startDate)) {
          errorMessage = "Error:"
                  + "The start date maybe the holiday, weekend, or in the future!";
          startDate = null;
          return this;
        }
      } catch (Exception e) {
        errorMessage = "Error date format!";
        startDate = null;
        return this;
      }
      try {
        endDate = LocalDate.parse(date[1]);
        portfolioModel.getValues(startDate, endDate);
        if (endDate.compareTo(LocalDate.now()) > 0) {
          errorMessage = "Error: EndDate cannot be in future.";
          endDate = null;
          return this;
        }
      } catch (Exception e) {
        errorMessage = "Error end date format!";
        endDate = null;
        return this;
      }
      try {
        portfolioPerformance = portfolioModel.getPerformance(startDate, endDate);
        isFinish = true;
      } catch (Exception e) {
        errorMessage = e.getMessage();
        startDate = null;
        endDate = null;
      }
      return this;
    } 
    return this;
  }
}
