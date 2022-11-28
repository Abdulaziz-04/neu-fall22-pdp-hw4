package portfolio.views.gui;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import portfolio.controllers.InputHandler;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a class that can generate different view, which implement the view factory.
 */
public class SwingViewFactory implements ViewFactory {

  private final InputHandler inputHandler;
  private final JFrame frame;

  public SwingViewFactory(JFrame frame, InputHandler inputHandler) {
    this.frame = frame;
    this.inputHandler = inputHandler;
  }

  @Override
  public View newInfoPageView(PortfolioWithValue portfolioWithPrice, Double costOfBasis,
      String errorMessage) {
    return null;
  }

  @Override
  public View newInflexibleCreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map,
      String errorMessage) {
    return null;
  }

  @Override
  public View newFlexibleCreatePageView(Boolean isEnd, Boolean isNamed, int stage,
      List<Transaction> transactions, String errorMessage) {
    return null;
  }

  @Override
  public View newLoadPageView(Portfolio portfolio, boolean showModifyMenu, String errorMessage) {
    return null;
  }

  @Override
  public View newMainPageView(String errorMessage, boolean isInitFailed) {
    return new MainPageSwingView(frame, inputHandler, errorMessage, isInitFailed);
  }

  @Override
  public View newPerformacePageView(String portfolioName,
      LocalDate startDate,
      LocalDate endDate,
      Map<String, Integer> performance,
      String scale, boolean isFinish, String errorMessage) {
    return null;
  }

}
