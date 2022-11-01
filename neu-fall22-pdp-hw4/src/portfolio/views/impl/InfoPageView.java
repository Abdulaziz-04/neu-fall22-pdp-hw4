package portfolio.views.impl;

import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;
import portfolio.views.View;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class InfoPageView implements View {

  private final Portfolio portfolio;
  private final PortfolioWithValue portfolioWithPrice;
  private final String errorMessage;

  /**
   * This is a constructor that construct a determine page view.
   * The error messages is "Error! Please input the correct date.".
   *
   * @param portfolio the portfolio that we want to determine
   * @param portfolioWithPrice the object of PortfolioWithValue
   * @param errorMessage the error message we want to show to the user
   */
  public InfoPageView(Portfolio portfolio, PortfolioWithValue portfolioWithPrice, String errorMessage){
    this.portfolio = portfolio;
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() {
    if (errorMessage != null) {
      System.out.println(errorMessage);
    }
    System.out.println("!!! If you enter back, you will back to the main menu.");
    if (portfolioWithPrice != null) {
      System.out.println("Portfolio value as of: " + portfolioWithPrice.getDate());
      for (var entry: portfolioWithPrice.getStocks()){
        String symbol = entry.getSymbol();
        int amount = entry.getAmount();
        double value = entry.getValue();
        System.out.println(symbol + "," + amount +"," + value);
      }
      System.out.println("Total value: " + portfolioWithPrice.getTotalValue());
    } else {
      System.out.println("Please enter the date that you want to determine." +
              "The format is year-month-day, ex: 2022-10-08");
    }
  }
}
