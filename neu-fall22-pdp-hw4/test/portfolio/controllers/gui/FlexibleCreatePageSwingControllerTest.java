package portfolio.controllers.gui;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FlexibleCreatePageSwingControllerTest {
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
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);
    argumentCaptor = new ArgumentCaptor<>();
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);

    transactions.add(
            new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 12));
    transactions.add(
            new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 34));
    transactions.add(
            new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 56));

    pageController = new FlexibleCreatePageSwingController(portfolioModel, viewFactory);
  }

  @Test
  public void getView_init() {
    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    //assertNull(argumentCaptor.getArguments().get(4));
  }

  @Test
  public void handelInput_back() {
    SwingPageController nextPage = pageController.handleInput("back");
    assertEquals(MainPageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_wrongDate() {
    SwingPageController nextPage = pageController.handleInput("10/10/2022,AAPL,BUY,100,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    //assertEquals(0, argumentCaptor.getArguments().get(3)((List<Transaction>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("The format error!",
            argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_wrongShareSymbol() {
    //pageController.handleInput("2022-10-10,APPL,BUY,100,1.23");
    SwingPageController nextPage = pageController.handleInput("2022-10-10,APPL,BUY,100,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("[APPL] is not a valid stock symbol.", argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_noPriceOnDate() {
    SwingPageController nextPage = pageController.handleInput("2022-10-12,AAPL,BUY,100,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("Stock [AAPL] is not available at date 2022-10-12.",
            argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_negativeShare() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,-10,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("The shares cannot be negative.", argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_shareNotIntOrNumber() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,abc,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("For input string: \"abc\"", argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_negativeCommissionFee() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,100,-1");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("Commission cannot be negative.", argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_commissionFeeNotNumber() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,100,abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("For input string: \"abc\"", argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_successTransactionInput() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,100,1.23");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertNull(argumentCaptor.getArguments().get(5));
  }


  @Test
  public void handleInput_successTransaction_confirm() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,100,1.23");
    assertEquals(pageController, nextPage);
    SwingPageController nextPage2 = pageController.handleInput("yes");
    assertEquals(pageController, nextPage2);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertNull(argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_fileExists() {
    pageController.handleInput("2022-10-10,AAPL,BUY,100,1.23");
    SwingPageController nextPage = pageController.handleInput("yes");
    assertEquals(pageController, nextPage);
    SwingPageController nextPage2 = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage2);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(1, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals("There is a file or a directory exists with filename: test/resources/flex.txt",
            argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_modifyMode() throws Exception {
    portfolioModel.load("test/resources/flex", "2022-10-10,BUY,AAA,100,12\n"
            + "2022-10-11,SELL,AAA,100,34\n");
    pageController = new FlexibleCreatePageSwingController(portfolioModel, viewFactory);
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,BUY,100,1.23");
    assertEquals(pageController, nextPage);
    SwingPageController nextPage2 = pageController.handleInput("yes");
    assertEquals(pageController, nextPage2);

    pageController.getView();
    assertTrue((boolean) argumentCaptor.getArguments().get(0));
    assertTrue((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(5, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(3, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertNull(argumentCaptor.getArguments().get(5));
  }

  @Test
  public void handleInput_listEmpty() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10,AAPL,SELL,100,1.23");
    assertEquals(pageController, nextPage);
    SwingPageController nextPage2 = pageController.handleInput("yes");
    assertEquals(pageController, nextPage2);


    pageController.getView();
    assertFalse((boolean) argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertEquals(0, (int) argumentCaptor.getArguments().get(2));
    assertEquals(0, ((List<ArrayList>) argumentCaptor.getArguments().get(3)).size());
    assertEquals(0, ((List<Transaction>) argumentCaptor.getArguments().get(4)).size());
    assertEquals(
            "There is a conflict in the input transaction. Please enter transaction list again.",
            argumentCaptor.getArguments().get(5));
  }

}