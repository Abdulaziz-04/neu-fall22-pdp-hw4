package portfolio.controllers.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.DollarCostAverageRunner;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

public class OneTimeStrategyPageSwingControllerTest {

  private ArgumentCaptor<Object> argumentCaptor;
  private SwingPageController pageController;
  private ViewFactory viewFactory;

  private List<Transaction> transactions = new ArrayList<>();
  private PortfolioModel portfolioModel;
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    PortfolioParser parser = new PortfolioTextParser();
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser,
        new DollarCostAverageRunner(stockQueryService));
    argumentCaptor = new ArgumentCaptor<>();
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    transactions.add(
        new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 12));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 34));
    transactions.add(
        new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 56));

    portfolioModel.create("a", PortfolioFormat.FLEXIBLE, transactions);
    pageController = new OneTimeStrategyPageSwingController(portfolioModel, viewFactory);
  }

  @Test
  public void getView_init() {
    pageController.getView();
    assertEquals(0, ((Map<String, Double>) argumentCaptor.getArguments().get(0)).size());
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handelInput_back() {
    SwingPageController nextPage = pageController.handleInput("back");
    assertEquals(LoadPageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_addToStockList() {
    SwingPageController nextPage = pageController.handleInput("AAPL,100");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals(1, ((Map<String, Double>) argumentCaptor.getArguments().get(0)).size());
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handleInput_negativeShare() {
    SwingPageController nextPage = pageController.handleInput("AAPL,-10");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals(0, ((Map<String, Double>) argumentCaptor.getArguments().get(0)).size());
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals("The weight cannot be negative and 0.", argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handleInput_shareNotIntOrNumber() {
    SwingPageController nextPage = pageController.handleInput("AAPL,abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals(0, ((Map<String, Double>) argumentCaptor.getArguments().get(0)).size());
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals("The weight is not a number.", argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handleInput_negativeCommissionFee() {
    SwingPageController nextPage = pageController.handleInput("AAPL,10");
    nextPage = pageController.handleInput("yes");
    nextPage = pageController.handleInput("2000.0,2022-10-10,-1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Commission cannot be negative.", argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handleInput_commissionFeeNotNumber() {
    SwingPageController nextPage = pageController.handleInput("AAPL,10");
    nextPage = pageController.handleInput("yes");
    nextPage = pageController.handleInput("2000.0,2022-10-10,abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Commission fee not number", argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handleInput_wrongDateFormat() {
    SwingPageController nextPage = pageController.handleInput("AAPL,10");
    nextPage = pageController.handleInput("yes");
    nextPage = pageController.handleInput("2000.0,2022-10-1021,abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("For input string: \"abc\"", argumentCaptor.getArguments().get(3));
  }

  @Test
  public void handleInput_wrongAmountFormat() {
    SwingPageController nextPage = pageController.handleInput("AAPL,10");
    nextPage = pageController.handleInput("yes");
    nextPage = pageController.handleInput("-2000.0,2022-10-10,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Amount cannot be less than zero Please enter transaction list again.",
        argumentCaptor.getArguments().get(3));
  }

}