package portfolio.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is a class that represent a portfolio object, which contains a list of portfolio entry.
 */
public class Portfolio implements IPortfolio {

  private final List<PortfolioEntry> stocks;

  /**
   * This is a constructor to a portfolio object from list of PortfolioEntry.
   *
   * @param stocks a list of PortfolioEntry
   */
  public Portfolio(List<PortfolioEntry> stocks) {
    this.stocks = stocks;
  }

  /**
   * Construct Portfolio from a map of symbol (String) and no. of shares (Integer)
   *
   * @param stocks a map of symbol (String) and no. of shares (Integer)
   */
  public Portfolio(Map<String, Integer> stocks) {
    List<PortfolioEntry> portfolioEntries = new ArrayList<>();
    for (var entry : stocks.entrySet()) {
      var portfolioEntry = new PortfolioEntry(entry.getKey(), entry.getValue());
      portfolioEntries.add(portfolioEntry);
    }
    this.stocks = portfolioEntries;
  }

  /**
   * Get a list of PortfolioEntry in this portfolio.
   *
   * @return an immutable list of PortfolioEntry
   */
  @Override
  public List<PortfolioEntry> getStocks() {
    return Collections.unmodifiableList(stocks);
  }

  /**
   * Get a list of stock symbol in this portfolio.
   *
   * @return an immutable list of stock symbol string
   */
  @Override
  public List<String> getSymbols() {
    return stocks.stream().map(PortfolioEntry::getSymbol).collect(Collectors.toUnmodifiableList());
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

    for (var entry : stocks) {
      Double value = null;
      StockPrice price = prices.get(entry.getSymbol());
      if (price != null) {
        value = price.getClose() * entry.getAmount();
        total = total + value;
      }
      portfolioEntryWithValues.add(new PortfolioEntryWithValue(entry, value));
    }

    return new PortfolioWithValue(date, portfolioEntryWithValues, total);
  }
}
