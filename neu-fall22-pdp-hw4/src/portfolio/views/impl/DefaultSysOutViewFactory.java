package portfolio.views.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
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
  public View newInflexibleCreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map,
      String errorMessage) {
    return new InflexibleCreatePageView(isEnd, isNamed, map, errorMessage);
  }

  @Override
  public View newFlexibleCreatePageView(Boolean isEnd, Boolean isNamed,
      List<Transaction> transactions, String errorMessage) {
    return new FlexibleCreatePageView(isEnd, isNamed, transactions, errorMessage);
  }

  @Override
  public View newLoadPageView(Portfolio portfolio, String errorMessage) {
    return new LoadPageView(portfolio, errorMessage);
  }

  @Override
  public View newMainPageView(String errorMessage, boolean isInitFailed) {
    return new MainPageView(errorMessage, isInitFailed);
  }

  @Override
  public View newPerformacePageView(String portfolioName,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    List<String> list,
                                    List<String> listStar,
                                    String scale, String errorMessage) {
    return new PerformancePageView(portfolioName, startDate, endDate, list,
            listStar, scale, errorMessage);
  }



}
