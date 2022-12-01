package portfolio.helper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.views.View;
import portfolio.views.ViewFactory;
import portfolio.views.impl.FlexibleCreatePageView;
import portfolio.views.impl.InflexibleCreatePageView;
import portfolio.views.impl.InfoPageView;
import portfolio.views.impl.LoadPageView;
import portfolio.views.impl.MainPageView;
import portfolio.views.impl.PerformancePageView;

/**
 * ViewFactory using ArgumentCaptor.
 */
public class ViewFactoryWithArgumentCaptor implements ViewFactory {

  private final ArgumentCaptor<Object> argumentCaptor;

  public ViewFactoryWithArgumentCaptor(ArgumentCaptor<Object> argumentCaptor) {
    this.argumentCaptor = argumentCaptor;
  }

  @Override
  public View newInfoPageView(PortfolioWithValue portfolioWithPrice, Double costOfBasis,
      String errorMessage) {
    argumentCaptor.addArgument(portfolioWithPrice);
    argumentCaptor.addArgument(costOfBasis);
    argumentCaptor.addArgument(errorMessage);
    return new InfoPageView(portfolioWithPrice, costOfBasis, errorMessage);
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
  public View newFlexibleCreatePageView(Boolean isEnd, Boolean isNamed, int state,
      List<String> inputBuffer,
      List<Transaction> transactions, String errorMessage) {
    argumentCaptor.clear();
    argumentCaptor.addArgument(isEnd);
    argumentCaptor.addArgument(isNamed);
    argumentCaptor.addArgument(state);
    argumentCaptor.addArgument(inputBuffer);
    argumentCaptor.addArgument(transactions);
    argumentCaptor.addArgument(errorMessage);
    return new FlexibleCreatePageView(isEnd, isNamed, state, inputBuffer, transactions,
        errorMessage);
  }

  @Override
  public View newLoadPageView(Portfolio portfolio, boolean showModifyMenu, String errorMessage) {
    argumentCaptor.addArgument(portfolio);
    argumentCaptor.addArgument(showModifyMenu);
    argumentCaptor.addArgument(errorMessage);
    return new LoadPageView(portfolio, showModifyMenu, errorMessage);
  }

  @Override
  public View newMainPageView(String errorMessage, boolean isInitFailed) {
    argumentCaptor.addArgument(errorMessage);
    argumentCaptor.addArgument(isInitFailed);
    return new MainPageView(errorMessage, isInitFailed);
  }

  @Override
  public View newPerformacePageView(String portfolioName,
      LocalDate startDate,
      LocalDate endDate,
      Map<String, Integer> performance,
      String scale, boolean isFinish, String errorMessage) {
    argumentCaptor.addArgument(portfolioName);
    argumentCaptor.addArgument(startDate);
    argumentCaptor.addArgument(endDate);
    argumentCaptor.addArgument(performance);
    argumentCaptor.addArgument(scale);
    argumentCaptor.addArgument(isFinish);
    argumentCaptor.addArgument(errorMessage);
    return new PerformancePageView(portfolioName, startDate, endDate, performance, scale, isFinish,
        errorMessage);
  }

  @Override
  public View newScheduleCreatePageView(Map<String, Double> stockList, boolean isEnd,
      List<String> inputBuffer, List<Transaction> transactions, String errorMessage) {
    return null;
  }

  @Override
  public View newScheduleInfoPageView(BuySchedule schedule, String errorMessage) {
    return null;
  }

}
