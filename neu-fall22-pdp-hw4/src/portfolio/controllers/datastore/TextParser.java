package portfolio.controllers.datastore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.InflexiblePortfolio;

public class TextParser implements PortfolioParser{
  public Portfolio parse(String str) {

    Map<String, Integer> map = new HashMap<>();

    for (String line : str.split("\n")) {
      line = line.replace("\r", "");
      String[] stock = line.split(",");
      map.put(stock[0], map.getOrDefault(stock[0], 0) + Integer.parseInt(stock[1]));
    }

    return new InflexiblePortfolio(map);
  }

  /**
   * Transfer the portfolio into a string format. The format is "symbol,amount". The amount means
   * shares.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  public String toString(InflexiblePortfolio portfolio) {
    List<Transaction> stocks = portfolio.getStocks();
    StringBuilder builder = new StringBuilder();

    for (var entry : stocks) {
      builder.append(entry.getSymbol()).append(",").append(entry.getAmount()).append("\n");
    }

    return builder.toString();
  }
}
