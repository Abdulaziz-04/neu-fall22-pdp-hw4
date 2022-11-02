package portfolio.views.impl;

import java.io.PrintStream;
import portfolio.views.ViewAbs;

/**
 * This is a class that represent a main menu view page, which implement the View interface.
 */
public class MainPageView extends ViewAbs {

  private final String errorMessage;
  private final boolean isInitFailed;

  /**
   * It will show the error message for main menu. The error message will be "Please enter the
   * correct number!"
   *
   * @param printStream a PrintStream object to output what will show on view
   * @param errorMessage "Please enter the correct number!"
   */
  public MainPageView(PrintStream printStream, String errorMessage, boolean isInitFailed) {
    super(printStream);
    this.errorMessage = errorMessage;
    this.isInitFailed = isInitFailed;
  }

  /**
   * It will show the error message for main menu. The error message will be "Please enter the
   * correct number!"
   *
   * @param errorMessage "Please enter the correct number!"
   */
  public MainPageView(String errorMessage, boolean isInitFailed) {
    this.errorMessage = errorMessage;
    this.isInitFailed = isInitFailed;
  }

  @Override
  public void render() {
    clearConsole();
    if (isInitFailed) {
      printStream.println(
          "Something wrong with external API, cannot initialize the application. " +
                  "Please try again in few minutes.");
    }

    if (errorMessage != null) {
      printStream.println("!Error message: " + errorMessage);
    }
    printStream.println("*********************************************************************" +
        "*******************************");
    printStream.println("This is the main menu of the portfolios");
    printStream.println("1.Create a portfolio");
    printStream.println("2.Examine the composition of a portfolio");
    printStream.println("*********************************************************************" +
            "*******************************");
    printStream.println("!!!If you want to determine a portfolio, you need to " +
            "go to examine page or create page first and" +
            " then go to determine it.");
    printStream.println("----------------------------------------------------------------------" +
        "----------------------------------------------------------");
    printStream.println("Please enter the number 1 or 2 that you want to choose.");
  }

}
