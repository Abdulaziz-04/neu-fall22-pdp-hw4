package portfolio.services;

import java.time.LocalDate;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioValue;

/**
 * Service interface for getting stock prices in the portfolio.
 */
public interface PortfolioValueService {

  /**
   * Get price of all stock in the portfolio on specific date. Return PortfolioValue object which
   * contains queried date and a map of price in the corresponding date.
   *
   * @param portfolio Portfolio object
   * @param date      date
   * @return PortfolioValue object
   */
  PortfolioValue getValue(Portfolio portfolio, LocalDate date);

}
