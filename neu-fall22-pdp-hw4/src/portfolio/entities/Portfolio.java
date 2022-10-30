package portfolio.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Portfolio {

  private final List<PortfolioEntry> stocks;

  public Portfolio(List<PortfolioEntry> stocks){
    this.stocks = stocks;
  }

  public Portfolio(Map<String, Integer> stocks){
    List<PortfolioEntry> portfolioEntries = new ArrayList<>();
    for (var entry: stocks.entrySet()) {
      var portfolioEntry = new PortfolioEntry(entry.getKey(), entry.getValue());
      portfolioEntries.add(portfolioEntry);
    }
    this.stocks = portfolioEntries;
  }

  public List<PortfolioEntry> getStocks() {
    return Collections.unmodifiableList(stocks);
  }
  public List<String> getSymbols() {
    var list = stocks.stream().map(x -> x.getSymbol()).collect(Collectors.toList());
    return Collections.unmodifiableList(list);
  }

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
