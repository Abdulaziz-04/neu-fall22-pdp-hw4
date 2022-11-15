package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;

/**
 * This is a class that represent a flexible portfolio, which implement the PortfolioAbs
 * class.
 */
public class FlexiblePortfolio extends PortfolioAbs {


  /**
   * This is a constructor to a flexible portfolio object, which will contain the name of this
   * portfolio and a list of transaction entry.
   *
   * @param name the name of a portfolio
   * @param transactions a list of transaction entry
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
      if (entry.getDate().compareTo(date) < 0) {
        StockPrice price = prices.get(entry.getSymbol());
        if (price != null) {
          total = total + price.getClose() * entry.getAmount();
        }
        total = total + entry.getCommissionFee();
      }
    }
    return total;
  }

  @Override
  public boolean isReadOnly() {
    return false;
  }

}
