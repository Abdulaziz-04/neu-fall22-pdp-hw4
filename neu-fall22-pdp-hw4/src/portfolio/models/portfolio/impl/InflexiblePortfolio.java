package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.Portfolio;

/**
 * This is a class that represent a portfolio object, which extends the PortfolioAbs class.
 */
public class InflexiblePortfolio extends PortfolioAbs {

  /**
   * This is a constructor to an inflexible portfolio object, which will contain the name of this
   * portfolio and a list of transaction entry.
   *
   * @param name         the name of a portfolio
   * @param transactions a list of transaction entry
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
  public Portfolio create(List<Transaction> transactions, List<BuySchedule> schedule) throws Exception {
    throw new Exception("Buy schedule is not supported.");
  }

  @Override
  public double getCostBasis(LocalDate date, Map<String, StockPrice> prices) throws Exception {
    throw new Exception("Cost basis function is not supported.");
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

  @Override
  public List<BuySchedule> getBuySchedules()  {
    return null;
  }

}
