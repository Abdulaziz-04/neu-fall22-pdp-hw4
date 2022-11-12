package portfolio;

import portfolio.controllers.FrontController;
import portfolio.controllers.impl.FrontControllerImpl;
import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.portfolio.impl.PortfolioServiceImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.AlphaVantageApi;
import portfolio.models.stockprice.StockPriceApi;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;
import portfolio.views.impl.DefaultSysOutViewFactory;

/**
 * This is main class to run the stock program. It will build the model, view and controller.
 */
public class Main {

  /**
   * Main function to run the stock program.
   *
   * @param args arguments
   * @throws Exception error
   */
  public static void main(String[] args) throws Exception {

    // Service
    StockPriceApi stockPriceApi = new AlphaVantageApi();
    StockQueryService stockQueryService = new StockQueryServiceImpl(stockPriceApi);
    PortfolioParser portfolioParser = new PortfolioTextParser();
    PortfolioService portfolioService = new PortfolioServiceImpl(stockQueryService, portfolioParser);

    ViewFactory viewFactory = new DefaultSysOutViewFactory();
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService,
        stockQueryService, portfolioParser, viewFactory);

    // Run
    FrontController frontController = new FrontControllerImpl(pageControllerFactory);
    frontController.run();
  }

}
