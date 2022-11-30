package portfolio.models.entities;

/**
 * This is a class represent an entry of the portfolio with current value on specific date. This
 * class contains the symbol of a stock and the amount(shares) of a stock and the value of this
 * stock.
 */
public class PortfolioEntryWithValue {

  private final String symbol;
  private final double amount;
  private final Double value;

  /**
   * This is a constructor to construct a PortfolioEntryWithValue object.
   *
   * @param symbol PortfolioEntry object containing symbol and shares
   * @param value  the value of this stock
   */
  public PortfolioEntryWithValue(String symbol, double amount, Double value) {
    this.value = value;
    this.symbol = symbol;
    this.amount = amount;
  }

  /**
   * Return the value of this stock.
   *
   * @return the value of this stock
   */
  public Double getValue() {
    return value;
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
   * Return the shares of this stock.
   *
   * @return the shares of this stock.
   */
  public double getAmount() {
    return amount;
  }
}
