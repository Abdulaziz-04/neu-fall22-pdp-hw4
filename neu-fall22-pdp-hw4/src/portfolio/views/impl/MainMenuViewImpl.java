package portfolio.views.impl;

import portfolio.views.MainMenuView;

public class MainMenuViewImpl implements MainMenuView {

  @Override
  public void print(String errorMessage){
    System.out.printf("*********************************************************************" +
        "*******************************\n");
    System.out.printf("This is the menu of the portfolio\n");
    System.out.printf("1.Create the portfolios\n");
    System.out.printf("2.Examine the composition of a portfolio\n");
    System.out.printf("3.Determine the total value of a portfolio on a certain date.\n");
    System.out.printf("4.Retrieve the portfolio.\n");
    System.out.printf("5.quit.\n");
    System.out.printf("----------------------------------------------------------------------" +
        "------------------\n");
    if (errorMessage != null) {
      System.out.println(errorMessage);
    }
    System.out.printf("Please enter the number(1-4) that you want to choose " +
        "before the option above");
  }

}
