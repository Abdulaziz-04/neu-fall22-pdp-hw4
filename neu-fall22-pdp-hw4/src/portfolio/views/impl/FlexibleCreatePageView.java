package portfolio.views.impl;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
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

  /**
   * This is a constructor that construct a create page view. The error messages will contain "Error
   * Format!", "The share is not a number.", "The shares cannot be negative and 0.", "Symbol not
   * found.", "error!", "The name cannot be end, back, no and yes.", "External API is not ready.
   * Please try again in the next few minutes.", "No stock entered. Please input stock.".
   *
   * @param printStream  a PrintStream object to where the output will be directed to
   * @param isEnd        if the user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed      if the user finish input name, it will be true. Otherwise, false.
   * @param transactions          the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public FlexibleCreatePageView(PrintStream printStream, Boolean isEnd, Boolean isNamed,
      List<Transaction> transactions, String errorMessage) {
    super(printStream);
    this.isEnd = isEnd;
    this.isNamed = isNamed;
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
   * @param transactions          the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public FlexibleCreatePageView(Boolean isEnd, Boolean isNamed, List<Transaction> transactions,
      String errorMessage) {
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.transactions = transactions;
    this.errorMessage = errorMessage;
  }

  private void printSelectedStocks() {
    printStream.println("             +-----------+------+---------+---------------+---------------+");
    printStream.println("Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|");
    printStream.println("             +-----------+------+---------+---------------+---------------+");
    for (var entry : transactions) {
      printStream.printf("             |%11s|%6s|%9s|%15d|%15f|%n", entry.getDate(),entry.getType(),
          entry.getSymbol(), entry.getAmount(), entry.getCommissionFee());
    }
    printStream.println("             +-----------+------+---------+---------------+---------------+");
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
      printStream.println("*********************************************************");
      printStream.println("!!! If you enter back, you will back to the main menu.");
      printStream.println("*********************************************************");
      printStream.println("Enter symbol and number of shares for one stock. "
          + "The format is: [yyyy-MM-dd,transaction_type,symbol,commission_fee] ex. 2022-10-10,BUY,AAPL,100,123.45.");
      printStream.println("--The symbol must be capital letters and "
          + "the shares need to be numbers.");
      printStream.println("--The shares cannot be 0 and "
          + "negative number.");
      printStream.println("--Between the symbol and shares must have a comma "
          + "with no spaces.");
      printStream.println("--Enter end to finish input this portfolio.");

    } else {
      if (!isNamed) {
        if (transactions.size() > 0) {
          printSelectedStocks();
        }
        printStream.println("*********************************************************");
        printStream.println("!!! If you enter back, you will back to the main menu.");
        printStream.println("*********************************************************");
        printStream.println("Please enter the file name of this portfolio." +
            "The name cannot be end, back, no and yes");
      } else {
        printStream.println("*********************************************************");
        printStream.println("!!! If you enter back, you will back to the main menu.");
        printStream.println("*********************************************************");
        printStream.println("Do you want to determine the total value of this portfolio?");
        printStream.println("--Please enter yes if you want to determine. " +
            "Other input will be back to the main menu.");
      }
    }
    printStream.print("input > ");
  }
}
