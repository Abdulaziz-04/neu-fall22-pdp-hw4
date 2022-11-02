package portfolio.entities;

/**
 * This is a class represent the entry to get the portfolio with value. This class contains
 * the symbol of a stock and the amount(shares) of a stock and the value of this stock.
 */
public class PortfolioEntryWithValue {
  private final String symbol;
  private final int amount;
  private final Double value;

  /**
   * This is a constructor to construct a PortfolioEntryWithValue object.
   *
   * @param portfolioEntry the entry for portfolio
   * @param value the value of this stock
   */
  public PortfolioEntryWithValue(PortfolioEntry portfolioEntry, Double value){
    this.value = value;
    this.symbol = portfolioEntry.getSymbol();
    this.amount = portfolioEntry.getAmount();
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
  public int getAmount() {
    return amount;
  }
}
