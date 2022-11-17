package portfolio.views.impl;

import java.io.PrintStream;
import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;


import java.util.Map;
import portfolio.views.ViewAbs;

/**
 * This is a class that represent a performance view page, which implement the View interface.
 */
public class PerformancePageView extends ViewAbs {

  private final String errorMessage;

  private final String portfolioName;

  private final LocalDate startDate;
  private final LocalDate endDate;

  private final Map<String, Integer> performance;
  private final String scale;

  private boolean isFinish;

  /**
   * This is a constructor to construct a performance page view.
   *
   * @param printStream   the print stream of the
   * @param portfolioName the name of portfolio
   * @param startDate     the start date to performance
   * @param endDate       the end date to performance
   * @param list          the list of timestamps
   * @param listStar      the list of stars
   * @param scale         the scale of performance
   * @param isFinish      finish current performance is true. Otherwise, false.
   * @param errorMessage  the error message will show to the user
   */
  public PerformancePageView(PrintStream printStream, String portfolioName,
      LocalDate startDate,
      LocalDate endDate,
      Map<String, Integer> performance,
      String scale,
      boolean isFinish,
      String errorMessage) {
    super(printStream);
    this.portfolioName = portfolioName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.performance = performance;
    this.scale = scale;
    this.isFinish = isFinish;
    this.errorMessage = errorMessage;
  }

  /**
   * This is a constructor to construct a performance page view. The output stream is System.out.
   *
   * @param portfolioName the name of portfolio
   * @param startDate     the start date to performance
   * @param endDate       the end date to performance
   * @param list          the list of timestamps
   * @param listStar      the list of stars
   * @param scale         the scale of performance
   * @param isFinish      finish current performance is true. Otherwise, false.
   * @param errorMessage  the error message will show to the user
   */
  public PerformancePageView(String portfolioName,
      LocalDate startDate,
      LocalDate endDate,
      Map<String, Integer> performance,
      String scale,
      boolean isFinish,
      String errorMessage) {
    this.portfolioName = portfolioName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.performance = performance;
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
    printStream.println("!!! If you input back, you will back to the load page.");
    printStream.println("!!! If you want to exit, please input exit");
    printStream.println("----------------------------------------------------------");

    if (startDate != null && endDate == null && isFinish == false) {
      printStream.println("+---------------+");
      printStream.println("|     start date|");
      printStream.println("+---------------+");
      printStream.printf("|%15s|%n", startDate.toString());
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
      for (var entry: performance.entrySet()) {
        printStream.println(entry.getKey() + "*".repeat(entry.getValue()));
      }
      printStream.println("scale: " + scale);
      printStream.println("----------------------------------------------------------");
      printStream.println("Please enter the the start date of the timespan " +
          "that you want to performance.");
      printStream.println("--EX.2020-10-09");
      printStream.println("--The format of date needs to be 2022-10-11");
      printStream.print("input > ");
    }
    if (startDate == null) {
      printStream.println("Please enter the the start date of the timespan " +
          "that you want to performance.");
      printStream.println("--EX.2020-10-09");
      printStream.println("--The format of date needs to be 2022-10-11");
      printStream.print("input > ");
    }
  }
}


