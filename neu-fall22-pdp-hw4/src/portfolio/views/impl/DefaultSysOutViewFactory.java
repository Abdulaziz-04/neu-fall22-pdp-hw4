package portfolio.views.impl;

import java.util.Map;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;
import portfolio.views.View;
import portfolio.views.ViewFactory;
import portfolio.views.impl.CreatePageView;
import portfolio.views.impl.InfoPageView;
import portfolio.views.impl.LoadPageView;
import portfolio.views.impl.MainPageView;

/**
 * This is a class that can generate different controller.
 */
public class DefaultSysOutViewFactory implements ViewFactory {

  @Override
  public View newInfoPageView(PortfolioWithValue portfolioWithPrice,
      String errorMessage){
    return new InfoPageView(portfolioWithPrice, errorMessage);
  }

  @Override
  public View newCreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map, String errorMessage){
    return new CreatePageView(isEnd, isNamed, map, errorMessage);
  }

  @Override
  public View newLoadPageView(Portfolio portfolio, String errorMessage){
    return new LoadPageView(portfolio, errorMessage);
  }

  @Override
  public View newMainPageView(String errorMessage){
    return new MainPageView(errorMessage);
  }

}
