package portfolio.views.impl;

import java.io.PrintStream;
import portfolio.entities.PortfolioWithValue;
import portfolio.views.ViewAbs;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class InfoPageView extends ViewAbs {

  private final PortfolioWithValue portfolioWithPrice;
  private final String errorMessage;

  /**
   * This is a constructor that construct a determine page view.
   * The error messages is "Error! Please input the correct date.".
   *
   * @param portfolioWithPrice the object of PortfolioWithValue
   * @param errorMessage the error message we want to show to the user
   */
  public InfoPageView(PrintStream printStream, PortfolioWithValue portfolioWithPrice, String errorMessage){
    super(printStream);
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
  }

  public InfoPageView(PortfolioWithValue portfolioWithPrice, String errorMessage){
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() {
    if (errorMessage != null) {
      printStream.println(errorMessage);
    }
    printStream.println("!!! If you enter back, you will back to the main menu.");
    if (portfolioWithPrice != null) {
      printStream.println("Portfolio value as of: " + portfolioWithPrice.getDate());
      for (var entry: portfolioWithPrice.getStocks()){
        String symbol = entry.getSymbol();
        int amount = entry.getAmount();
        double value = entry.getValue();
        printStream.println(symbol + "," + amount +"," + value);
      }
      printStream.println("Total value: " + portfolioWithPrice.getTotalValue());
    } else {
      printStream.println("Please enter the date that you want to determine." +
              "The format is year-month-day, ex: 2022-10-08");
    }
  }
}
