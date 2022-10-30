package portfolio.services;

import portfolio.entities.Portfolio;

/**
 * Service interface for creating and retrieving Portfolio.
 */
public interface PortfolioService {

  /**
   * Get Portfolio object from file.
   *
   * @param fileName filename to load the portfolio
   * @return Portfolio object
   */
  Portfolio getPortfolio(String fileName);

  /**
   * Save a Portfolio to file.
   *
   * @param portfolio Portfolio object
   * @param fileName save location
   * @return boolean true if successfully saved otherwise false
   */
  boolean saveToFile(Portfolio portfolio, String fileName) throws Exception;


}
