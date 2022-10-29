package portfolio.views.impl;

import java.util.Scanner;
import portfolio.views.View;

public class ViewImpl implements View{

  public void showMainMenu(){
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
    System.out.printf("Please enter the number(1-4) that you want to choose " +
        "before the option above");
  }

  public void create() {
    System.out.printf("--This is Create the portfolios interface--");
    System.out.printf("Please enter one of stock that you want to create.");
    Scanner scan = new Scanner(System.in);
    if (scan.hasNextLine()) {
      String symbol = scan.nextLine();
      if (!symbolList.contains(symbol)) {
        System.out.printf("We do not have this stock. Please enter the correct symbol");
      }

    }

    System.out.printf("Please enter the shares of this stock.");
  }

}
