package portfolio.controllers.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.FlexiblePortfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

/**
 * Test class for LoadPageSwingController.
 */
public class LoadPageSwingControllerTest {
  private final PortfolioParser parser = new PortfolioTextParser();
  private ArgumentCaptor<Object> argumentCaptor;
  private PortfolioModel portfolioModel;
  private SwingPageController pageController;
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    argumentCaptor = new ArgumentCaptor<>();
    ViewFactory viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);
    pageController = new LoadPageSwingController(portfolioModel, viewFactory);
  }


  @Test
  public void getView_init() {
    pageController.getView();

    assertNull(argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileExists() {
    SwingPageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);

    pageController.getView();

    List<Transaction> actual = ((FlexiblePortfolio) argumentCaptor.getArguments()
            .get(0)).getTransactions();
    List<Transaction> expectedTx = new ArrayList<>();
    expectedTx.add(
            new Transaction(TransactionType.BUY, "AAA", 100, LocalDate.parse("2022-10-10"), 1));
    expectedTx.add(
            new Transaction(TransactionType.BUY, "AAPL", 100, LocalDate.parse("2022-10-10"), 2));
    expectedTx.add(
            new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-11"), 3));
    expectedTx.add(
            new Transaction(TransactionType.BUY, "AAA", 12, LocalDate.parse("2022-10-11"), 4));
    List<Transaction> expected = new FlexiblePortfolio("name",
            expectedTx).getTransactions();

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), actual.get(i).getSymbol());
      assertEquals(expected.get(i).getAmount(), actual.get(i).getAmount(), EPSILON);
    }
    assertTrue((boolean) argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileParseFail() {
    SwingPageController nextPage = pageController.handleInput("test/resources/parsefail");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("For input string: \"abc\"",
            argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileParseFail2() {
    SwingPageController nextPage = pageController.handleInput("test/resources/parsefail2");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Symbol [APPL] not found.",
            argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileNotFound() {
    SwingPageController nextPage = pageController.handleInput("nofile");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("nofile.txt (The system cannot find the file specified)",
            argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileExists_Determine() {
    SwingPageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);
    assertEquals(FlexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("1");
    assertEquals(InfoPageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExists_Performance() {
    SwingPageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);
    assertEquals(FlexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("2");
    assertEquals(PerformancePageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExists_Modify() {
    SwingPageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);
    assertEquals(FlexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    pageController.getView();
    assertEquals(true, argumentCaptor.getArguments().get(1));

    nextPage = pageController.handleInput("3");
    assertEquals(FlexibleCreatePageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExistsInflex_Determine() {
    SwingPageController nextPage = pageController.handleInput("test/resources/inflex");
    assertEquals(pageController, nextPage);
    assertEquals(InflexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("1");
    assertEquals(InfoPageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExistsInflex_Performance() {
    SwingPageController nextPage = pageController.handleInput("test/resources/inflex");
    assertEquals(pageController, nextPage);
    assertEquals(InflexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("2");
    assertEquals(PerformancePageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExistsInflex_Modify() {
    SwingPageController nextPage = pageController.handleInput("test/resources/inflex");
    assertEquals(pageController, nextPage);
    assertEquals(InflexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    pageController.getView();
    assertEquals(false, argumentCaptor.getArguments().get(1));
    argumentCaptor.clear();

    nextPage = pageController.handleInput("3");
    assertEquals(pageController, nextPage);
    pageController.getView();
    assertEquals("Please input valid number.", argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileExists_wrongInput() {
    SwingPageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);

    nextPage = pageController.handleInput("abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Please input valid number.", argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_back() {
    SwingPageController nextPage = pageController.handleInput("back");
    assertEquals(MainPageSwingController.class, nextPage.getClass());
  }

}