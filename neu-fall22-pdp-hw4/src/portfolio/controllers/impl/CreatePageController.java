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

/**
 * This is a page controller for the create page, which is implement the page controller.
 */
public class CreatePageController implements PageController {

  private final StockQueryService stockQueryService;
  private final PortfolioService portfolioService;
  private final PageControllerFactory pageControllerFactory;
  private String errorMessage;
  private Boolean isEnd = false;
  private Boolean isNamed = false;


  private Portfolio portfolio;


  private Map<String, Integer> stockList = new HashMap<>();

  /**
   * This is a constructor to construct a create page controller.
   *
   * @param stockQueryService the stock price data that we get from the external source
   * @param portfolioService the portfolio service
   * @param controllerFactory the controller factory for this controller
   */
  public CreatePageController(StockQueryService stockQueryService,
      PortfolioService portfolioService, PageControllerFactory controllerFactory) {
    this.stockQueryService = stockQueryService;
    this.portfolioService = portfolioService;
    this.pageControllerFactory = controllerFactory;
  }

  @Override
  public View getView() {
    return new CreatePageView(isEnd, isNamed, stockList, errorMessage);
  }

  @Override
  public PageController handleCommand(String command) throws Exception {
    command = command.trim();
    errorMessage = "";
    if(command.equals("back")) {
      return pageControllerFactory.newMainPageController();
    }
    if(!isEnd && !command.equals("end")) {
      List<String> allStocks = new ArrayList<>();
      for (StockListEntry entry : stockQueryService.getStockList()) {
        allStocks.add(entry.getSymbol());
      }
      try {
        String[] cmd = command.split(",");
        String symbol = cmd[0];
        if (cmd.length != 2) {
          errorMessage = "Error Format!";
          return this;
        }
        //int amount = Integer.parseInt(cmd[1]);
        if (allStocks.contains(symbol)) {
          int amount = 0;
          try {
             amount = Integer.parseInt(cmd[1]);
          } catch (Exception e) {
            errorMessage = "The share is not a number.";
            return this;
          }
          if (amount <= 0) {
            errorMessage = "The shares cannot be negative and 0.";
            return this;
          }
          stockList.put(symbol, stockList.getOrDefault(symbol, 0) + amount);
        } else {
          errorMessage = "Symbol not found.";
          return this;
        }
      } catch (Exception e) {
        errorMessage = "error!";
        return this;
      }
    } else if (command.equals("end") && !isEnd && isNamed == false) {
        portfolio = new Portfolio(stockList);
        isEnd = true;
        return this;
    } else if (isEnd && isNamed == false) {
      //save to file
      if(command.equals("end") || command.equals("yes") || command.equals("no")
              || command.equals("back")) {
        errorMessage = "The name cannot be end, back, no and yes.";
      }
      portfolioService.saveToFile(portfolio, command + ".txt");
      isNamed = true;
      return this;
    } else {
      if(command.equals("yes")) {
        return pageControllerFactory.newInfoPageController(portfolio);
      } else {
        return pageControllerFactory.newMainPageController();
      }
    }
   /* if (command.equals("end")) {
      Portfolio portfolio = new Portfolio(stockList);
      //save to file
      portfolioService.saveToFile(portfolio, LocalDateTime.now() + ".txt");
      isEnd = true;
      return this;
    }

    //return pageControllerFactory.newInfoPageController(portfolio);

    if (command.equals("back")){
      return pageControllerFactory.newMainPageController();
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
    }*/
    return this;
  }

}
