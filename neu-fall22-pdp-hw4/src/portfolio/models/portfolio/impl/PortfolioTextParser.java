package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioParser;

public class PortfolioTextParser implements PortfolioParser {

  public PortfolioFormat parseFormat(String str) throws Exception {
    String[] line = str.split("\n");
    line[0] = line[0].replace("\r", "");
    String[] format = line[0].split("=");
    if (format[0].equals("FORMAT")) {
      return PortfolioFormat.parse(format[1]);
    }
    return PortfolioFormat.INFLEXIBLE;
  }

  public List<Transaction> parseTransaction(String str) throws Exception {
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
            new Transaction(TransactionType.parse(stock[1]), stock[2], Integer.parseInt(stock[3]),
                LocalDate.parse(stock[0]), Double.parseDouble(stock[4])));
      } else if (stock.length == 2) {
        transactions.add(new Transaction(stock[0], Integer.parseInt(stock[1])));
      } else {
        throw new Exception("Wrong Transaction format.");
      }
    }
    return transactions;
  }

  /**
   * Transfer the portfolio into a string format. The format is "symbol,amount". The amount means
   * shares.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  public String toString(Portfolio portfolio) {
    List<Transaction> transactions = portfolio.getTransaction();
    StringBuilder builder = new StringBuilder();
    builder.append("FORMAT=").append(portfolio.getFormat()).append("\n");

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
    return builder.toString();
  }
}
