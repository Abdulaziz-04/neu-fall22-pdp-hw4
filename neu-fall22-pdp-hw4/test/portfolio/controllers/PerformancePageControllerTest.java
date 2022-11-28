package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.PerformancePageController;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

/**
 * This is a test class to test PerformancePageController class.
 */
public class PerformancePageControllerTest {

  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;

  private final List<Transaction> transactions = new ArrayList<>();
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    PortfolioParser parser = new PortfolioTextParser();
    PortfolioModel portfolioModel = new PortfolioModelImpl(stockQueryService, parser,null);
    argumentCaptor = new ArgumentCaptor<>();
    ViewFactory viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);

    transactions.add(
        new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 12));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 34));
    transactions.add(
        new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 56));

    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    pageController = new PerformancePageController(portfolioModel, viewFactory);
  }

  @Test
  public void getView_init() {
    pageController.getView();
    assertEquals("name", argumentCaptor.getArguments().get(0));
    assertNull(argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(2));
    assertNull(argumentCaptor.getArguments().get(4));
    assertFalse((boolean) argumentCaptor.getArguments().get(5));
    assertNull(argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handelInput_back() {
    PageController nextPage = pageController.handleInput("back");
    assertEquals(LoadPageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_wrongStartDate() {
    PageController nextPage = pageController.handleInput("10/10/2022");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Error start date format!",
        argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handleInput_wrongEndDate() {
    pageController.handleInput("2022-10-10");
    PageController nextPage = pageController.handleInput("11/10/2022");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Error end date format!",
        argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handleInput_startDateNoData() {
    pageController.handleInput("2022-11-01");
    pageController.getView();
    assertEquals(
        "Error: Please choose input new timespan.This start date maybe the "
            + "holiday, weekend, or in the future!",
        argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handleInput_endDateBeforeStartDate() {
    pageController.handleInput("2022-10-10");
    PageController nextPage = pageController.handleInput("2022-10-01");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("endDate cannot be before than startDate",
        argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handleInput_startDateInFuture() {
    pageController.handleInput("2042-10-10");
    pageController.getView();
    assertEquals(
        "Error: Please choose input new timespan.This start date maybe the "
            + "holiday, weekend, or in the future!",
        argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handleInput_endDateInFuture() {
    pageController.handleInput("2022-10-10");
    PageController nextPage = pageController.handleInput("2042-10-10");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Error: EndDate cannot be in future.",
        argumentCaptor.getArguments().get(6));
  }

  @Test
  public void handleInput_rangeTooShort() {
    pageController.handleInput("2022-10-10");
    PageController nextPage = pageController.handleInput("2022-10-11");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Date range is too short.",
        argumentCaptor.getArguments().get(6));
  }

}
