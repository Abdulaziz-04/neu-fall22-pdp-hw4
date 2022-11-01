package portfolio.views.impl;

import portfolio.views.View;

/**
 * This is a class that represent a main menu view page, which implement the View interface.
 */
public class MainPageView implements View {

  private final String errorMessage;

  /**
   * It will show the error message for main menu.
   * The error message will be "Please enter the correct number!"
   *
   * @param errorMessage "Please enter the correct number!"
   */
  public MainPageView(String errorMessage){
    this.errorMessage = errorMessage;
  }
  @Override
  public void render(){
    if (errorMessage != null) {
      System.out.println(errorMessage);
    }
    System.out.printf("*********************************************************************" +
        "*******************************\n");
    System.out.printf("This is the main menu of the portfolios\n");
    System.out.printf("1.Create a portfolios\n");
    System.out.printf("2.Examine the composition of a portfolio\n");
    System.out.printf("*********************************************************************" +
            "*******************************\n");
    System.out.printf("!!!If you want to determine a portfolio, you need to go examine it first and" +
            "then to determine it.\n");
    System.out.printf("----------------------------------------------------------------------" +
        "----------------------------------------------------------\n");
    System.out.printf("Please enter the number 1 or 2 that you want to choose.\n");
  }

}
