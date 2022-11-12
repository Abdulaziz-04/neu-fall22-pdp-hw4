package portfolio.views;

import java.util.Map;
import portfolio.models.portfolio.InflexiblePortfolio;
import portfolio.models.entities.PortfolioWithValue;

/**
 * This is the interface that will generate different views.
 */
public interface ViewFactory {

  /**
   * To generate the view of determine page.
   *
   * @param portfolioWithPrice the portfolio with price
   * @param errorMessage the error message will show to the user
   * @return a new view of determine page
   */
  View newInfoPageView(PortfolioWithValue portfolioWithPrice,
      String errorMessage);

  /**
   * To generate the view of create page.
   *
   * @param isEnd the user finish input the portfolio or not
   * @param isNamed the user named the portfolio or not
   * @param map the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message will show to the user
   * @return a new view of create page
   */
  View newCreatePageView(Boolean isEnd, Boolean isNamed,
      Map<String, Integer> map, String errorMessage);

  /**
   * To generate the view of examine page.
   *
   * @param portfolio the portfolio that we want to examine
   * @param errorMessage the error message will show to the user
   * @return a new view of examine page
   */
  View newLoadPageView(InflexiblePortfolio portfolio, String errorMessage);

  /**
   * To generate the view of main menu page.
   *
   * @param errorMessage the error message will show to the user
   * @param isInitFailed is initialization failed
   * @return return a new view of main menu page
   */
  View newMainPageView(String errorMessage, boolean isInitFailed);
}
