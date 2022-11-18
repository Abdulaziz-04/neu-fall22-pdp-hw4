package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.FlexibleCreatePageController;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.controllers.impl.PerformancePageController;
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
 * This is a test class to test LoadPageController class.
 */
public class LoadPageControllerTest {

  private final PortfolioParser parser = new PortfolioTextParser();
  private ArgumentCaptor<Object> argumentCaptor;
  private PortfolioModel portfolioModel;
  private PageController pageController;

  @Before
  public void setUp() throws Exception {
    argumentCaptor = new ArgumentCaptor<>();
    ViewFactory viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser);
    pageController = new LoadPageController(portfolioModel, viewFactory);
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
    PageController nextPage = pageController.handleInput("test/resources/flex");
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
      assertEquals(expected.get(i).getAmount(), actual.get(i).getAmount());
    }
    assertTrue((boolean) argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileParseFail() {
    PageController nextPage = pageController.handleInput("test/resources/parsefail");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Wrong Transaction format.",
        argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileParseFail2() {
    PageController nextPage = pageController.handleInput("test/resources/parsefail2");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Symbol [APPL] not found.",
        argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileNotFound() {
    PageController nextPage = pageController.handleInput("nofile");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("nofile.txt (The system cannot find the file specified)",
        argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_portfolioFileExists_Determine() {
    PageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);
    assertEquals(FlexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("1");
    assertEquals(InfoPageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExists_Performance() {
    PageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);
    assertEquals(FlexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("2");
    assertEquals(PerformancePageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExists_Modify() {
    PageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);
    assertEquals(FlexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    pageController.getView();
    assertEquals(true, argumentCaptor.getArguments().get(1));

    nextPage = pageController.handleInput("3");
    assertEquals(FlexibleCreatePageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExistsInflex_Determine() {
    PageController nextPage = pageController.handleInput("test/resources/inflex");
    assertEquals(pageController, nextPage);
    assertEquals(InflexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("1");
    assertEquals(InfoPageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExistsInflex_Performance() {
    PageController nextPage = pageController.handleInput("test/resources/inflex");
    assertEquals(pageController, nextPage);
    assertEquals(InflexiblePortfolio.class, portfolioModel.getPortfolio().getClass());

    nextPage = pageController.handleInput("2");
    assertEquals(PerformancePageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExistsInflex_Modify() {
    PageController nextPage = pageController.handleInput("test/resources/inflex");
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
    PageController nextPage = pageController.handleInput("test/resources/flex");
    assertEquals(pageController, nextPage);

    nextPage = pageController.handleInput("abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Please input valid number.", argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_back() {
    PageController nextPage = pageController.handleInput("back");
    assertEquals(MainPageController.class, nextPage.getClass());
  }


}
