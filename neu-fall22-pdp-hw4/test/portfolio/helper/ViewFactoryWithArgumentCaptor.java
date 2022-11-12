package portfolio.helper;

import java.util.List;
import java.util.Map;
import portfolio.controllers.PageController;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.views.View;
import portfolio.views.ViewFactory;
import portfolio.views.impl.InflexibleCreatePageView;
import portfolio.views.impl.InfoPageView;
import portfolio.views.impl.LoadPageView;
import portfolio.views.impl.MainPageView;

/**
 * ViewFactory using ArgumentCaptor.
 */
public class ViewFactoryWithArgumentCaptor implements ViewFactory {

  private final ArgumentCaptor<Object> argumentCaptor;

  public ViewFactoryWithArgumentCaptor(ArgumentCaptor<Object> argumentCaptor) {
    this.argumentCaptor = argumentCaptor;
  }

  @Override
  public View newInfoPageView(PortfolioWithValue portfolioWithPrice,
      String errorMessage) {
    argumentCaptor.addArgument(portfolioWithPrice);
    argumentCaptor.addArgument(errorMessage);
    return new InfoPageView(portfolioWithPrice, errorMessage);
  }

  @Override
  public View newInflexibleCreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map,
      String errorMessage) {
    argumentCaptor.addArgument(isEnd);
    argumentCaptor.addArgument(isNamed);
    argumentCaptor.addArgument(map);
    argumentCaptor.addArgument(errorMessage);
    return new InflexibleCreatePageView(isEnd, isNamed, map, errorMessage);
  }

  @Override
  public View newFlexibleCreatePageView(Boolean isEnd, Boolean isNamed,
      List<Transaction> transactions, String errorMessage) {
    return null;
  }

  @Override
  public View newLoadPageView(Portfolio portfolio, String errorMessage) {
    argumentCaptor.addArgument(portfolio);
    argumentCaptor.addArgument(errorMessage);
    return new LoadPageView(portfolio, errorMessage);
  }

  @Override
  public View newMainPageView(String errorMessage, boolean isInitFailed) {
    argumentCaptor.addArgument(errorMessage);
    argumentCaptor.addArgument(isInitFailed);
    return new MainPageView(errorMessage, isInitFailed);
  }

}
