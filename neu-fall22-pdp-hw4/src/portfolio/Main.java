package portfolio;

import portfolio.controllers.FrontController;
import portfolio.controllers.gui.SwingFrontController;
import portfolio.controllers.impl.FrontControllerImpl;

import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.AlphaVantageApi;
import portfolio.models.stockprice.StockPriceApi;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;
import portfolio.views.gui.SwingViewFactory;
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
//    FrontController frontController = new FrontControllerImpl(portfolioModel, viewFactory,
//        System.in);
    SwingFrontController swingFrontController = new SwingFrontController(portfolioModel);

    // Run controller
//    frontController.run();
    swingFrontController.run();
  }

}
