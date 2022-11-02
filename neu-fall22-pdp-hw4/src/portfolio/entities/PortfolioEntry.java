package portfolio.entities;

/**
 * This is a class for portfolio entry.
 */
public class PortfolioEntry {

  private final String symbol;
  private final int amount;

  /**
   * This is a constructor to construct a portfolio entry, which contains the symbol and amount. The
   * amount means shares.
   *
   * @param symbol the symbol of stock
   * @param amount the share for this stock
   */
  public PortfolioEntry(String symbol, int amount) {
    this.symbol = symbol;
    this.amount = amount;
  }

  /**
   * This is the method that return the symbol of a stock in a string format.
   *
   * @return the symbol of stock
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * This is the method that return the shares of a stock.
   *
   * @return the shares of a stock
   */
  public int getAmount() {
    return amount;
  }
}
