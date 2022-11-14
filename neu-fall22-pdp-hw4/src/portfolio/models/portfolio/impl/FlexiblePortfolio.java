package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;

public class FlexiblePortfolio extends PortfolioAbs {

  /**
   * This is a constructor to a portfolio object from list of PortfolioEntry.
   *
   * @param transactions a list of PortfolioEntry
   */
  public FlexiblePortfolio(String name, List<Transaction> transactions) {
    super(name, transactions);
  }

  @Override
  public PortfolioFormat getFormat() {
    return PortfolioFormat.FLEXIBLE;
  }

  @Override
  public Portfolio create(List<Transaction> transactions) {
    return new FlexiblePortfolio(name, transactions);
  }

  @Override
  public double getCostBasis(LocalDate date, Map<String, StockPrice> prices) {
    double total = 0;
    for (var entry : transactions) {
      StockPrice price = prices.get(entry.getSymbol());
      if (price != null) {
        double value = price.getClose() * entry.getAmount();
        total = total + value - entry.getCommissionFee();
      }
    }
    return total;
  }

  @Override
  public boolean isReadOnly() {
    return false;
  }

}
