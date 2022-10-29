package portfolio.services;

import portfolio.entities.Portfolio;

public class PortfolioStore {
  private Portfolio portfolio;

  public void setPortfolio(Portfolio portfolio) {
    this.portfolio = portfolio;
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }
}
