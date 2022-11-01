package portfolio.entities;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public class PortfolioWithValue {

  private final LocalDate date;
  private final List<PortfolioEntryWithValue> stocks;

  private final double totalValue;

  /**
   *
   * @param date
   * @param stocks
   * @param totalValue
   */
  public PortfolioWithValue(LocalDate date, List<PortfolioEntryWithValue> stocks, double totalValue) {
    this.date = date;
    this.stocks = stocks;
    this.totalValue = totalValue;
  }

  /**
   *
   * @return
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   *
   * @return
   */
  public List<PortfolioEntryWithValue> getStocks() {
    return stocks;
  }

  /**
   *
   * @return
   */
  public double getTotalValue() {
    return totalValue;
  }
}
