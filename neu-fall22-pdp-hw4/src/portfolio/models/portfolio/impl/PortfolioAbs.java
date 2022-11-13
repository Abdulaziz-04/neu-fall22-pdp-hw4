package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import portfolio.models.entities.PortfolioEntryWithValue;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.Portfolio;

public abstract class PortfolioAbs implements Portfolio {

  protected final List<Transaction> transactions;

  protected final String name;

  protected PortfolioAbs(String name, List<Transaction> transactions) {
    this.name = name;
    this.transactions = transactions;
    if (transactions.get(0).getDate() != null) {
      transactions.sort(Comparator.comparing(Transaction::getDate));
    }
    getStocks(null);
  }

  public Map<String, Integer> getStocks(LocalDate date) {
    Map<String, Integer> stocks = new LinkedHashMap<>();
    for (var tx : transactions) {
      if (date != null && tx.getDate() != null && tx.getDate().compareTo(date) > 0) {
        break;
      }
      int current = stocks.getOrDefault(tx.getSymbol(), 0);
      int newShare = current + tx.getAmount() * TransactionType.getMultiplier(tx.getType());
      if (newShare > 0) {
        stocks.put(tx.getSymbol(), newShare);
      }
      else if (newShare == 0) {
        stocks.remove(tx.getSymbol());
      }
      else {
        throw new RuntimeException("There is a conflict in the input transaction.");
      }
    }
    return stocks;
  }

  @Override
  public String getName(){
    return name;
  }
  /**
   * Get a list of PortfolioEntry in this portfolio.
   *
   * @return an immutable list of PortfolioEntry
   */
  @Override
  public Map<String, Integer> getStocks() {
    return Collections.unmodifiableMap(getStocks(null));
  }


  @Override
  public List<Transaction> getTransaction() {
    return Collections.unmodifiableList(transactions);
  }

  /**
   * Get a list of stock symbol in this portfolio.
   *
   * @return an immutable list of stock symbol string
   */
  @Override
  public List<String> getSymbols(LocalDate date) {
    return getStocks(date).keySet().stream().collect(Collectors.toUnmodifiableList());
  }


  /**
   * Calculate value of all stocks and total value of the portfolio from price map.
   *
   * @return PortfolioWithValue object containing calculated date and values
   */
  @Override
  public PortfolioWithValue getPortfolioWithValue(LocalDate date, Map<String, StockPrice> prices) {

    List<PortfolioEntryWithValue> portfolioEntryWithValues = new ArrayList<>();
    double total = 0;

    for (var entry : getStocks(date).entrySet()) {
      Double value = null;
      StockPrice price = prices.get(entry.getKey());
      if (price != null) {
        value = price.getClose() * entry.getValue();
        total = total + value;
      }
      portfolioEntryWithValues.add(
          new PortfolioEntryWithValue(entry.getKey(), entry.getValue(), value));
    }

    return new PortfolioWithValue(date, portfolioEntryWithValues, total);
  }
}
