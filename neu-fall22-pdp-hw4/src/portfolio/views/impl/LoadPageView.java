package portfolio.views.impl;

import java.io.PrintStream;
import java.util.List;

import javax.swing.JPanel;

import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.views.ViewAbs;

/**
 * This is a view that show the determine page, which implement the View function.
 */
public class LoadPageView extends ViewAbs {

  private final String errorMessage;
  private final Portfolio portfolio;
  private final boolean showModifyMenu;

  /**
   * This is a constructor that construct a load page view.
   *
   * @param printStream    a PrintStream object to where the output will be directed to
   * @param portfolio      the portfolio that we want to load
   * @param showModifyMenu show modify menu or not
   * @param errorMessage   the error message we want to show to the user
   */
  public LoadPageView(PrintStream printStream, Portfolio portfolio,
                      boolean showModifyMenu, String errorMessage) {
    super(printStream);
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
    this.showModifyMenu = showModifyMenu;
  }

  /**
   * This is a constructor that construct a load page view. The output stream is System.out.
   *
   * @param portfolio      the portfolio that we want to examine
   * @param showModifyMenu show modify menu or not
   * @param errorMessage   the error message we want to show to the user
   */
  public LoadPageView(Portfolio portfolio, boolean showModifyMenu, String errorMessage) {
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
    this.showModifyMenu = showModifyMenu;
  }

  private void printTransaction(List<Transaction> transactions) {
    printStream.println(
            "             +-----------+------+---------+---------------+---------------+");
    printStream.println(
            "Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|");
    printStream.println(
            "             +-----------+------+---------+---------------+---------------+");
    for (var entry : transactions) {
      printStream.printf("             |%11s|%6s|%9s|%15d|%15s|%n",
              entry.getDate() == null ? "N/A" : entry.getDate(),
              entry.getType() == null ? "N/A" : entry.getType(),
              entry.getSymbol(),
              entry.getAmount(),
              entry.getCommissionFee() == null ? "N/A" : "$" + entry.getCommissionFee()
      );
    }
    printStream.println(
            "             +-----------+------+---------+---------------+---------------+");
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
    printStream.println("!!! If you enter back, you will back to the main menu.");
    printStream.println("!!! If you want to exit, please input exit");
    printStream.println("----------------------------------------------------------");
    if (portfolio == null) {
      printStream.println("Please enter the name of the portfolio that you want to load. " +
              "The name cannot be back.");
    } else {
      printStream.println("Portfolio: " + portfolio.getName());
      printTransaction(portfolio.getTransactions());
      printStream.println("Menu:");
      printStream.println("1. View composition, value, cost of basis for specific date");
      printStream.println("2. View performance over time");
      if (showModifyMenu) {
        printStream.println("3. Modify portfolio (Add transaction to portfolio)");
      }
      printStream.println("--Please enter the number in above");
    }
    printStream.print("input > ");
  }

}
