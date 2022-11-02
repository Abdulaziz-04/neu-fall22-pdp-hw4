package portfolio.entities;

/**
 * This is a class that represent a stock price.
 */
public class StockPrice {
  private final double open;
  private final double high;
  private final double low;
  private final double close;
  private final long volume;

  /**
   * This is a constructor that construct a stock price object, which contains open price,
   * the highest price, the lowest price, the close price and volume in a day.
   *
   * @param open the price for a stock at opening time
   * @param high the highest price of a stock on that day
   * @param low the lowest price of a stock on that day
   * @param close the price for a stock at the closing time
   * @param volume the volume of a stock in that day
   */
  public StockPrice(double open, double high, double low, double close, long volume) {
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  /**
   * Return the price for a stock at opening time.
   *
   * @return the price for a stock at opening time
   */
  public double getOpen() {
    return open;
  }

  /**
   * Return the price for a stock at closing time.
   *
   * @return the price for a stock at closing time
   */
  public double getClose() {
    return close;
  }

  /**
   * Return the highest price of a stock on that day.
   *
   * @return the highest price of a stock on that day
   */
  public double getHigh() {
    return high;
  }

  /**
   * Return the lowest price of a stock on that day.
   *
   * @return the lowest price of a stock on that day
   */
  public double getLow() {
    return low;
  }

  /**
   * Return the volume of a stock in that day.
   *
   * @return the volume of a stock in that day
   */
  public long getVolume() {
    return volume;
  }
}
