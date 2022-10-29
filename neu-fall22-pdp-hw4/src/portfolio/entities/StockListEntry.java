package portfolio.entities;

public class StockListEntry {

  private final String symbol;
  private final String name;
  private final String exchange;
  private final String assetType;
  private final String ipoDate;
  private final String delistingDate;
  private final String status;

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

  public String getSymbol() {
    return symbol;
  }

  public String getAssetType() {
    return assetType;
  }

  public String getDelistingDate() {
    return delistingDate;
  }

  public String getExchange() {
    return exchange;
  }

  public String getIpoDate() {
    return ipoDate;
  }

  public String getName() {
    return name;
  }

  public String getStatus() {
    return status;
  }
}
