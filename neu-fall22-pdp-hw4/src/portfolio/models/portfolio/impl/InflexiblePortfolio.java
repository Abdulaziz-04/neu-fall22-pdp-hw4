package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;

/**
 * This is a class that represent a portfolio object, which contains a list of portfolio entry.
 */
public class InflexiblePortfolio extends PortfolioAbs {

  /**
   * This is a constructor to a portfolio object from list of PortfolioEntry.
   *
   * @param transactions a list of PortfolioEntry
   */
  public InflexiblePortfolio(String name, List<Transaction> transactions) {
    super(name, transactions);
  }

  @Override
  public PortfolioFormat getFormat() {
    return PortfolioFormat.INFLEXIBLE;
  }

  @Override
  public Portfolio create(List<Transaction> transactions) throws Exception {
    throw new Exception("Modifying inflexible portfolio is not supported.");
  }

  @Override
  public double getCostBasis(LocalDate date, Map<String, StockPrice> prices) throws Exception {
    throw new Exception("Cost basis function is not supported.");
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

}
