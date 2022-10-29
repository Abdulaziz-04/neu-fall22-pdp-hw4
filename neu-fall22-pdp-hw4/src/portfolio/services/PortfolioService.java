package portfolio.services;

import portfolio.entities.Portfolio;

public interface PortfolioService {

  Portfolio getPortfolio(String fileName);
  boolean saveToFile(Portfolio portfolio, String fileName) throws Exception;



}
