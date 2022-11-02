package portfolio.controllers.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a controller for the determining portfolio value. It implements PageController.
 * InfoPageController handles input from user and is responsible for getting portfolio value of the
 * input date.
 */
public class InfoPageController implements PageController {

  private final StockQueryService stockQueryService;
  private final PageControllerFactory controllerFactory;
  private final ViewFactory viewFactory;
  private final Portfolio portfolio;
  private String errorMessage;
  private PortfolioWithValue portfolioWithValue;

  /**
   * This is a constructor that construct a InfoPageController, which is for determining a portfolio
   * on a certain date.
   *
   * @param stockQueryService the stock price data that we get from the external source
   * @param portfolio         the portfolio that we want to determine
   * @param controllerFactory PageControllerFactory for creating PageController
   * @param viewFactory       ViewFactor for creating a view
   */
  public InfoPageController(StockQueryService stockQueryService, Portfolio portfolio,
      PageControllerFactory controllerFactory, ViewFactory viewFactory) {
    this.portfolio = portfolio;
    this.stockQueryService = stockQueryService;
    this.controllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
  }

  /**
   * This is a helper function that to help us the get the portfolio value from the model.
   *
   * @param date the date that we want to determine
   * @throws Exception cannot get the price on that date
   */
  private void updatePortfolioWithValue(LocalDate date) throws Exception {
    var prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
    portfolioWithValue = portfolio.getPortfolioWithValue(date, prices);
  }

  @Override
  public View getView() {
    return viewFactory.newInfoPageView(portfolioWithValue, errorMessage);
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
      return controllerFactory.newMainPageController();
    }
    try {
      LocalDate date = LocalDate.parse(input);
      updatePortfolioWithValue(date);
    } catch (DateTimeParseException e) {
      errorMessage = "Error! Please input the correct date.";
    } catch (Exception e) {
      errorMessage = e.getMessage();
    }
    return this;
  }
}
