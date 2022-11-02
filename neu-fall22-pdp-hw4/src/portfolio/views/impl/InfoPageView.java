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
   * This is a constructor that construct a determine page view. The error messages is "Error!
   * Please input the correct date.".
   *
   * @param printStream a PrintStream object to output what will show on view
   * @param portfolioWithPrice the object of PortfolioWithValue
   * @param errorMessage       the error message we want to show to the user
   */
  public InfoPageView(PrintStream printStream, PortfolioWithValue portfolioWithPrice,
      String errorMessage) {
    super(printStream);
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
  }

  /**
   * This is a constructor that construct a determine page view. The error messages is "Error!
   * Please input the correct date.".
   *
   * @param portfolioWithPrice the object of PortfolioWithValue
   * @param errorMessage       the error message we want to show to the user
   */
  public InfoPageView(PortfolioWithValue portfolioWithPrice, String errorMessage) {
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() {
    clearConsole();
    printStream.println("*********************************************************");
    printStream.println("!!! If you enter back, you will back to the main menu.");
    printStream.println("*********************************************************");
    if (portfolioWithPrice != null) {
      printStream.println(
          "If stock price not found, the value with be N/A and will not be include in the total value.");
      printStream.println("");
      printStream.println("Portfolio value as of: " + portfolioWithPrice.getDate());
      printStream.println("+---------+---------------+--------------------+");
      printStream.println("|    Stock|  No. of shares|       Current value|");
      printStream.println("+---------+---------------+--------------------+");
      for (var entry : portfolioWithPrice.getStocks()) {
        String symbol = entry.getSymbol();
        int amount = entry.getAmount();
        String value = entry.getValue() == null ? "N/A" : entry.getValue().toString();
        printStream.printf("|%9s|%15d|%20s|%n", symbol, amount, value);
      }
      printStream.println("+---------+---------------+--------------------+");
      printStream.println("Total value: " + portfolioWithPrice.getTotalValue());
      printStream.println("");
      printStream.println("Please enter the date again if you want to determine value for another date. " +
          "The format is year-month-day, ex: 2022-10-11");
    } else {
      printStream.println("Please enter the date that you want to determine. " +
          "The format is year-month-day, ex: 2022-10-11");
    }
    if (errorMessage != null) {
      printStream.println("! Error message: " + errorMessage);
    }
    printStream.print("input > ");
  }

}
