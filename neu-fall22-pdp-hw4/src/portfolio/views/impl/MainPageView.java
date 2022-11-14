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
   * It will show the error message for main menu.
   *
   * @param printStream  a PrintStream object to where the output will be directed to
   * @param errorMessage "Please enter the correct number!"
   * @param isInitFailed is initialization failed
   */
  public MainPageView(PrintStream printStream, String errorMessage, boolean isInitFailed) {
    super(printStream);
    this.errorMessage = errorMessage;
    this.isInitFailed = isInitFailed;
  }

  /**
   * It will show the error message for main menu. The output stream is System.out.
   *
   * @param errorMessage "Please enter the correct number!"
   * @param isInitFailed is initialization failed
   */
  public MainPageView(String errorMessage, boolean isInitFailed) {
    this.errorMessage = errorMessage;
    this.isInitFailed = isInitFailed;
  }

  @Override
  public void render() {
    clearConsole();
    if (errorMessage != null) {
      printStream.println("---------------------ERROR--------------------------------");
      printStream.println("! Error message: " + errorMessage);
      printStream.println("----------------------------------------------------------");
    }
    if (isInitFailed) {
      printStream.println(
          "Something wrong with external API, cannot initialize the application. " +
              "Please try again in few minutes.");
      return;
    }

    printStream.println("*********************************************************************" +
        "*******************************");
    printStream.println("This is the main menu of the portfolios");
    printStream.println("1.Create an inflexible portfolio");
    printStream.println("2.Create a flexible portfolio");
    printStream.println("3.Load a portfolio");
    printStream.println("*********************************************************************" +
        "*******************************");
    printStream.println(
        "!!!If you want to determine portfolio's value or modify a portfolio, you need to " +
            "load or create a portfolio first");
    printStream.println("---------------------------------------------------------------------" +
        "-------------------------------");
    printStream.println("Please enter the number 1,2 or 3.");
    printStream.print("input > ");
  }

}
