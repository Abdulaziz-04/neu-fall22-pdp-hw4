package portfolio.views;

import java.util.Map;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;

public interface ViewFactory {

  View newInfoPageView(PortfolioWithValue portfolioWithPrice,
      String errorMessage);

  View newCreatePageView(Boolean isEnd, Boolean isNamed,
      Map<String, Integer> map, String errorMessage);

  View newLoadPageView(Portfolio portfolio, String errorMessage);

  View newMainPageView(String errorMessage);
}
