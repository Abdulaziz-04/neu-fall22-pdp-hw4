package portfolio.views.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import portfolio.controllers.FrontController;
import portfolio.controllers.PortfolioController;
import portfolio.entities.StockListEntry;
import portfolio.views.View;

public class ViewImpl implements View{

  PortfolioController portfolioController;
  FrontController frontController;

  public ViewImpl(PortfolioController portfolioController, FrontController frontController) {
    this.portfolioController = portfolioController;
    this.frontController = frontController;
  }
  public void show(){
    while(true){
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
    Scanner scan = new Scanner(System.in);
    int option;
    option = scan.nextInt();
    if(option == 1) {

    } else if (option == 2){

    } else if (option == 3) {

    } else if (option == 4) {

    } else {
      System.out.printf("Please enter the correct number of the options\n");
    }
  }
}

  public void create() {
    StockListResponse stockListResponse = priceAPI.getStockList();
    List<StockListEntry> stockListEntries = stockListResponse.entries;
    List<String> symbolList = new ArrayList<>();
    for (StockListEntry entry : stockListEntries) {
      symbolList.add(entry.symbol);
    }

    StockPriceResponse stockPriceResponse = priceAPI.getStockPrice(new StockPriceRequest(symbol));
    List<DailyStockPrice> dailyStockPrice = stockPriceResponse.prices;
    for (DailyStockPrice entry : dailyStockPrice) {
      if (entry.timestamp == "2022-10-28") {
        entry.close * amount;
      }
    }
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
