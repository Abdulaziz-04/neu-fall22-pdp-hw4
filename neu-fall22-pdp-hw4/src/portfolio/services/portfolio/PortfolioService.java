package portfolio.services.portfolio;

import java.io.IOException;
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
   * @throws IOException if file cannot be parsed
   */
  Portfolio getPortfolio(String fileName) throws IOException;

  /**
   * Save a Portfolio to file.
   *
   * @param portfolio Portfolio object
   * @param fileName save location
   * @return boolean true if successfully saved otherwise false
   * @throws IllegalArgumentException if filepath is not valid
   */
  boolean saveToFile(Portfolio portfolio, String fileName) throws IllegalArgumentException;


}
