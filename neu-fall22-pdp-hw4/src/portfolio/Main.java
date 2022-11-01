package portfolio;

import portfolio.controllers.PageControllerFactory;
import portfolio.services.datastore.FileIOService;
import portfolio.services.datastore.IOService;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.portfolio.PortfolioServiceImpl;
import portfolio.services.stockprice.AlphaVantageApi;
import portfolio.services.stockprice.StockPriceApi;
import portfolio.services.stockprice.StockQueryService;
import portfolio.services.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;
import portfolio.views.impl.DefaultSysOutViewFactory;

public class Main {

  public static void main(String[] args) throws Exception {

    // Service
    IOService ioService = new FileIOService();
    StockPriceApi stockPriceApi = new AlphaVantageApi();
    StockQueryService stockQueryService = new StockQueryServiceImpl(stockPriceApi);
    PortfolioService portfolioService = new PortfolioServiceImpl(ioService);
    ViewFactory viewFactory = new DefaultSysOutViewFactory();
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService, stockQueryService, viewFactory);

    // Init cache
    stockQueryService.getStockList();

    // Run
    EventLoop eventLoop = new EventLoopImpl(pageControllerFactory, viewFactory);
    eventLoop.run();
  }

}
