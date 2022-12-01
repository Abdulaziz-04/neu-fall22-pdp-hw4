package portfolio.views;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import portfolio.controllers.InputHandler;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.Portfolio;

/**
 * This is the interface that will generate different views.
 */
public interface ViewFactory {

  /**
   * To generate the view of determine page.
   *
   * @param portfolioWithPrice the portfolio with price
   * @param errorMessage       the error message will show to the user
   * @return a new view of determine page
   */
  View newInfoPageView(PortfolioWithValue portfolioWithPrice, Double costOfBasis,
      String errorMessage);

  /**
   * To generate the view of create page.
   *
   * @param isEnd        the user finish input the portfolio or not
   * @param isNamed      the user named the portfolio or not
   * @param transactions the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message will show to the user
   * @return a new view of create page
   */
  View newInflexibleCreatePageView(Boolean isEnd, Boolean isNamed,
      Map<String, Integer> transactions, String errorMessage);

  View newFlexibleCreatePageView(Boolean isEnd, Boolean isNamed, int stage,
      List<String> inputBuffer,
      List<Transaction> transactions, String errorMessage);

  /**
   * To generate the view of examine page.
   *
   * @param portfolio    the portfolio that we want to examine
   * @param errorMessage the error message will show to the user
   * @return a new view of examine page
   */
  View newLoadPageView(Portfolio portfolio, boolean showModifyMenu, String errorMessage);

  /**
   * To generate the view of main menu page.
   *
   * @param errorMessage the error message will show to the user
   * @param isInitFailed is initialization failed
   * @return return a new view of main menu page
   */
  View newMainPageView(String errorMessage, boolean isInitFailed);

  /**
   * To generate the view of performance page.
   *
   * @param portfolioName the name of portfolio
   * @param startDate     the start date to performance
   * @param endDate       the end date to performance
   * @param performance   performance map
   * @param scale         the scale of performance
   * @param isFinish      finish current performance is true. Otherwise, false.
   * @param errorMessage  the error message will show to the user
   * @return a new view of performance page
   */
  View newPerformacePageView(String portfolioName,
      LocalDate startDate,
      LocalDate endDate,
      Map<String, Integer> performance,
      String scale, boolean isFinish,
      String errorMessage);

  View newScheduleCreatePageView(boolean isEnd, List<String> inputBuffer,
      List<Transaction> transactions, String errorMessage);

  View newScheduleInfoPageView(BuySchedule schedule, String errorMessage);
}
