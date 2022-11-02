package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntryWithValue;
import portfolio.entities.PortfolioWithValue;
import portfolio.mock.ArgumentCaptor;
import portfolio.mock.IOServiceMock;
import portfolio.mock.StockApiMock;
import portfolio.mock.ViewFactoryWithArgumentCaptor;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.portfolio.PortfolioServiceImpl;
import portfolio.services.stockprice.StockQueryService;
import portfolio.services.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

/**
 * This is a test class to test InfoPageController class.
 */
public class InfoPageControllerTest {

  private final PortfolioService portfolioService = new PortfolioServiceImpl(new IOServiceMock());
  private ViewFactory viewFactory;
  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;
  private StockQueryService stockQueryService;
  private PageControllerFactory pageControllerFactory;
  private final Map<String, Integer> map = new HashMap<>();
  private Portfolio portfolio;

  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() {
    argumentCaptor = new ArgumentCaptor<>();
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    pageControllerFactory = new PageControllerFactory(portfolioService, stockQueryService,
        viewFactory);

    map.put("AAPL", 100);
    map.put("AAA", 10000);
    portfolio = new Portfolio(map);

    pageController = new InfoPageController(stockQueryService, portfolio, pageControllerFactory,
        viewFactory);
  }


  @Test
  public void getView_init() {
    pageController.getView();

    assertNull(argumentCaptor.getArguments().get(0));
    assertNull(argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_CorrectDate() {
    PageController nextPage = pageController.handleInput("2022-10-10");
    assertEquals(pageController, nextPage);

    pageController.getView();

    PortfolioWithValue actual = ((PortfolioWithValue) argumentCaptor.getArguments().get(0));
    LocalDate date = LocalDate.parse("2022-10-10");
    assertEquals(date, actual.getDate());
    assertEquals(440400.0, actual.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = actual.getStocks();
    assertEquals(440000, list.get(0).getValue(), EPSILON);
    assertEquals(400, list.get(1).getValue(), EPSILON);

    assertNull(argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_wrongDateFormat() {
    PageController nextPage = pageController.handleInput("10/10/2022");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertNull(argumentCaptor.getArguments().get(0));
    assertEquals("Error! Please input the correct date.", argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_back() {
    PageController nextPage = pageController.handleInput("back");
    assertEquals(MainPageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_serviceFail() {
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    pageController = new InfoPageController(stockQueryService, portfolio, pageControllerFactory,
        viewFactory);

    PageController nextPage = pageController.handleInput("2022-10-10");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertNull(argumentCaptor.getArguments().get(0));
    assertEquals("Something wrong.", argumentCaptor.getArguments().get(1));
  }
}
