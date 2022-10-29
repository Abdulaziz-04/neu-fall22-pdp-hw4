package portfolio;

import portfolio.controllers.FrontController;
import portfolio.controllers.PortfolioController;
import portfolio.controllers.impl.FrontControllerImpl;
import portfolio.controllers.impl.PortfolioControllerImpl;
import portfolio.services.IOService;
import portfolio.services.PortfolioService;
import portfolio.services.StockQueryService;
import portfolio.services.impl.AlphaVantageAPI;
import portfolio.services.impl.FileIOService;
import portfolio.services.impl.PortfolioServiceImpl;
import portfolio.services.impl.StockQueryServiceImpl;

public class Main {

  public static void main(String[] args) {

    IOService ioService = new FileIOService();
    AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
    StockQueryService stockQueryService = new StockQueryServiceImpl(alphaVantageAPI);
    PortfolioService portfolioService = new PortfolioServiceImpl(ioService, stockQueryService);

    PortfolioController portfolioController = new PortfolioControllerImpl(portfolioService);
    FrontController frontController = new FrontControllerImpl();
  }

}
