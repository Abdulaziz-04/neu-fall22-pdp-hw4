package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioPerformance;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.ScheduleRunner;
import portfolio.models.stockprice.StockQueryService;

/**
 * Portfolio model handles all portfolio related operation including querying stock price for
 * specific date.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private final StockQueryService stockQueryService;
  private final PortfolioParser portfolioParser;

  private final ScheduleRunner scheduleRunner;
  private Portfolio portfolio = null;

  /**
   * This is a constructor to construct the portfolio model, which will get the stock list and parse
   * the portfolio to given format.
   *
   * @param stockQueryService service for getting stock list
   * @param portfolioParser   parse portfolio
   */
  public PortfolioModelImpl(StockQueryService stockQueryService,
      PortfolioParser portfolioParser, ScheduleRunner scheduleRunner) {
    this.stockQueryService = stockQueryService;
    this.portfolioParser = portfolioParser;
    this.scheduleRunner = scheduleRunner;
  }

  public void init() throws Exception {
    portfolio = null;
    stockQueryService.getStockList();
  }

  @Override
  public Portfolio getPortfolio() {
    return portfolio;
  }

  @Override
  public Portfolio create(String name, PortfolioFormat format, List<Transaction> transactions)
      throws Exception {
    checkTransactions(transactions);
    switch (format) {
      case INFLEXIBLE:
        portfolio = new InflexiblePortfolio(name, transactions);
        break;
      case FLEXIBLE:
        portfolio = new FlexiblePortfolio(name, transactions);
        break;
      default:
        throw new Exception("Unsupported Portfolio type.");
    }
    return portfolio;
  }

  @Override
  public void load(String name, String text) throws Exception {
    var p = portfolioParser.parse(text);
    checkTransactions(p.getTransactions());
    List<Transaction> transactions = new ArrayList<>(p.getTransactions());
    List<BuySchedule> schedules = p.getBuySchedules();
    portfolio = create(name, p.getFormat(), transactions);
    if (schedules != null) {
      for (var schedule : schedules) {
        addSchedule(
            schedule.getName(),
            schedule.getAmount(),
            schedule.getFrequencyDays(),
            schedule.getStartDate(),
            schedule.getEndDate(),
            schedule.getTransactionFee(),
            schedule.getLastRunDate(),
            schedule.getBuyingList()
        );
      }
    }
  }

  @Override
  public boolean checkTransaction(LocalDate date, String symbol) throws Exception {
    stockQueryService.getStockPrice(date, List.of(symbol));
    return true;
  }

  @Override
  public void checkTransactions(List<Transaction> transactions)
      throws Exception {
    Map<String, LocalDate> map = new HashMap<>();
    for (var stock : stockQueryService.getStockList()) {
      map.put(stock.getSymbol(), stock.getIpoDate());
    }
    for (var entry : transactions) {
      List<String> symbols = new ArrayList<>();
      if (entry.getDate() != null) {
        checkTransaction(entry.getDate(), entry.getSymbol());
      }
      symbols.add(entry.getSymbol());
      if (!map.containsKey(entry.getSymbol())) {
        throw new IllegalArgumentException("Symbol [" + entry.getSymbol() + "] not found.");
      }
      if (entry.getDate() != null) {
        stockQueryService.getStockPrice(entry.getDate(), symbols).containsKey(entry.getSymbol());
      }
    }
  }

  @Override
  public void addTransactions(List<Transaction> newTransactions) throws Exception {
    if (portfolio.isReadOnly()) {
      throw new IllegalArgumentException("Portfolio is not modifiable.");
    }

    List<Transaction> transactions = new ArrayList<>(portfolio.getTransactions());
    transactions.addAll(newTransactions);

    // Create same class of portfolio with new set of transactions
    portfolio = portfolio.create(transactions);
  }

  @Override
  public void addSchedule(String name, double amount, int frequencyDays, LocalDate startDate,
      LocalDate endDate,
      double transactionFee, LocalDate lastRunDate, List<Transaction> buyingList) throws Exception {
    BuySchedule schedule = new DollarCostAverageSchedule(name, amount, frequencyDays, startDate,
        endDate,
        transactionFee, lastRunDate, buyingList);
    List<BuySchedule> currentSchedules = new ArrayList<>(portfolio.getBuySchedules());
    if (portfolio.getBuySchedules() == null) {
      throw new Exception("Cannot add schedule to this portfolio.");
    }
    if (currentSchedules.stream().anyMatch(x -> Objects.equals(x.getName(), name))) {
      throw new Exception("Duplicate schedule name in the same portfolio.");
    }
    List<Transaction> scheduledTransaction = scheduleRunner.run(LocalDate.now(), schedule);
    List<Transaction> transactions = new ArrayList<>(portfolio.getTransactions());
    scheduledTransaction.addAll(transactions);
    schedule = new DollarCostAverageSchedule(name, amount, frequencyDays, startDate, endDate,
        transactionFee, LocalDate.now(), buyingList);
    currentSchedules.add(schedule);
    portfolio = portfolio.create(scheduledTransaction, currentSchedules);
  }

  @Override
  public void modifySchedule(String name, double amount, int frequencyDays, LocalDate startDate,
      LocalDate endDate, double transactionFee, LocalDate lastRunDate, List<Transaction> buyingList)
      throws Exception {
    List<BuySchedule> schedules = new ArrayList<>(portfolio.getBuySchedules());
    if (schedules == null || schedules.isEmpty()) {
      throw new Exception("Current portfolio does not have buy schedule.");
    }
    BuySchedule schedule = null;
    for (int i = 0; i < schedules.size(); i++) {
      if (Objects.equals(schedules.get(i).getName(), name)) {
        schedule = schedules.get(i);
        schedules.remove(i);
        break;
      }
    }
    if (schedule == null) {
      throw new Exception("Cannot find schedule name: " + name);
    }
    schedule = new DollarCostAverageSchedule(name, amount, frequencyDays, startDate, endDate,
        transactionFee, schedule.getLastRunDate(), buyingList);
    List<Transaction> scheduledTransaction = scheduleRunner.run(LocalDate.now(), schedule);
    List<Transaction> transactions = new ArrayList<>(portfolio.getTransactions());
    scheduledTransaction.addAll(transactions);
    schedules.add(schedule);
    portfolio = portfolio.create(scheduledTransaction, schedules);
  }

  @Override
  public PortfolioWithValue getValue(LocalDate date) throws Exception {
    Map<String, StockPrice> prices = stockQueryService.getStockPrice(date,
        portfolio.getSymbols(date));
    return portfolio.getPortfolioWithValue(date, prices);
  }

  @Override
  public double getCostBasis(LocalDate date) throws Exception {
    Map<String, StockPrice> prices = new HashMap<>();
    for (var entry : portfolio.getTransactions()) {
      for (var p : stockQueryService.getStockPrice(entry.getDate(), List.of(entry.getSymbol()))
          .entrySet()) {
        prices.put(entry.getDate() + p.getKey(), p.getValue());
      }
    }
    return portfolio.getCostBasis(date, prices);
  }

  @Override
  public Map<LocalDate, Double> getValues(LocalDate from, LocalDate to) {
    Map<LocalDate, Double> map = new HashMap<>();
    for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
      Map<String, StockPrice> prices;
      try {
        prices = stockQueryService.getStockPrice(date, portfolio.getSymbols(date));
        map.put(date, portfolio.getPortfolioWithValue(date, prices).getTotalValue());
      } catch (Exception ignored) {
      }
    }
    return map;
  }

  /**
   * This is a method to the performance for a portfolio on a certain timespan.
   *
   * @param from the start date
   * @param to   the end date
   * @return the PortfolioPerformance entity
   */
  @Override
  public PortfolioPerformance getPerformance(LocalDate from, LocalDate to) {
    if (to.compareTo(from) < 0) {
      throw new IllegalArgumentException("endDate cannot be before than startDate");
    }
    Map<LocalDate, Double> map = getValues(from.minusDays(7), to);
    Map<String, Double> perf = new LinkedHashMap<>();
    Map<String, Integer> scaledPerf = new LinkedHashMap<>();
    String scale;
    long dayCount = ChronoUnit.DAYS.between(from, to) + 1;
    long weekCount = ChronoUnit.WEEKS.between(from, to) + 1;
    long monthCount = ChronoUnit.MONTHS.between(from, to) + 1;
    long yearCount = ChronoUnit.YEARS.between(from, to) + 1;
    LocalDate currentDate = from;

    // divide the time span
    if (dayCount >= 5 && dayCount <= 30) {
      //output the list
      while (!currentDate.isAfter(to)) {
        if (map.containsKey(currentDate)) {
          perf.put(currentDate + ": ", map.get(currentDate));
          currentDate = currentDate.plusDays(1);
        } else {
          LocalDate currentGet = currentDate.minusDays(1);
          while (!map.containsKey(currentGet)) {
            currentGet = currentGet.minusDays(1);
          }
          perf.put(currentDate + ": ", map.get(currentGet));
          currentDate = currentDate.plusDays(1);
        }
      }
    } else if (weekCount >= 5 && weekCount <= 23) {
      //split it to weeks
      // find the last working day of this week
      while (!currentDate.isAfter(to)) {
        LocalDate currentWeekEnd = currentDate.with(ChronoField.DAY_OF_WEEK, 7);
        LocalDate currentWeekGet = currentDate.with(ChronoField.DAY_OF_WEEK, 5);
        if (currentWeekEnd.isAfter(to)) {
          currentWeekEnd = to;
          currentWeekGet = to;
        }
        while (!map.containsKey(currentWeekGet)) {
          currentWeekGet = currentWeekGet.minusDays(1);
        }
        perf.put("Week: " + currentDate + " to " + currentWeekEnd + ": ", map.get(currentWeekGet));
        currentDate = currentWeekEnd.plusDays(1);
      }
    } else if (monthCount >= 5 && monthCount <= 30) {
      //split it to month
      // find the last working day of this month
      while (!currentDate.isAfter(to)) {
        LocalDate currentMonthEnd = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        LocalDate currentMonthGet = currentMonthEnd;
        if (currentMonthEnd.isAfter(to)) {
          currentMonthEnd = to;
          currentMonthGet = to;
        }
        while (!map.containsKey(currentMonthGet)) {
          currentMonthGet = currentMonthGet.minusDays(1);
        }
        perf.put(currentMonthEnd.getYear() +
            "-" + currentMonthEnd.getMonthValue() + ": ", map.get(currentMonthGet));
        currentDate = currentMonthEnd.plusDays(1);
      }
    } else if ((monthCount / 3) >= 5 && (monthCount / 3) < 29) {
      // split it to quarter
      // find the last working day of this month
      while (!currentDate.isAfter(to)) {
        LocalDate currentMonthEnd = getQuarterEnd(currentDate);
        LocalDate currentMonthGet = currentMonthEnd;
        if (currentMonthEnd.isAfter(to)) {
          currentMonthEnd = to;
          currentMonthGet = to;
        }
        while (!map.containsKey(currentMonthGet)) {
          currentMonthGet = currentMonthGet.minusDays(1);
        }
        perf.put(currentMonthEnd.getYear() + "-Quarter " +
            (currentMonthEnd.getMonthValue() / 3 + 1) + ": ", map.get(currentMonthGet));
        currentDate = currentMonthEnd.plusDays(1);
        //monthCount = monthCount -3;
      }
    } else if (yearCount >= 5 && yearCount <= 30) {
      //split it to year
      //fins the last working day of this year
      while (!currentDate.isAfter(to)) {
        LocalDate currentYearEnd = currentDate.withDayOfYear(currentDate.lengthOfYear());
        LocalDate currentYearGet = currentYearEnd;
        if (currentYearEnd.isAfter(to)) {
          currentYearEnd = to;
          currentYearGet = to;
        }
        while (!map.containsKey(currentYearGet)) {
          currentYearGet = currentYearGet.minusDays(1);
        }
        perf.put(currentDate.getYear() + ": ", map.get(currentYearGet));
        currentDate = currentYearEnd.plusDays(1);
        //yearCount--;
      }
    } else {
      throw new IllegalArgumentException("Date range is too short.");
    }

    // prepare for calculate scale
    List<Double> listAmount = new ArrayList<>(perf.values());
    Double maxAmount = Collections.max(listAmount);
    Double minAmount = Collections.min(listAmount);
    // the timespan is before the date of its first purchase
    if (maxAmount.equals(0.0) && minAmount.equals(0.0)) {
      scale = "all the performance are 0";
      for (var entry : perf.entrySet()) {
        scaledPerf.put(entry.getKey(), 0);
      }
      return new PortfolioPerformance(scaledPerf, scale);
    }
    // the min can not be zero when we calculate the scale
    if (minAmount.equals(0.0)) {
      for (Double aDouble : listAmount) {
        if (aDouble.equals(0.0)) {
          continue;
        } else if (minAmount.equals(0.0)) {
          minAmount = aDouble;
        } else {
          if (aDouble < minAmount) {
            minAmount = aDouble;
          }
        }
      }
    }

    // calculate the scale and add "*" to star list
    if (maxAmount.equals(minAmount)) {
      scale = "one asterisk is $" + maxAmount;
      for (var entry : perf.entrySet()) {
        int star = !entry.getValue().equals(0.0) ? 1 : 0;
        scaledPerf.put(entry.getKey(), star);
      }
    } else {
      double more = (maxAmount - minAmount) / 45;
      double base = minAmount - more - 1;

      scale = "one asterisk is" + more + "more than a base amount of $" + base;

      for (var entry : perf.entrySet()) {
        int star = 0;
        if (!entry.getValue().equals(0.0)) {
          for (int j = 0; j < (int) ((entry.getValue() - base) / more); j++) {
            star = star + 1;
          }
        }
        scaledPerf.put(entry.getKey(), star);
      }
    }
    return new PortfolioPerformance(scaledPerf, scale);
  }


  /**
   * This is a helper method to find the last day of the quarter which the given date falls.
   *
   * @param localDate the date that we want to check
   * @returnthe last day of the quarter which the given date falls.
   */
  private static LocalDate getQuarterEnd(LocalDate localDate) {
    localDate = localDate.withDayOfMonth(localDate.lengthOfMonth());
    long month = localDate.getMonth().getValue();
    if (month <= 3) {
      localDate = localDate.withMonth(3);
      localDate = localDate.withDayOfMonth(localDate.lengthOfMonth());
    } else if (month <= 6) {
      localDate = localDate.withMonth(6);
      localDate = localDate.withDayOfMonth(localDate.lengthOfMonth());
    } else if (month <= 9) {
      localDate = localDate.withMonth(9);
      localDate = localDate.withDayOfMonth(localDate.lengthOfMonth());
    } else {
      localDate = localDate.withMonth(12);
      localDate = localDate.withDayOfMonth(localDate.lengthOfMonth());
    }
    return localDate;
  }

  @Override
  public String getString() throws Exception {
    return portfolioParser.toString(portfolio);
  }
}
