package portfolio.controllers.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.entities.Portfolio;
import portfolio.entities.StockListEntry;
import portfolio.services.PortfolioService;
import portfolio.services.PortfolioStore;
import portfolio.services.StockQueryService;
import portfolio.views.CreateMenuView;

public class CreateMenuController implements PageController {

  CreateMenuView view;
  StockQueryService stockQueryService;
  String errorMessage;
  PortfolioStore portfolioStore;
  PortfolioService portfolioService;
  Map<String, Integer> stockList = new HashMap<>();
  
  public CreateMenuController(CreateMenuView view, StockQueryService stockQueryService, PortfolioStore portfolioStore, PortfolioService portfolioService){
    this.view = view;
    this.stockQueryService = stockQueryService;
    this.portfolioStore = portfolioStore;
  }
  @Override
  public void render() {
    view.print(stockList, errorMessage);
  }

  @Override
  public Page gotCommand(String command) throws Exception {

    if (command == "end") {
      Portfolio portfolio = new Portfolio(stockList);
      portfolioStore.setPortfolio(portfolio);
      portfolioService.saveToFile(portfolio, LocalDateTime.now().toString() + ".txt");
      return Page.PORTFOLIO_INFO;
    }

    List<String> allStocks = new ArrayList<>();
    for (StockListEntry entry: stockQueryService.getStockList()) {
      allStocks.add(entry.getSymbol());
    }

    String symbol;
    Integer amount;

    try {
      String[] cmd = command.split(",");
      symbol = cmd[0];
      amount = Integer.parseInt(cmd[1]);
      if (allStocks.contains(symbol)) {
        stockList.put(symbol, stockList.getOrDefault(symbol,0) + amount);
      }
      else {
        errorMessage = "Symbol not found.";
        return null;
      }
    }
    catch (Exception e) {
      errorMessage = "error!";
      return null;
    }
    return null;
  }
}
