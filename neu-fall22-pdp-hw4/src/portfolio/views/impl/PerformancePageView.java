package portfolio.views.impl;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;


import portfolio.views.ViewAbs;

public class PerformancePageView extends ViewAbs {

  private final String errorMessage;

  private final String portfolioName;

  private final LocalDate startDate;
  private final LocalDate endDate;

  private final List<String> listStar;
  private final List<String> list;
  private final String scale;


  public PerformancePageView(String portfolioName,
                             LocalDate startDate,
                             LocalDate endDate,
                             List<String> list,
                             List<String> listStar,
                             String scale,
                             String errorMessage) {
    this.portfolioName = portfolioName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.list = list;
    this.listStar = listStar;
    this.scale = scale;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() {
    clearConsole();
    printStream.println("*************This is the interface for Performance**************");
    if (errorMessage != null) {
      printStream.println("---------------------ERROR--------------------------------");
      printStream.println("! Error message: " + errorMessage);
      printStream.println("----------------------------------------------------------");
    }
    printStream.println("*********************************************************");
    printStream.println("!!! If you enter back, you will back to the main menu.");
    printStream.println("*********************************************************");
    printStream.println("Please enter another scale of the timespan " +
            "that you want to performance.");
    printStream.println("--EX.2020-10-09,2022-10-09");
    printStream.println("--The format of date needs to be 2022-10-11");
    printStream.println("--There is a comma between the two date and no space between them.");
    printStream.print("input > ");
    //printStream.println("--Other input except back will be error.");

    if (startDate != null && endDate != null) {
      printStream.println("Performance of portfolio " + portfolioName
              + " from " + startDate + " to " + endDate);
      for (int i = 0; i < list.size(); i++) {
        printStream.println(list.get(i) + listStar.get(i));
      }
      printStream.println("scale: " + scale);
      printStream.println("----------------------------------------------------------");
      printStream.println("Please enter the scale of the timespan " +
              "that you want to performance.");
      printStream.println("--EX.2020-10-09,2022-10-09");
      printStream.println("--The format of date needs to be 2022-10-11");
      printStream.println("--There is a comma between the two date and no space between them.");
      printStream.print("input > ");
    }

  }
}


