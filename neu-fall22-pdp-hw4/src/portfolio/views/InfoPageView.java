package portfolio.views;

import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioValue;

public interface InfoPageView {

  void showPortfolioValue(Portfolio portfolio, PortfolioValue portfolioValue, String errorMessage);
}
