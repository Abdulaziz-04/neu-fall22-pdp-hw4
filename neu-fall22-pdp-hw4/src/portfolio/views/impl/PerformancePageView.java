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

  private boolean isFinish;


  public PerformancePageView(String portfolioName,
                             LocalDate startDate,
                             LocalDate endDate,
                             List<String> list,
                             List<String> listStar,
                             String scale,
                             boolean isFinish,
                             String errorMessage) {
    this.portfolioName = portfolioName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.list = list;
    this.listStar = listStar;
    this.scale = scale;
    this.isFinish = isFinish;
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
    printStream.println("-------------------------Tips-----------------------------");
    printStream.println("!!! If you enter back, you will back to the main menu.");
    printStream.println("----------------------------------------------------------");

    if(startDate != null && endDate == null && isFinish == false) {
      printStream.println("+---------------+");
      printStream.println("|     start date|");
      printStream.println("+---------------+");
      printStream.printf("|%15s|%n",startDate.toString());
      printStream.println("+---------------+");
      printStream.println("Please enter the end date of the timespan " +
              "that you want to performance.");
      printStream.println("--EX.2022-10-09");
      printStream.println("--The format of date needs to be 2022-10-11");
      printStream.print("input > ");
    }

    if (startDate != null && endDate != null && isFinish == true) {
      printStream.println("Performance of portfolio " + portfolioName
              + " from " + startDate + " to " + endDate);
      for (int i = 0; i < list.size(); i++) {
        printStream.println(list.get(i) + listStar.get(i));
      }
      printStream.println("scale: " + scale);
      printStream.println("----------------------------------------------------------");
      printStream.println("Please enter the the start date of the timespan " +
              "that you want to performance.");
      printStream.println("--EX.2020-10-09");
      printStream.println("--The format of date needs to be 2022-10-11");
      printStream.print("input > ");
    }
    if(startDate == null) {
      printStream.println("Please enter the the start date of the timespan " +
              "that you want to performance.");
      printStream.println("--EX.2020-10-09");
      printStream.println("--The format of date needs to be 2022-10-11");
      printStream.print("input > ");
    }
  }
}


