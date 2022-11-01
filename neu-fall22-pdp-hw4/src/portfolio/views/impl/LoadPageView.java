package portfolio.views.impl;

import java.io.PrintStream;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntry;
import portfolio.views.ViewAbs;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class LoadPageView extends ViewAbs {

  private final String errorMessage;
  private final Portfolio portfolio;

  /**
   * This is a constructor that construct a determine page view.
   * The error messages is "Error! Cannot load file. Please try again.".
   *
   * @param portfolio the portfolio that we want to examine
   * @param errorMessage the error message we want to show to the user
   */
  public LoadPageView(PrintStream printStream, Portfolio portfolio, String errorMessage){
    super(printStream);
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
  }

  public LoadPageView(Portfolio portfolio, String errorMessage){
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
  }

  @Override
  public void render() {
    if (errorMessage != null) {
      printStream.println(errorMessage);
    }
    printStream.println("!!! If you enter back, you will back to the main menu.");
    if(portfolio == null) {
      printStream.println("--Please enter the name of the portfolio that you want to examine." +
              "The name cannot be end,yes,no,back.--");
    } else {
      for (PortfolioEntry entry : portfolio.getStocks()) {
        String symbol = entry.getSymbol();
        int amount = entry.getAmount();
        printStream.println(symbol + "," + amount);
      }
      printStream.println("Do you want to determine the total value of current portfolio?");
      printStream.println("Please enter yes if you want to determine. " +
              "Other input will be back to the main menu.");
    }
  }

  @Override
  protected String getConstructor() {
    return portfolio.toString() + "," + errorMessage;
  }

}
