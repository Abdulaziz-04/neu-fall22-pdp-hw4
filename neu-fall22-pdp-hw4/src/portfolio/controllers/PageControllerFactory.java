package portfolio.controllers;

import portfolio.controllers.impl.CreatePageController;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.entities.Portfolio;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.stockprice.StockQueryService;

public class PageControllerFactory {

  private final PortfolioService portfolioService;
  private final StockQueryService stockQueryService;

  public PageControllerFactory(
      PortfolioService portfolioService, StockQueryService stockQueryService) {
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
  }

  public PageController newInfoPageController(Portfolio portfolio){
    return new InfoPageController(stockQueryService, portfolio, this);
  }

  public PageController newCreatePageController(){
    return new CreatePageController(stockQueryService, portfolioService, this);
  }

  public PageController newLoadPageController(){
    return new LoadPageController(portfolioService, this);
  }

}
