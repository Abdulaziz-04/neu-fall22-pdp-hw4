package portfolio.controllers;

import portfolio.controllers.impl.FlexibleCreatePageController;
import portfolio.controllers.impl.InflexibleCreatePageController;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.stockprice.StockQueryService;
import portfolio.views.ViewFactory;

/**
 * Factory class for PageController. It can generate different controllers with their dependencies.
 */
public class PageControllerFactory {

  private final PortfolioService portfolioService;
  private final PortfolioParser portfolioParser;
  private final StockQueryService stockQueryService;
  private final ViewFactory viewFactory;

  /**
   * Construct a page controller factory. Constructor takes dependencies of all controllers
   * including PortfolioService, StockQueryService and ViewFactory.
   *
   * @param portfolioService  the service for Portfolio
   * @param stockQueryService the service for getting stock list
   * @param viewFactory       factory for creating View
   */
  public PageControllerFactory(
      PortfolioService portfolioService, StockQueryService stockQueryService, PortfolioParser portfolioParser,
      ViewFactory viewFactory) {
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
    this.portfolioParser = portfolioParser;
    this.viewFactory = viewFactory;
  }

  /**
   * Return a new InfoPageController, which is to determine a portfolio on certain date.
   *
   * @param portfolio the portfolio that we want to determine.
   * @return a new InfoPageController object
   */
  public PageController newInfoPageController(Portfolio portfolio) {
    return new InfoPageController(stockQueryService, portfolio, this, viewFactory);
  }

  /**
   * Return a new CreatePageController, which is to create the portfolio.
   *
   * @return a new CreatePageController object
   */
  public PageController newInflexibleCreatePageController() {
    return new InflexibleCreatePageController(portfolioService, portfolioParser, this, viewFactory);
  }

  public PageController newFlexibleCreatePageController() {
    return new FlexibleCreatePageController(portfolioService, portfolioParser, this, viewFactory);
  }

  /**
   * Return a new LoadPageController, which is to examine the composition of a portfolio.
   *
   * @return a new LoadPageController object
   */
  public PageController newLoadPageController() {
    return new LoadPageController(portfolioService, this, viewFactory);
  }

  /**
   * Return a new MainPageController, which is to the main menu.
   *
   * @return a new MainPageController.
   */
  public PageController newMainPageController() {
    return new MainPageController(stockQueryService, this, viewFactory);
  }

}
