package portfolio.mock;

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
public class ViewFactoryWithArgumentCaptor implements ViewFactory {

  private final ArgumentCaptor<Object> argumentCaptor;
  public ViewFactoryWithArgumentCaptor(ArgumentCaptor<Object> argumentCaptor){
    this.argumentCaptor = argumentCaptor;
  }

  @Override
  public View newInfoPageView(PortfolioWithValue portfolioWithPrice,
      String errorMessage){
    argumentCaptor.addArgument(portfolioWithPrice);
    argumentCaptor.addArgument(errorMessage);
    return new InfoPageView(portfolioWithPrice, errorMessage);
  }

  @Override
  public View newCreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map, String errorMessage){
    argumentCaptor.addArgument(isEnd);
    argumentCaptor.addArgument(isNamed);
    argumentCaptor.addArgument(map);
    argumentCaptor.addArgument(errorMessage);
    return new CreatePageView(isEnd, isNamed, map, errorMessage);
  }

  @Override
  public View newLoadPageView(Portfolio portfolio, String errorMessage){
    argumentCaptor.addArgument(portfolio);
    argumentCaptor.addArgument(errorMessage);
    return new LoadPageView(portfolio, errorMessage);
  }

  @Override
  public View newMainPageView(String errorMessage, boolean isInitFailed){
    argumentCaptor.addArgument(errorMessage);
    argumentCaptor.addArgument(isInitFailed);
    return new MainPageView(errorMessage, isInitFailed);
  }

}
