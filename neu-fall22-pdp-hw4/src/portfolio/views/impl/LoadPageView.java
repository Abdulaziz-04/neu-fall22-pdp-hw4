package portfolio.views.impl;

import java.io.PrintStream;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.views.ViewAbs;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class LoadPageView extends ViewAbs {

  private final String errorMessage;
  private final Portfolio portfolio;

  /**
   * This is a constructor that construct a determine page view.
   *
   * @param printStream  a PrintStream object to where the output will be directed to
   * @param portfolio    the portfolio that we want to examine
   * @param errorMessage the error message we want to show to the user
   */
  public LoadPageView(PrintStream printStream, InflexiblePortfolio portfolio, String errorMessage) {
    super(printStream);
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
  }

  /**
   * This is a constructor that construct a determine page view. The output stream is System.out.
   *
   * @param portfolio    the portfolio that we want to examine
   * @param errorMessage the error message we want to show to the user
   */
  public LoadPageView(Portfolio portfolio, String errorMessage) {
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
  }

  @Override
  public void render() {
    clearConsole();
    if (errorMessage != null) {
      printStream.println("---------------------ERROR--------------------------------");
      printStream.println("! Error message: " + errorMessage);
      printStream.println("----------------------------------------------------------");
    }
    printStream.println("*********************************************************");
    printStream.println("!!! If you enter back, you will back to the main menu.");
    printStream.println("*********************************************************");
    if (portfolio == null) {
      printStream.println("--Please enter the name of the portfolio that you want to examine. " +
          "The name cannot be end,yes,no,back.--");
    } else {
      printStream.println("           +---------+---------------+");
      printStream.println("Portfolio: |    Stock|  No. of shares|");
      printStream.println("           +---------+---------------+");
      for (var entry : portfolio.getStocks().entrySet()) {
        printStream.printf("           |%9s|%15d|%n", entry.getKey(), entry.getValue());
      }
      printStream.println("           +---------+---------------+");
      printStream.println("Do you want to determine the total value of current portfolio?");
      printStream.println("Please enter yes if you want to determine. " +
          "Other input will be back to the main menu.");
    }
    printStream.print("input > ");
  }

}
