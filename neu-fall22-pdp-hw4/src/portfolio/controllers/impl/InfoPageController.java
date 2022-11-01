package portfolio.controllers.impl;

import java.time.LocalDate;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.View;
import portfolio.views.impl.InfoPageView;

/**
 * This is a page controller for the determine page, which is implement the page controller.
 */

public class InfoPageController implements PageController {
  private final StockQueryService stockQueryService;
  private final PageControllerFactory controllerFactory;
  private final Portfolio portfolio;
  private String errorMessage;
  private PortfolioWithValue portfolioWithValue;

  /**
   * This is a constructor that construct a page controller, which is for determining
   * a portfolio on a certain date.
   *
   * @param stockQueryService the stock price data that we get from the external source
   * @param portfolio the portfolio that we want to determine
   * @param controllerFactory the controller factory that we will use
   */
  public InfoPageController(StockQueryService stockQueryService, Portfolio portfolio, PageControllerFactory controllerFactory){
    this.portfolio = portfolio;
    this.stockQueryService = stockQueryService;
    this.controllerFactory = controllerFactory;
  }

  /**
   * This is a helper function that to help us the get the portfolio value from the model and then
   * @param date the date that we want to determine
   * @throws Exception
   */
  private void updatePortfolioWithValue(LocalDate date) throws Exception {
    var prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
    portfolioWithValue = portfolio.getPortfolioWithPrice(date, prices);
  }
  @Override
  public View getView() {
    /*if (portfolioWithValue == null) {
      LocalDate date = LocalDate.now().minusDays(2);
      updatePortfolioWithValue(date);
    }*/
    return new InfoPageView(portfolio, portfolioWithValue, errorMessage);
  }

  @Override
  public PageController handleCommand(String command) throws Exception {
    if (command.equals("back")) {
      return controllerFactory.newMainPageController();
    }
    try {
      LocalDate date = LocalDate.parse(command);
      updatePortfolioWithValue(date);
      return this;
    } catch (Exception e){
      errorMessage = "Error! Please input the correct date.";
      return this;
    }
  }
}
