package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import portfolio.models.entities.PortfolioEntryWithValue;
import portfolio.models.entities.PortfolioWithCostBasis;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;

public class FlexiblePortfolio implements Portfolio {

  private final List<Transaction> transactions;
  private Map<String, Integer> stocks;

  /**
   * This is a constructor to a portfolio object from list of PortfolioEntry.
   *
   * @param transactions a list of PortfolioEntry
   */
  public FlexiblePortfolio(List<Transaction> transactions) {
    this.transactions = transactions;
    setStocks();
  }

  private void setStocks() {
    Map<String, Integer> stocks = new LinkedHashMap<>();
    for (var tx : transactions) {
      int current = stocks.getOrDefault(tx.getSymbol(), 0);
      int multiplier = tx.getType() == TransactionType.BUY ? 1 : -1;
      int newShare = current + multiplier * tx.getAmount();
      if (newShare < 0) {
        throw new IllegalArgumentException("Transaction invalid.");
      }
      stocks.put(tx.getSymbol(), newShare);
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
    return stocks;
  }

  @Override
  public List<Transaction> getTransaction() {
    return transactions.stream().collect(Collectors.toUnmodifiableList());
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

  @Override
  public PortfolioWithCostBasis getCostBasis(LocalDate date, Map<String, StockPrice> prices,
      Double commissionFee) throws Exception {
    throw new Exception("Cost basis function is not supported.");
  }

  @Override
  public Portfolio setTransactions(List<Transaction> transactions) throws Exception {
    throw new Exception("Set tranaction is not supported.");
  }
}
