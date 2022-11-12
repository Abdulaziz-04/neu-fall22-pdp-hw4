package portfolio.views.impl;

import java.util.Map;
import portfolio.models.portfolio.InflexiblePortfolio;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a class that can generate different view, which implement the view factory.
 */
public class DefaultSysOutViewFactory implements ViewFactory {

  @Override
  public View newInfoPageView(PortfolioWithValue portfolioWithPrice,
      String errorMessage) {
    return new InfoPageView(portfolioWithPrice, errorMessage);
  }

  @Override
  public View newCreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map,
      String errorMessage) {
    return new CreatePageView(isEnd, isNamed, map, errorMessage);
  }

  @Override
  public View newLoadPageView(InflexiblePortfolio portfolio, String errorMessage) {
    return new LoadPageView(portfolio, errorMessage);
  }

  @Override
  public View newMainPageView(String errorMessage, boolean isInitFailed) {
    return new MainPageView(errorMessage, isInitFailed);
  }

}
