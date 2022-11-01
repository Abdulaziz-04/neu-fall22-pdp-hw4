package portfolio.views.impl;

import java.io.PrintStream;
import portfolio.views.ViewAbs;

/**
 * This is a class that represent a main menu view page, which implement the View interface.
 */
public class MainPageView extends ViewAbs {

  private final String errorMessage;

  /**
   * It will show the error message for main menu.
   * The error message will be "Please enter the correct number!"
   *
   * @param errorMessage "Please enter the correct number!"
   */
  public MainPageView(PrintStream printStream, String errorMessage){
    super(printStream);
    this.errorMessage = errorMessage;
  }

  public MainPageView(String errorMessage){
    this.errorMessage = errorMessage;
  }

  @Override
  public void render(){
    if (errorMessage != null) {
      printStream.println(errorMessage);
    }
    printStream.println("*********************************************************************" +
        "*******************************");
    printStream.println("This is the main menu of the portfolios");
    printStream.println("1.Create a portfolios");
    printStream.println("2.Examine the composition of a portfolio");
    printStream.println("*********************************************************************" +
            "*******************************");
    printStream.println("!!!If you want to determine a portfolio, you need to go examine it first and" +
            "then to determine it.");
    printStream.println("----------------------------------------------------------------------" +
        "----------------------------------------------------------");
    printStream.println("Please enter the number 1 or 2 that you want to choose.");
  }

  @Override
  protected String getConstructor() {
    return errorMessage;
  }

}
