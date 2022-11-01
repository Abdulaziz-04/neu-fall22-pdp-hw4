package portfolio.entities;

/**
 *
 */
public class StockPrice {
  private final double open;
  private final double high;
  private final double low;
  private final double close;
  private final long volume;

  /**
   *
   * @param open
   * @param high
   * @param low
   * @param close
   * @param volume
   */
  public StockPrice(double open, double high, double low, double close, long volume) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  /**
   *
   * @return
   */
  public double getOpen() {
    return open;
  }

  /**
   *
   * @return
   */
  public double getClose() {
    return close;
  }

  /**
   *
   * @return
   */
  public double getHigh() {
    return high;
  }

  /**
   *
   * @return
   */
  public double getLow() {
    return low;
  }

  /**
   *
   * @return
   */
  public long getVolume() {
    return volume;
  }
}
