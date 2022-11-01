package portfolio.entities;

/**
 *
 */
public class PortfolioEntry {
  private final String symbol;
  private final int amount;

  /**
   *
   * @param symbol
   * @param amount
   */
  public PortfolioEntry(String symbol, int amount){
    this.symbol = symbol;
    this.amount = amount;
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
  public int getAmount() {
    return amount;
  }
}
