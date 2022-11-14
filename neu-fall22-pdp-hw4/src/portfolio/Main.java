package portfolio;

import portfolio.controllers.FrontController;
import portfolio.controllers.impl.FrontControllerImpl;
import portfolio.controllers.PageControllerFactory;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
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

    // Models
    StockPriceApi stockPriceApi = new AlphaVantageApi();
    StockQueryService stockQueryService = new StockQueryServiceImpl(stockPriceApi);
    PortfolioParser portfolioParser = new PortfolioTextParser();
    PortfolioModel portfolioModel = new PortfolioModelImpl(stockQueryService, portfolioParser);

    // Views
    ViewFactory viewFactory = new DefaultSysOutViewFactory();

    // Controller
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioModel,
        stockQueryService, portfolioParser, viewFactory);
    FrontController frontController = new FrontControllerImpl(pageControllerFactory, System.in);

    // Run controller
    frontController.run();
  }

}
