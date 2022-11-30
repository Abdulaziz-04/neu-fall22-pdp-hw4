package portfolio;

import java.util.Objects;
import portfolio.controllers.FrontController;
import portfolio.controllers.gui.SwingFrontController;
import portfolio.controllers.impl.FrontControllerImpl;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.ScheduleRunner;
import portfolio.models.portfolio.impl.DollarCostAverageRunner;
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
    ScheduleRunner scheduleRunner = new DollarCostAverageRunner(stockQueryService);
    PortfolioModel portfolioModel = new PortfolioModelImpl(stockQueryService, portfolioParser,
        scheduleRunner);

    // Views
    ViewFactory viewFactory = new DefaultSysOutViewFactory();

    if (args.length > 0 && Objects.equals(args[0], "cli")) {
      FrontController frontController = new FrontControllerImpl(portfolioModel, viewFactory,
          System.in);
      frontController.run();
    }
    else {
      SwingFrontController swingFrontController = new SwingFrontController(portfolioModel);
      swingFrontController.run();
    }
  }

}
