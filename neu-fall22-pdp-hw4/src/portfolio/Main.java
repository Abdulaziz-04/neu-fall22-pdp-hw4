package portfolio;

import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.portfolio.PortfolioServiceImpl;
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
    IOService ioService = new FileIOService();
    StockPriceApi stockPriceApi = new AlphaVantageApi();
    StockQueryService stockQueryService = new StockQueryServiceImpl(stockPriceApi);
    PortfolioService portfolioService = new PortfolioServiceImpl(ioService);
    ViewFactory viewFactory = new DefaultSysOutViewFactory();
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService,
        stockQueryService, viewFactory);

    // Run
    EventLoop eventLoop = new EventLoopImpl(pageControllerFactory);
    eventLoop.run();
  }

}
