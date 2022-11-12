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
  protected Map<String, Integer> stocks;

  protected PortfolioAbs(List<Transaction> transactions) {
    this.transactions = transactions;
    if (transactions.get(0).getDate() != null) {
      transactions.sort(Comparator.comparing(Transaction::getDate));
    }
    setStocks();
  }

  private void setStocks() {
    Map<String, Integer> stocks = new LinkedHashMap<>();
    for (var tx : transactions) {
      int current = stocks.getOrDefault(tx.getSymbol(), 0);
      int newShare = current + tx.getAmount() * TransactionType.getMultiplier(tx.getType());
      if (newShare >= 0) {
        stocks.put(tx.getSymbol(), newShare);
      }
      else {
        throw new RuntimeException("There is a conflict in the input transaction.");
      }
    }
    this.stocks = stocks;

  }

  /**
   * Get a list of PortfolioEntry in this portfolio.
   *
   * @return an immutable list of PortfolioEntry
   */
  @Override
  public Map<String, Integer> getStocks() {
    return Collections.unmodifiableMap(stocks);
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
  public List<String> getSymbols() {
    return transactions.stream().map(Transaction::getSymbol)
        .collect(Collectors.toUnmodifiableList());
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

    for (var entry : stocks.entrySet()) {
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
