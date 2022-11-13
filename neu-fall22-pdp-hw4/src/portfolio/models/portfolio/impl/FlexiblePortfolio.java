package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioWithCostBasis;
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
  public PortfolioWithCostBasis getCostBasis(LocalDate date, Map<String, StockPrice> prices,
      Double commissionFee) throws Exception {
    throw new Exception("Cost basis function is not supported.");
  }

  @Override
  public boolean isReadOnly() {
    return false;
  }

}
