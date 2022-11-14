package portfolio.controllers.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.stockprice.StockQueryService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a controller for the determining portfolio value. It implements PageController.
 * InfoPageController handles input from user and is responsible for getting portfolio value of the
 * input date.
 */
public class InfoPageController implements PageController {
  private final PageControllerFactory controllerFactory;
  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private PortfolioWithValue portfolioWithValue;
  private Double costOfBasis;

  /**
   * This is a constructor that construct a InfoPageController, which is for determining a portfolio
   * on a certain date.
   *
   * @param controllerFactory PageControllerFactory for creating PageController
   * @param viewFactory       ViewFactor for creating a view
   */
  public InfoPageController(PortfolioModel portfolioModel, PageControllerFactory controllerFactory, ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.controllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
  }

  @Override
  public View getView() {
    return viewFactory.newInfoPageView(portfolioWithValue, costOfBasis, errorMessage);
  }

  /**
   * Handle user input for determining portfolio value. User can input a date as many times as they
   * want. If the input is 'back', the method will return MainPageController, otherwise return
   * current object.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  @Override
  public PageController handleInput(String input) {
    errorMessage = null;
    if (input.equals("back")) {
      return controllerFactory.newLoadPageController();
    }

    LocalDate date;

    try {
      date = LocalDate.parse(input);

    } catch (DateTimeParseException e) {
      errorMessage = "Error! Please input the correct date.";
      return this;
    }

    try {
      costOfBasis = portfolioModel.getCostBasis(date);
    }
    catch (Exception ignored) {}

    try {
      portfolioWithValue = portfolioModel.getValue(date);
    }
    catch (Exception e) {
      errorMessage = e.getMessage();
    }
    return this;
  }
}
