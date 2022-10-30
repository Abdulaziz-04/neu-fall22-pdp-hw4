package portfolio.entities;

import java.time.LocalDate;
import java.util.List;

public class PortfolioWithValue {

  private final LocalDate date;
  private final List<PortfolioEntryWithValue> stocks;

  private final double totalValue;


  public PortfolioWithValue(LocalDate date, List<PortfolioEntryWithValue> stocks, double totalValue) {
    this.date = date;
    this.stocks = stocks;
    this.totalValue = totalValue;
  }

  public LocalDate getDate() {
    return date;
  }

  public List<PortfolioEntryWithValue> getStocks() {
    return stocks;
  }

  public double getTotalValue() {
    return totalValue;
  }
}
