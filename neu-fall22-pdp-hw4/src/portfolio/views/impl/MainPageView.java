package portfolio.views.impl;

import portfolio.views.View;

public class MainPageView implements View {

  private final String errorMessage;
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
    System.out.printf("1.Create the portfolios\n");
    System.out.printf("2.Examine the composition of a portfolio\n");
    System.out.printf("*********************************************************************" +
            "*******************************\n");
    System.out.printf("If you want to determine a portfolio, you need to go examine it first and" +
            "then to determine it.\n");
    System.out.printf("----------------------------------------------------------------------" +
        "----------------------------------------------------------\n");
    System.out.printf("Please enter the number 1 or 2 that you want to choose.\n");
  }

}
