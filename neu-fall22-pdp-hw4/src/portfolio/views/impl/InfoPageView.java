package portfolio.views.impl;

import java.io.PrintStream;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.views.ViewAbs;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class InfoPageView extends ViewAbs {

  private final PortfolioWithValue portfolioWithPrice;
  private final String errorMessage;
  private final Double costOfBasis;

  /**
   * This is a constructor that construct a determine page view.
   *
   * @param printStream        a PrintStream object to where the output will be directed to
   * @param portfolioWithPrice the object of PortfolioWithValue
   * @param errorMessage       the error message we want to show to the user
   */
  public InfoPageView(PrintStream printStream, Double costOfBasis,
      PortfolioWithValue portfolioWithPrice,
      String errorMessage) {
    super(printStream);
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
    this.costOfBasis = costOfBasis;
  }

  /**
   * This is a constructor that construct a determine page view. The output stream is System.out.
   *
   * @param portfolioWithPrice the object of PortfolioWithValue
   * @param errorMessage       the error message we want to show to the user
   */
  public InfoPageView(PortfolioWithValue portfolioWithPrice, Double costOfBasis,
      String errorMessage) {
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
    this.costOfBasis = costOfBasis;
  }

  @Override
  public void render() {
    clearConsole();
    if (errorMessage != null) {
      printStream.println("---------------------ERROR--------------------------------");
      printStream.println("! Error message: " + errorMessage);
      printStream.println("----------------------------------------------------------");
    }
    printStream.println("-------------------------Tips-----------------------------");
    printStream.println("!!! If you enter back, you will back to the load page.");
    printStream.println("!!! If you want to exit, please input exit");
    printStream.println("----------------------------------------------------------");
    if (portfolioWithPrice != null) {
      printStream.println(
          "If stock price not found, the value with be N/A and "
              + "will not be include in the total value.");
      printStream.println("");
      printStream.println("Portfolio composition and value as of: "
          + portfolioWithPrice.getDate());
      printStream.println("+---------+---------------+--------------------+");
      printStream.println("|    Stock|  No. of shares|       Current value|");
      printStream.println("+---------+---------------+--------------------+");
      for (var entry : portfolioWithPrice.getValues()) {
        String symbol = entry.getSymbol();
        int amount = entry.getAmount();
        String value = entry.getValue() ==
            null ? "N/A" : "$" + entry.getValue().toString();
        printStream.printf("|%9s|%15d|%20s|%n", symbol, amount, value);
      }
      printStream.println("+---------+---------------+--------------------+");
      printStream.println("Total value: " + "$" + portfolioWithPrice.getTotalValue());
      if (costOfBasis != null) {
        printStream.println("Cost of basis as of "
            + portfolioWithPrice.getDate() + ": " + "$" + costOfBasis);
      } else {
        printStream.println("Cost of basis is not available for this portfolio.");
      }
      printStream.println();
      printStream.println("Please enter the date again if "
          + "you want to determine value for another date. "
          + "The format is year-month-day, ex: 2022-10-11");
    } else {
      printStream.println("Please enter the date that you want to determine. "
          + "The format is year-month-day, ex: 2022-10-11");
    }
    printStream.print("input > ");
  }

}
