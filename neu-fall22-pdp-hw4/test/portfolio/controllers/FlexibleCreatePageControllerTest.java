package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import portfolio.controllers.impl.FlexibleCreatePageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.FlexiblePortfolio;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

/**
 * This is a test class to test CreatePageController class.
 */
public class FlexibleCreatePageControllerTest {

  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;
  private ViewFactory viewFactory;

  private List<Transaction> transactions = new ArrayList<>();
  private PortfolioModel portfolioModel;
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    PortfolioParser parser = new PortfolioTextParser();
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser);
    argumentCaptor = new ArgumentCaptor<>();
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);

    transactions.add(
        new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 12));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 34));
    transactions.add(
        new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 56));

    pageController = new FlexibleCreatePageController(portfolioModel, viewFactory);
  }

  @Test
  public void getView_init() {
    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertNull(argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handelInput_back() {
    PageController nextPage = pageController.handleInput("back");
    assertEquals(MainPageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_wrongDate() {
    PageController nextPage = pageController.handleInput("10/10/2022");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("Text '10/10/2022' could not be parsed at index 0",
        argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_wrongShareSymbol() {
    pageController.handleInput("2022-10-10");
    PageController nextPage = pageController.handleInput("APPL");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("[APPL] is not a valid stock symbol.", argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_noPriceOnDate() {
    pageController.handleInput("2022-10-12");
    PageController nextPage = pageController.handleInput("AAPL");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("Stock [AAPL] is not available at date 2022-10-12.",
        argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_wrongTransactionType() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    PageController nextPage = pageController.handleInput("buy");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(2, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("Transaction type is not supported.", argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_negativeShare() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    PageController nextPage = pageController.handleInput("-10");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(3, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("The shares cannot be negative.", argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_shareNotIntOrNumber() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    PageController nextPage = pageController.handleInput("abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(3, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("The share is not a number or an integer.", argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_negativeCommissionFee() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    PageController nextPage = pageController.handleInput("-1");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(4, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("Commission cannot be negative.", argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_commissionFeeNotNumber() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    PageController nextPage = pageController.handleInput("abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(4, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("Commission fee input is not a number.", argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_successTransactionInput() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    PageController nextPage = pageController.handleInput("1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(5, (int) argumentCaptor.getArguments().get(2));
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertNull(argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_successTransaction_addMore() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    pageController.handleInput("1.23");
    PageController nextPage = pageController.handleInput("yes");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertNull(argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_successTransaction_confirm() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    pageController.handleInput("1.23");
    PageController nextPage = pageController.handleInput("no");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(5, (int) argumentCaptor.getArguments().get(2));
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertNull(argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_fileExists() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    pageController.handleInput("1.23");
    pageController.handleInput("no");
    PageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(5, (int) argumentCaptor.getArguments().get(2));
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals("There is a file or a directory exists with filename: test/resources/flex.txt",
        argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_modifyMode() throws Exception {
    portfolioModel.load("test/resources/flex", "2022-10-10,BUY,AAA,100,12\n"
        + "2022-10-11,SELL,AAA,100,34\n");
    pageController = new FlexibleCreatePageController(portfolioModel, viewFactory);
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("BUY");
    pageController.handleInput("100");
    pageController.handleInput("1.23");
    PageController nextPage = pageController.handleInput("no");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertTrue((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(5, (int) argumentCaptor.getArguments().get(2));
    assertEquals(3, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertNull(argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handleInput_listEmpty() {
    pageController.handleInput("2022-10-10");
    pageController.handleInput("AAA");
    pageController.handleInput("SELL");
    pageController.handleInput("200");
    pageController.handleInput("1.23");
    pageController.handleInput("no");
    PageController nextPage = pageController.handleInput("a");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(
        "There is a conflict in the input transaction. Please enter transaction list again.",
        argumentCaptor.getArguments().get(4));
  }


}
