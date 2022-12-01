package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioParser;

/**
 * Class for parsing text to transactions or portfolio, implements PortfolioParser.
 */
public class PortfolioTextParser implements PortfolioParser {

  protected int parseVersion(String str) throws Exception {
    Matcher m = Pattern.compile("\\[INFO\\][\\s\\S]*?[\\r\\n]{2}")
        .matcher(str);
    boolean match = m.find();
    if (match) {
      Matcher versionMatcher = Pattern.compile("VERSION=(.*)")
          .matcher(str);
      versionMatcher.find();
      return Integer.parseInt(versionMatcher.group(1));
    } else {
      Matcher formatMatcher = Pattern.compile("FORMAT=(.*)")
          .matcher(str);
      boolean hasFormat = formatMatcher.find();
      if (hasFormat) {
        return 2;
      }
    }
    return 1;
  }

  protected PortfolioFormat parseFormat(String str) throws Exception {
    String[] format = null;
    try {
      format = Arrays.stream(str.split("\n")).filter(x -> x.contains("FORMAT=")).collect(
          Collectors.toList()).get(0).replace("\r", "").split("=");
    } catch (Exception ignored) {
    }
    if (format != null && format[0].equals("FORMAT")) {
      return PortfolioFormat.parse(format[1]);
    }
    return PortfolioFormat.INFLEXIBLE;
  }

  protected List<Transaction> parseTransaction(String str) throws Exception {
    String[] line = str.split("\n");
    List<Transaction> transactions = new ArrayList<>();
    for (var i = 0; i < line.length; i++) {
      line[i] = line[i].replace("\r", "");
      String[] stock = line[i].split(",");
      if (stock.length == 1) {
        continue;
      }
      if (stock.length == 5) {
        transactions.add(
            new Transaction(TransactionType.parse(stock[1]), stock[2], Double.parseDouble(stock[3]),
                LocalDate.parse(stock[0]), Double.parseDouble(stock[4])));
      } else if (stock.length == 2) {
        transactions.add(new Transaction(stock[0], Double.parseDouble(stock[1])));
      } else {
        throw new Exception("Wrong Transaction format.");
      }
    }
    return transactions;
  }

  protected DollarCostAverageSchedule parseSchedule(String str) throws Exception {
    Matcher m = Pattern.compile("NAME=(.*)").matcher(str);
    m.find();
    String name = m.group(1);
    m = Pattern.compile("AMOUNT=(.*)").matcher(str);
    m.find();
    double amount = Double.parseDouble(m.group(1));
    m = Pattern.compile("SCHEDULE=(.*)").matcher(str);
    m.find();
    String schedule = m.group(1);
    String[] splited = schedule.split(",");
    m = Pattern.compile("TRANSACTION_FEE=(.*)").matcher(str);
    m.find();
    double transactionFee = Double.parseDouble(m.group(1));
    m = Pattern.compile("LAST_RUN_DATE=(.*)").matcher(str);
    m.find();
    LocalDate lastRunDate = m.group(1).isEmpty() ? null : LocalDate.parse(m.group(1));

    List<String> lines = new ArrayList<>();
    for (var line : str.split("\n")) {
      if (!line.contains("=") && !line.contains("[")) {
        lines.add(line);
      }
    }

    List<Transaction> transactions = parseTransaction(String.join("\n", lines));

    return new DollarCostAverageSchedule(
        name,
        amount,
        Integer.parseInt(splited[0]),
        LocalDate.parse(splited[1]),
        !Objects.equals(splited[2], "null") ? LocalDate.parse(splited[2]) : null,
        transactionFee,
        lastRunDate,
        transactions);
  }

  @Override
  public Portfolio parse(String str) throws Exception {
    str = str.replaceAll("\r", "");
    int version = parseVersion(str);
    PortfolioFormat format = parseFormat(str);

    if (version == 3) {
      Matcher m = Pattern.compile("\\[SCHEDULE\\]([\\s\\S]*?[\\r\\n]{2})")
          .matcher(str);
      List<BuySchedule> buySchedules = new ArrayList<>();
      while (m.find()) {
        String scheduleStr = m.group(1);
        buySchedules.add(parseSchedule(scheduleStr));
      }

      Matcher txMatcher = Pattern.compile("\\[TRANSACTION\\]([\\s\\S]*?[\\r\\n]{2})")
          .matcher(str);
      txMatcher.find();
      String txStr = txMatcher.group(1);
      List<Transaction> transactions = parseTransaction(txStr);
      switch (format) {
        case INFLEXIBLE:
          return new InflexiblePortfolio(null, transactions);
        case FLEXIBLE:
          return new FlexiblePortfolio(null, transactions, buySchedules);
        default:
          throw new Exception("Unsupported Portfolio type.");
      }
    } else if (version == 2) {
      String txStr = str.substring(str.indexOf('\n') + 1);
      List<Transaction> transactions = parseTransaction(txStr);
      switch (format) {
        case INFLEXIBLE:
          return new InflexiblePortfolio(null, transactions);
        case FLEXIBLE:
          return new FlexiblePortfolio(null, transactions);
        default:
          throw new Exception("Unsupported Portfolio type.");
      }
    } else if (version == 1) {
      List<Transaction> transactions = parseTransaction(str);
      return new InflexiblePortfolio(null, transactions);
    } else {
      throw new Exception("Unsupported version.");
    }
  }

  /**
   * Transfer the portfolio into a string format. The format is "symbol,amount". The amount means
   * shares.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  public String toString(Portfolio portfolio) {
    List<Transaction> transactions = portfolio.getTransactions();
    StringBuilder builder = new StringBuilder();
    builder.append("[INFO]\n");
    builder.append("FORMAT=").append(portfolio.getFormat()).append("\n");
    builder.append("VERSION=3\n\n");

    List<BuySchedule> schedules = portfolio.getBuySchedules();
    if (schedules != null) {
      for (var schedule : schedules) {
        builder.append("[SCHEDULE]\n");
        builder.append(String.format("NAME=%s\n"
                + "TYPE=%s\n"
                + "AMOUNT=%.2f\n"
                + "SCHEDULE=%d,%s,%s\n"
                + "TRANSACTION_FEE=%.2f\n"
                + "LAST_RUN_DATE=%s\n", schedule.getName(), schedule.getType(), schedule.getAmount(),
            schedule.getFrequencyDays(),
            schedule.getStartDate(), schedule.getEndDate(), schedule.getTransactionFee(),
            schedule.getLastRunDate()));
        for (var entry : schedule.getBuyingList()) {
          builder.append(String.format("%s, %.2f\n", entry.getSymbol(), entry.getAmount()));
        }
        builder.append("\n");
      }
    }

    builder.append("[TRANSACTION]\n");
    for (var entry : transactions) {
      String[] list = new String[]{
          entry.getDate() == null ? null : String.valueOf(entry.getDate()),
          TransactionType.toString(entry.getType()), entry.getSymbol(),
          String.valueOf(entry.getAmount()),
          entry.getCommissionFee() == null ? null : String.valueOf(entry.getCommissionFee())
      };
      String joined = Arrays.stream(list).filter(Objects::nonNull).collect(Collectors.joining(","));
      builder.append(joined).append("\n");
    }
    builder.append("\n");
    return builder.toString();
  }
}
