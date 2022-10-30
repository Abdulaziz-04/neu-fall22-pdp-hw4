package portfolio.entities;

public class PortfolioEntry {
  private final String symbol;
  private final int amount;

  public PortfolioEntry(String symbol, int amount){
    this.symbol = symbol;
    this.amount = amount;
  }

  public String getSymbol() {
    return symbol;
  }

  public int getAmount() {
    return amount;
  }
}
