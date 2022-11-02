package portfolio.controllers;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import portfolio.entities.Portfolio;
import portfolio.mock.ArgumentCaptor;
import portfolio.mock.IOServiceMock;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.portfolio.PortfolioServiceImpl;
import portfolio.services.stockprice.StockQueryService;
import portfolio.views.ViewFactory;

/**
 * This is a test class to test CreatePageController class.
 */
public class CreatePageControllerTest {

  private final PortfolioService portfolioService = new PortfolioServiceImpl(new IOServiceMock());
  private ViewFactory viewFactory;
  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;
  private StockQueryService stockQueryService;
  private PageControllerFactory pageControllerFactory;
  private final Map<String, Integer> map = new HashMap<>();
  private Portfolio portfolio;

  private final double EPSILON = 0.000000001;

  @Test
  public void test(){

  }
}
