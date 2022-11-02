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
   * The error messages will contain "Error Format!", "The share is not a number.",
   * "The shares cannot be negative and 0.", "Symbol not found.", "error!",
   * "The name cannot be end, back, no and yes.".
   *
   * @param printStream
   * @param isEnd if the user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed if the user finish input name, it will be true. Otherwise, false.
   * @param map the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public CreatePageView(PrintStream printStream, Boolean isEnd, Boolean isNamed, Map<String, Integer> map, String errorMessage){
    super(printStream);
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.map = map;
    this.errorMessage = errorMessage;
  }

  /**
   * This is a constructor that construct a create page view.
   * The error messages will contain "Error Format!", "The share is not a number.",
   * "The shares cannot be negative and 0.", "Symbol not found.", "error!",
   * "The name cannot be end, back, no and yes.".
   *
   * @param isEnd if the user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed if the user finish input name, it will be true. Otherwise, false.
   * @param map the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public CreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map, String errorMessage){
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.map = map;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() /*throws IOException*/ {
    clearConsole();
    if (errorMessage != null){
      printStream.println("!Error message: " + errorMessage);
    }
    if(!isEnd) {
      printStream.println("*********************************************************");
      printStream.println("!!! If you enter back, you will back to the main menu.");
      printStream.println("*********************************************************");
      printStream.println("Enter symbol and number of shares for one stock. " +
              "The format is: AAPL,100.");
      printStream.println("--The symbol must be capital letters and " +
      "the shares need to be numbers.");
      printStream.println("--The shares cannot be 0 and " +
      "negative number.");
      printStream.println("--Between the symbol and shares must have a comma " +
      "with no spaces.");
      printStream.println("--Enter end to finish input this portfolio.");
    } else {
      if(!isNamed) {
        if (map.size() > 0) {
          printStream.println("Selected stocks and shares:");
          for (var entry: map.entrySet()) {
            printStream.println(entry.getKey() + "," + entry.getValue());
          }
        }
        printStream.println("*********************************************************");
        printStream.println("!!! If you enter back, you will back to the main menu.");
        printStream.println("*********************************************************");
        printStream.println("Please enter the file name of this portfolio." +
                "The name cannot be end, back, no and yes.");
      } else {
        printStream.println("*********************************************************");
        printStream.println("!!! If you enter back, you will back to the main menu.");
        printStream.println("*********************************************************");
        printStream.println("Do you want to determine the total value of this portfolio?");
        printStream.println("--Please enter yes if you want to determine. " +
                "Other input will be back to the main menu.");
      }
    }

  }

}
