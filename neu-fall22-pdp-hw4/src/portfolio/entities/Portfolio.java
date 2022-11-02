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
   * This is a constructor to a portfolio object, which we already
   * have the list of entry of it.
   *
   * @param stocks a list of portfolio entry
   */
  public Portfolio(List<PortfolioEntry> stocks){
    this.stocks = stocks;
  }

  /**
   * This is a constructor to a portfolio object, which do not have the portfolio entry.
   *
   * @param stocks a map type object
   */
  public Portfolio(Map<String, Integer> stocks){
    List<PortfolioEntry> portfolioEntries = new ArrayList<>();
    for (var entry: stocks.entrySet()) {
      var portfolioEntry = new PortfolioEntry(entry.getKey(), entry.getValue());
      portfolioEntries.add(portfolioEntry);
    }
    this.stocks = portfolioEntries;
  }


  @Override
  public List<PortfolioEntry> getStocks() {
    return Collections.unmodifiableList(stocks);
  }


  @Override
  public List<String> getSymbols() {
    return stocks.stream().map(PortfolioEntry::getSymbol).collect(Collectors.toUnmodifiableList());
  }


  @Override
  public PortfolioWithValue getPortfolioWithPrice(LocalDate date, Map<String, StockPrice> prices) {

    List<PortfolioEntryWithValue> portfolioEntryWithValues = new ArrayList<>();
    double total = 0;

    for (var entry: stocks) {
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
