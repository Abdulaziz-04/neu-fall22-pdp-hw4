package portfolio.controllers;

import portfolio.controllers.impl.CreatePageController;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.entities.Portfolio;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.ViewFactory;

/**
 * This is a class that can generate different controller.
 */
public class PageControllerFactory {

  private final PortfolioService portfolioService;
  private final StockQueryService stockQueryService;
  private final ViewFactory viewFactory;

  /**
   * This is a constructor that construct a page controller factory.
   *
   * @param portfolioService the service for portfolio
   * @param stockQueryService the service for get stock list
   */
  public PageControllerFactory(
      PortfolioService portfolioService, StockQueryService stockQueryService, ViewFactory viewFactory) {
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
    this.viewFactory = viewFactory;
  }

  /**
   * Return a new page controller,which is to determine a portfolio on certain date.
   *
   * @param portfolio the portfolio that we want to determine.
   * @return a new page controller,which is to determine a portfolio on certain date
   */
  public PageController newInfoPageController(Portfolio portfolio){
    return new InfoPageController(stockQueryService, portfolio, this, viewFactory);
  }

  /**
   * Return a new page controller,which is to create the portfolio.
   *
   * @return a new page controller,which is to create the portfolio.
   */
  public PageController newCreatePageController(){
    return new CreatePageController(stockQueryService, portfolioService, this, viewFactory);
  }

  /**
   * Return a new page controller,which is to examine the composition of a portfolio.
   *
   * @return a new page controller,which is to examine the composition of a portfolio.
   */
  public PageController newLoadPageController(){
    return new LoadPageController(portfolioService, this, viewFactory);
  }

  /**
   * Return a new page controller,which is to the main menu.
   *
   * @return a new page controller,which is to the main menu.
   */
  public PageController newMainPageController(){
    return new MainPageController(stockQueryService, this, viewFactory);
  }

}
