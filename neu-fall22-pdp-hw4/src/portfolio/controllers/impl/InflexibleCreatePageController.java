package portfolio.controllers.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import portfolio.controllers.PageController;
import portfolio.controllers.PageControllerFactory;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioService;
import portfolio.views.ViewFactory;
import portfolio.views.View;

/**
 * This is a page controller for the create page, which is implement the page controller.
 * CreatePageController handles input from user and is responsible for checking valid stock input,
 * creating portfolio, saving portfolio and generate View. The controller can hold states while user
 * creating their portfolio. The states are stock selection, naming portfolio and portfolio
 * confirmation.
 */
public class InflexibleCreatePageController implements PageController {

  private final PortfolioService portfolioService;
  private final IOService ioService = new FileIOService();
  private final PortfolioParser portfolioParser;
  private final PageControllerFactory pageControllerFactory;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private Boolean isEnd = false;
  private Boolean isNamed = false;
  private Portfolio portfolio;
  private final Map<String, Integer> stockList = new LinkedHashMap<>();

  public InflexibleCreatePageController(
      PortfolioService portfolioService, PortfolioParser portfolioParser,
      PageControllerFactory controllerFactory,
      ViewFactory viewFactory) {
    this.portfolioService = portfolioService;
    this.pageControllerFactory = controllerFactory;
    this.viewFactory = viewFactory;
    this.portfolioParser = portfolioParser;
  }

  @Override
  public View getView() {
    return viewFactory.newInflexibleCreatePageView(isEnd, isNamed, stockList, errorMessage);
  }

  /**
   * Handle user input for creating portfolio. User can select stock symbol and number of shares,
   * after type 'end', user can input the desire portfolio name. Portfolio name cannot be the same
   * as an existing file in the folder and some keywords such as 'end', 'yes' and 'no'. The method
   * return the next page controller that user should be navigated to.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  @Override
  public PageController handleInput(String input) {
    input = input.trim();
    errorMessage = null;
    if (input.equals("back")) {
      return pageControllerFactory.newMainPageController();
    }
    if (!isEnd && !input.equals("end")) {
      try {
        String[] cmd = input.split(",");
        String symbol = cmd[0];
        if (cmd.length != 2) {
          errorMessage = "Error Format!";
          return this;
        }
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
      } catch (Exception e) {
        errorMessage = "error!";
        return this;
      }
    } else if (input.equals("end") && stockList.size() == 0) {
      errorMessage = "No stock entered. Please input stock.";
      return this;
    } else if (input.equals("end") && !isEnd && !isNamed) {
      List<Transaction> transactions = stockList.entrySet().stream()
          .map(x -> new Transaction(x.getKey(), x.getValue())).collect(
              Collectors.toList());
      try {
        portfolio = portfolioService.create("aa", PortfolioFormat.INFLEXIBLE, transactions);
        isEnd = true;
        return this;
      }
      catch (Exception e) {
        errorMessage = e.getMessage();
        stockList.clear();
        return this;
      }
    } else if (isEnd && !isNamed) {
      //save to file
      if (input.equals("end") || input.equals("yes") || input.equals("no")) {
        errorMessage = "The name cannot be end, back, no and yes.";
        return this;
      }
      try {
        isNamed = ioService.saveTo(portfolioParser.toString(portfolio), input + ".txt");
      } catch (Exception e) {
        errorMessage = e.getMessage();
      }
      return this;
    } else {
      if (input.equals("yes")) {
        return pageControllerFactory.newInfoPageController(portfolio);
      } else {
        return pageControllerFactory.newMainPageController();
      }
    }
    return this;
  }

}
