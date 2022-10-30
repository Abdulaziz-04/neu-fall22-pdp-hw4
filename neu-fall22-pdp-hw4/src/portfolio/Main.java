package portfolio;

import portfolio.controllers.FrontController;
import portfolio.controllers.impl.FrontControllerImpl;
import portfolio.services.IOService;
import portfolio.services.PortfolioService;
import portfolio.services.PortfolioValueService;
import portfolio.services.StockQueryService;
import portfolio.services.impl.AlphaVantageAPI;
import portfolio.services.impl.FileIOService;
import portfolio.services.impl.PortfolioServiceImpl;
import portfolio.services.impl.PortfolioValueServiceImpl;
import portfolio.services.impl.StockQueryServiceImpl;
import portfolio.views.CreatePageView;
import portfolio.views.InfoPageView;
import portfolio.views.LoadPageView;
import portfolio.views.MainPageView;
import portfolio.views.impl.CreatePageViewImpl;
import portfolio.views.impl.InfoPageViewImpl;
import portfolio.views.impl.LoadPageViewImpl;
import portfolio.views.impl.MainPageViewImpl;

public class Main {

  public static void main(String[] args) throws Exception {

    // View
    MainPageView mainMenuView = new MainPageViewImpl();
    CreatePageView createMenuView = new CreatePageViewImpl();
    LoadPageView loadPageView = new LoadPageViewImpl();
    InfoPageView infoPageView = new InfoPageViewImpl();

    // Service
    IOService ioService = new FileIOService();
    AlphaVantageAPI alphaVantageAPI = new AlphaVantageAPI();
    StockQueryService stockQueryService = new StockQueryServiceImpl(alphaVantageAPI);
    PortfolioService portfolioService = new PortfolioServiceImpl(ioService);
    PortfolioValueService portfolioValueService = new PortfolioValueServiceImpl(stockQueryService);

    FrontController frontController = new FrontControllerImpl(mainMenuView, createMenuView,
        loadPageView, infoPageView, portfolioService, stockQueryService, portfolioValueService);
    frontController.run();
  }

}
