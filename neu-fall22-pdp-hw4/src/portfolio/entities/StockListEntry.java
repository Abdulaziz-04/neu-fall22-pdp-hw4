package portfolio.entities;

/**
 * This is a class represent a stock list entry.
 */
public class StockListEntry {

  private final String symbol;
  private final String name;
  private final String exchange;
  private final String assetType;
  private final String ipoDate;
  private final String delistingDate;
  private final String status;

  /**
   * This is a constructor that to construct a stock list entry, which contains symbol,name,
   * exchange,assetType,ipoDate,delistingDate,status
   *
   * @param symbol the symbol of a stock
   * @param name the name of a stock
   * @param exchange the exchange of a stock
   * @param assetType the assertType of a stock
   * @param ipoDate the ipo date of a stock
   * @param delistingDate the delisting date
   * @param status the status of this entry
   */
  public StockListEntry(String symbol, String name, String exchange, String assetType,
      String ipoDate, String delistingDate, String status) {
    this.symbol = symbol;
    this.name = name;
    this.exchange = exchange;
    this.assetType = assetType;
    this.ipoDate = ipoDate;
    this.delistingDate = delistingDate;
    this.status = status;
  }

  /**
   * Return the symbol of this stock.
   *
   * @return the symbol of this stock.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   *
   * @return
   */
  public String getAssetType() {
    return assetType;
  }

  /**
   *
   * @return
   */
  public String getDelistingDate() {
    return delistingDate;
  }

  /**
   *
   * @return
   */
  public String getExchange() {
    return exchange;
  }

  /**
   *
   * @return
   */
  public String getIpoDate() {
    return ipoDate;
  }

  /**
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return
   */
  public String getStatus() {
    return status;
  }
}
