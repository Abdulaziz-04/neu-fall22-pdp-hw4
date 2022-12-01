package portfolio.controllers.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.TransactionConverter;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.PortfolioEntryWithValue;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

public class InfoPageSwingControllerTest {

  private final PortfolioParser parser = new PortfolioTextParser();
  private ArgumentCaptor<Object> argumentCaptor;
  private SwingPageController pageController;
  private PortfolioModel portfolioModel;
  private final Map<String, Integer> map = new HashMap<>();

  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    argumentCaptor = new ArgumentCaptor<>();
    ViewFactory viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);

    map.put("AAPL", 100);
    map.put("AAA", 10000);
    portfolioModel.create("name", PortfolioFormat.INFLEXIBLE, TransactionConverter.convert(map));
    pageController = new InfoPageSwingController(portfolioModel, viewFactory);
  }


  @Test
  public void getView_init() {
    pageController.getView();

    assertNull(argumentCaptor.getArguments().get(0));
    assertNull(argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_Inflexible() {
    SwingPageController nextPage = pageController.handleInput("2022-10-10");
    assertEquals(pageController, nextPage);

    pageController.getView();

    PortfolioWithValue actual = ((PortfolioWithValue) argumentCaptor.getArguments().get(0));
    LocalDate date = LocalDate.parse("2022-10-10");
    assertEquals(date, actual.getDate());
    assertEquals(440400.0, actual.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = actual.getValues();
    assertEquals(440000, list.get(0).getValue(), EPSILON);
    assertEquals(400, list.get(1).getValue(), EPSILON);

    assertNull(argumentCaptor.getArguments().get(1));
    assertNull(argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_Flexible() throws Exception {
    List<Transaction> expectedTx = new ArrayList<>();
    expectedTx.add(
            new Transaction(TransactionType.BUY, "AAA", 100, LocalDate.parse("2022-10-10"), 1));
    expectedTx.add(
            new Transaction(TransactionType.BUY, "AAPL", 100, LocalDate.parse("2022-10-10"), 2));
    expectedTx.add(
            new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-11"), 3));
    expectedTx.add(
            new Transaction(TransactionType.BUY, "AAA", 12, LocalDate.parse("2022-10-11"), 4));

    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, expectedTx);
    SwingPageController nextPage = pageController.handleInput("2022-10-10");
    assertEquals(pageController, nextPage);

    pageController.getView();

    PortfolioWithValue actual = ((PortfolioWithValue) argumentCaptor.getArguments().get(0));
    LocalDate date = LocalDate.parse("2022-10-10");
    assertEquals(date, actual.getDate());
    assertEquals(4800, actual.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = actual.getValues();
    assertEquals(4400, list.get(0).getValue(), EPSILON);
    assertEquals(400, list.get(1).getValue(), EPSILON);

    assertEquals(4803.0, (double) argumentCaptor.getArguments().get(1), EPSILON);
    assertNull(argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_wrongDateFormat() {
    SwingPageController nextPage = pageController.handleInput("10/10/2022");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertNull(argumentCaptor.getArguments().get(0));
    assertEquals("Error! Please input the correct date.", argumentCaptor.getArguments().get(2));
  }

  @Test
  public void handelInput_back() {
    SwingPageController nextPage = pageController.handleInput("back");
    assertEquals(LoadPageSwingController.class, nextPage.getClass());
  }

}