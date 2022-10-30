package portfolio.controllers.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.entities.Portfolio;
import portfolio.entities.StockListEntry;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.View;
import portfolio.views.impl.CreatePageView;

public class CreatePageController implements PageController {

  private final StockQueryService stockQueryService;
  private final PortfolioService portfolioService;
  private final PageControllerFactory pageControllerFactory;
  String errorMessage;
  Map<String, Integer> stockList = new HashMap<>();

  public CreatePageController(StockQueryService stockQueryService,
      PortfolioService portfolioService, PageControllerFactory controllerFactory) {
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
    this.pageControllerFactory = controllerFactory;
  }

  @Override
  public View getView() {
    return new CreatePageView(stockList, errorMessage);
  }

  @Override
  public PageController handleCommand(String command) throws Exception {
    command = command.trim();
    errorMessage = "";
    if (command.equals("end")) {
      Portfolio portfolio = new Portfolio(stockList);
      portfolioService.saveToFile(portfolio, LocalDateTime.now() + ".txt");
      return pageControllerFactory.newInfoPageController(portfolio);
    }

    List<String> allStocks = new ArrayList<>();
    for (StockListEntry entry : stockQueryService.getStockList()) {
      allStocks.add(entry.getSymbol());
    }

    try {
      String[] cmd = command.split(",");
      String symbol = cmd[0];
      int amount = Integer.parseInt(cmd[1]);
      if (allStocks.contains(symbol)) {
        stockList.put(symbol, stockList.getOrDefault(symbol, 0) + amount);
      } else {
        errorMessage = "Symbol not found.";
        return this;
      }
    } catch (Exception e) {
      errorMessage = "error!";
      return this;
    }
    return this;
  }

}
