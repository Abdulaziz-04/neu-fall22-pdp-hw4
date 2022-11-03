package portfolio.views.impl;

import java.io.PrintStream;
import java.util.Map;

import portfolio.views.ViewAbs;

/**
 * This is a view that show the create page, which implement the View function.
 */
public class CreatePageView extends ViewAbs {

  private final Map<String, Integer> map;
  private final String errorMessage;
  private final Boolean isEnd;
  private final Boolean isNamed;

  /**
   * This is a constructor that construct a create page view.
   *
   * @param printStream  a PrintStream object to where the output will be directed to
   * @param isEnd        if the user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed      if the user finish input name, it will be true. Otherwise, false.
   * @param map          the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public CreatePageView(PrintStream printStream, Boolean isEnd, Boolean isNamed,
      Map<String, Integer> map, String errorMessage) {
    super(printStream);
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.map = map;
    this.errorMessage = errorMessage;
  }

  /**
   * This is a constructor that construct a create page view. The output stream is System.out.
   *
   * @param isEnd        if the user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed      if the user finish input name, it will be true. Otherwise, false.
   * @param map          the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public CreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map,
      String errorMessage) {
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.map = map;
    this.errorMessage = errorMessage;
  }

  private void printSelectedStocks() {
    printStream.println("          +---------+---------------+");
    printStream.println("Selected: |    Stock|  No. of shares|");
    printStream.println("          +---------+---------------+");
    for (var entry : map.entrySet()) {
      printStream.printf("          |%9s|%15d|%n", entry.getKey(), entry.getValue());
    }
    printStream.println("          +---------+---------------+");
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
    if (!isEnd) {
      if (!map.isEmpty()) {
        printSelectedStocks();
      }
      printStream.println("Enter symbol and number of shares for one stock. "
          + "The format is: AAPL,100.");
      printStream.println("--The symbol must be capital letters and "
          + "the shares need to be numbers.");
      printStream.println("--The shares cannot be 0 and "
          + "negative number.");
      printStream.println("--Between the symbol and shares must have a comma "
          + "with no spaces.");
      printStream.println("--Enter end to finish input this portfolio.");
    } else {
      if (!isNamed) {
        if (map.size() > 0) {
          printSelectedStocks();
        }
        printStream.println("Please enter the file name of this portfolio." +
            "The name cannot be end, back, no and yes");
      } else {

        printStream.println("Do you want to determine the total value of this portfolio?");
        printStream.println("--Please enter yes if you want to determine. " +
            "Other input will be back to the main menu.");
      }
    }
    printStream.print("input > ");
  }

}
