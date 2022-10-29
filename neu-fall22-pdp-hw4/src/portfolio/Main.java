package portfolio;

import portfolio.controllers.FrontController;
import portfolio.controllers.impl.FrontControllerImpl;
import portfolio.services.IOService;
import portfolio.services.PortfolioService;
import portfolio.services.StockQueryService;
import portfolio.services.impl.AlphaVantageAPI;
import portfolio.services.impl.FileIOService;
import portfolio.services.impl.PortfolioServiceImpl;
import portfolio.services.impl.StockQueryServiceImpl;
import portfolio.views.View;
import portfolio.views.impl.ViewImpl;

public class Main {
  public static void main(String[] args) {

    IOService ioService = new FileIOService();
    AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
    StockQueryService stockQueryService = new StockQueryServiceImpl(alphaVantageAPI);
    PortfolioService portfolioService = new PortfolioServiceImpl(ioService, stockQueryService);

    View view = new ViewImpl();

    FrontController frontController = new FrontControllerImpl(view, portfolioService);
    frontController.run();
  }

}
