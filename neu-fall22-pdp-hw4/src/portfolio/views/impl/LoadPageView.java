package portfolio.views.impl;

import java.util.ArrayList;
import java.util.List;

import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntry;
import portfolio.views.View;

public class LoadPageView implements View {

  private final String errorMessage;
  private Portfolio portfolio;
  public LoadPageView(Portfolio portfolio, String errorMessage){
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
  }
  @Override
  public void render() {
    if (errorMessage != null) {
      System.out.println(errorMessage);
    }
    System.out.println("!!! If you enter back, you will back to the main menu.");
    if(portfolio == null) {
      System.out.println("--Please enter the name of the portfolio that you want to examine." +
              "The name cannot be end,yes,no,back.--");
    } else {
      // get portfoio list
      for (PortfolioEntry entry : portfolio.getStocks()) {
        String symbol = entry.getSymbol();
        int amount = entry.getAmount();
        System.out.println(symbol + "," + amount);
      }
      System.out.println("Do you want to determine the total value of current portfolio?");
      System.out.println("Please enter yes if you want to determine. " +
              "Other input will be back to the main menu.");
    }
  }
}
