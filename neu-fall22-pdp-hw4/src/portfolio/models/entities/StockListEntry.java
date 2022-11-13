package portfolio.models.entities;

import java.time.LocalDate;

/**
 * This is a class representing a stock list entry. It contains stock symbol and basic information
 * of each stock.
 */
public class StockListEntry {

  private final String symbol;
  private final LocalDate ipoDate;

  /**
   * This is a constructor that to construct a stock list entry, which contains symbol, name,
   * exchange, assetType, ipoDate, delistingDate and status.
   *
   * @param symbol        the symbol of a stock
   * @param name          the name of a stock
   * @param exchange      the exchange of a stock
   * @param assetType     the assertType of a stock
   * @param ipoDate       the ipo date of a stock
   * @param delistingDate the delisting date
   * @param status        the status of this entry
   */
  public StockListEntry(String symbol, String name, String exchange, String assetType,
      LocalDate ipoDate, String delistingDate, String status) {
    this.symbol = symbol;
    this.ipoDate = ipoDate;
  }

  /**
   * Return the symbol of this stock.
   *
   * @return the symbol of this stock.
   */
  public String getSymbol() {
    return symbol;
  }

  public LocalDate getIpoDate() {
    return ipoDate;
  }

}
