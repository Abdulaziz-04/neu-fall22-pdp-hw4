package portfolio.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class Portfolio implements IPortfolio {

  private final List<PortfolioEntry> stocks;

  /**
   *
   * @param stocks
   */
  public Portfolio(List<PortfolioEntry> stocks){
    this.stocks = stocks;
  }

  /**
   *
   * @param stocks
   */
  public Portfolio(Map<String, Integer> stocks){
    List<PortfolioEntry> portfolioEntries = new ArrayList<>();
    for (var entry: stocks.entrySet()) {
      var portfolioEntry = new PortfolioEntry(entry.getKey(), entry.getValue());
      portfolioEntries.add(portfolioEntry);
    }
    this.stocks = portfolioEntries;
  }

  /**
   *
   * @return
   */
  @Override
  public List<PortfolioEntry> getStocks() {
    return Collections.unmodifiableList(stocks);
  }

  /**
   *
   * @return
   */
  @Override
  public List<String> getSymbols() {
    var list = stocks.stream().map(x -> x.getSymbol()).collect(Collectors.toList());
    return Collections.unmodifiableList(list);
  }

  /**
   * This is the function is to calculate the portfolio price on a certain date. It will return
   * a PortfolioWithValue class that has the total value of this portfolio.
   *
   * @param date the date that we want to determine the price
   * @param prices the price for every stock in this portfolio
   *
   * @return PortfolioWithValue class that has the total value of this portfolio
   */
  @Override
  public PortfolioWithValue getPortfolioWithPrice(LocalDate date, Map<String, StockPrice> prices) {

    List<PortfolioEntryWithValue> portfolioEntryWithValues = new ArrayList<>();
    double total = 0;

    for (var entry: stocks
    ) {
      double value = prices.get(entry.getSymbol()).getClose() * entry.getAmount();
      total = total + value;
      portfolioEntryWithValues.add(new PortfolioEntryWithValue(entry, value));
    }

    return new PortfolioWithValue(date, portfolioEntryWithValues, total);
  }
}
