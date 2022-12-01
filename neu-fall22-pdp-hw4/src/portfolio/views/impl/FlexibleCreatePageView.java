package portfolio.views.impl;

import java.io.PrintStream;
import java.util.List;
import portfolio.models.entities.Transaction;
import portfolio.views.ViewAbs;

/**
 * This is a view that show the create page, which implement the View function.
 */
public class FlexibleCreatePageView extends ViewAbs {

  private final List<Transaction> transactions;
  private final String errorMessage;
  private final Boolean isEnd;
  private final Boolean isNamed;
  private final int state;

  /**
   * This is a constructor that construct a create page view. The error messages will contain "Error
   * Format!", "The share is not a number.", "The shares cannot be negative and 0.", "Symbol not
   * found.", "error!", "The name cannot be end, back, no and yes.", "External API is not ready.
   * Please try again in the next few minutes.", "No stock entered. Please input stock.".
   *
   * @param printStream  a PrintStream object to where the output will be directed to
   * @param isEnd        if the user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed      if the user finish input name, it will be true. Otherwise, false.
   * @param state        the inputBuffer size in controller
   * @param transactions the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public FlexibleCreatePageView(PrintStream printStream, Boolean isEnd, Boolean isNamed,
                                int state, List<String> inputBuffer,
                                List<Transaction> transactions, String errorMessage) {
    super(printStream);
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.state = state;
    this.transactions = transactions;
    this.errorMessage = errorMessage;
  }

  /**
   * This is a constructor that construct a create page view. The error messages will contain "Error
   * Format!", "The share is not a number.", "The shares cannot be negative and 0.", "Symbol not
   * found.", "error!", "The name cannot be end, back, no and yes.", "External API is not ready.
   * Please try again in the next few minutes.", "No stock entered. Please input stock.".
   *
   * @param isEnd        if user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed      if user finish input name, it will be true. Otherwise, false.
   * @param state        the inputBuffer size in controller
   * @param transactions the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public FlexibleCreatePageView(Boolean isEnd, Boolean isNamed, int state, List<String> inputBuffer,
                                List<Transaction> transactions,
                                String errorMessage) {
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.state = state;
    this.transactions = transactions;
    this.errorMessage = errorMessage;
  }

  private void printSelectedStocks() {
    printStream.println(
        "             +-----------+------+---------+---------------+---------------+");
    printStream.println(
        "Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|");
    printStream.println(
        "             +-----------+------+---------+---------------+---------------+");
    for (var entry : transactions) {
      printStream.printf("             |%11s|%6s|%9s|%15.2f|%15s|%n", entry.getDate(),
          entry.getType(),
          entry.getSymbol(), entry.getAmount(), "$" + entry.getCommissionFee());
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
    if (!isEnd) {
      if (!transactions.isEmpty()) {
        printSelectedStocks();
      }
      printStream.println("-------------------------Tips-----------------------------");
      printStream.println("!!! If you enter back, you will back to the main menu.");
      printStream.println("!!! If you want to exit, please input exit");
      printStream.println("----------------------------------------------------------");

      if (state == 0) {
        printStream.println(
            "Please enter the date of transaction. Format: yyyy-MM-dd, Ex: 2022-10-10");
      } else if (state == 1) {
        printStream.println("Please enter stock symbol. Format: All capital letters, Ex: AAPL");
      } else if (state == 2) {
        printStream.println("Please enter transaction type (BUY/SELL)");
      } else if (state == 3) {
        printStream.println("Please enter number of shares. Format: Positive integer, Ex: 100");
      } else if (state == 4) {
        printStream.println(
            "Please enter commission fee for this transaction. Format: Non-negative double, "
                + "Ex: 123.45");
      } else if (state == 5) {
        printStream.println("Do you want to enter another transaction? (yes/no)");
      }

    } else {
      if (!isNamed) {
        if (transactions.size() > 0) {
          printSelectedStocks();
        }
        printStream.println("-------------------------Tips-----------------------------");
        printStream.println("!!! If you enter back, you will back to the main menu.");
        printStream.println("!!! If you want to exit, please input exit");
        printStream.println("----------------------------------------------------------");
        printStream.println("Please enter the name of this portfolio." +
            "The name cannot be end, back, no and yes");
      } else {
        printStream.println("-------------------------Tips-----------------------------");
        printStream.println("!!! If you enter back, you will back to the main menu.");
        printStream.println("!!! If you want to exit, please input exit");
        printStream.println("----------------------------------------------------------");
        printStream.println("Portfolio has been saved.");
        printStream.println(
            "Please enter any key to load page or enter \"back\" to go back to main menu.");
      }
    }
    printStream.print("input > ");
  }

}
