package portfolio.entities;

public class StockPrice {
  private final double open;
  private final double high;
  private final double low;
  private final double close;
  private final long volume;

  public StockPrice(double open, double high, double low, double close, long volume) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  public double getOpen() {
    return open;
  }

  public double getClose() {
    return close;
  }

  public double getHigh() {
    return high;
  }

  public double getLow() {
    return low;
  }

  public long getVolume() {
    return volume;
  }
}
