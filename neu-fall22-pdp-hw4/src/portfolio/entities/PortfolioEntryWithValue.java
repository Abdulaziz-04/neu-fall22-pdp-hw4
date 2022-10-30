package portfolio.entities;

public class PortfolioEntryWithValue {
  private final String symbol;
  private final int amount;
  private final double value;

  public PortfolioEntryWithValue(PortfolioEntry portfolioEntry, double value){
    this.value = value;
    this.symbol = portfolioEntry.getSymbol();
    this.amount = portfolioEntry.getAmount();
  }

  public double getValue() {
    return value;
  }

  public String getSymbol() {
    return symbol;
  }

  public int getAmount() {
    return amount;
  }
}
