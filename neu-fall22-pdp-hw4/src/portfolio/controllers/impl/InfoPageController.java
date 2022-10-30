package portfolio.controllers.impl;

import java.time.LocalDate;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.View;
import portfolio.views.impl.InfoPageView;

public class InfoPageController implements PageController {
  private final StockQueryService stockQueryService;
  private final PageControllerFactory controllerFactory;
  private String errorMessage;
  private final Portfolio portfolio;
  private PortfolioWithValue portfolioWithValue;

  public InfoPageController(StockQueryService stockQueryService, Portfolio portfolio, PageControllerFactory controllerFactory){
    this.portfolio = portfolio;
    this.stockQueryService = stockQueryService;
    this.controllerFactory = controllerFactory;
  }

  @Override
  public View getView() {
    if (portfolioWithValue == null) {
      LocalDate date = LocalDate.now().minusDays(2);
      var prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
      portfolioWithValue = portfolio.getPortfolioWithPrice(date, prices);
    }
    return new InfoPageView(portfolio, portfolioWithValue, errorMessage);
  }

  @Override
  public PageController handleCommand(String command) throws Exception {
    try {
      LocalDate date = LocalDate.parse(command);
      var prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
      portfolioWithValue = portfolio.getPortfolioWithPrice(date, prices);
      return this;
    }
    catch (Exception e){
      errorMessage = "Error! Cannot load file. Please try again.";
      return this;
    }
  }
}
