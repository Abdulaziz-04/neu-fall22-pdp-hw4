package portfolio.entities;

/**
 *
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
   *
   * @param symbol
   * @param name
   * @param exchange
   * @param assetType
   * @param ipoDate
   * @param delistingDate
   * @param status
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
   *
   * @return
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
