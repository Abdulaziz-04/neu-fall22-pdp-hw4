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

public class Main {

  public static void main(String[] args) throws Exception {

    // Service
    IOService ioService = new FileIOService();
    StockPriceApi stockPriceApi = new AlphaVantageApi();
    StockQueryService stockQueryService = new StockQueryServiceImpl(stockPriceApi);
    PortfolioService portfolioService = new PortfolioServiceImpl(ioService);
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService, stockQueryService);

    EventLoop frontController = new EventLoopImpl(portfolioService, stockQueryService, pageControllerFactory);
    frontController.run();
  }

}
