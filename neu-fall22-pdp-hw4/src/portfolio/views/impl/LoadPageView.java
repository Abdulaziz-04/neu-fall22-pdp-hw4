package portfolio.views.impl;

import java.util.ArrayList;
import java.util.List;

import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntry;
import portfolio.views.View;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class LoadPageView implements View {

  private final String errorMessage;
  private Portfolio portfolio;

  /**
   * This is a constructor that construct a determine page view.
   * The error messages is "Error! Cannot load file. Please try again.".
   *
   * @param portfolio the portfolio that we want to examine
   * @param errorMessage the error message we want to show to the user
   */
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
