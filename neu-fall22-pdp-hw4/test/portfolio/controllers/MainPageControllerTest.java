package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.CreatePageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
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
 * This is a test class to test MainPageController class.
 */
public class MainPageControllerTest {

  private final PortfolioService portfolioService = new PortfolioServiceImpl(new IOServiceMock());
  private ViewFactory viewFactory;
  private StockQueryService stockQueryService;
  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;

  @Before
  public void setUp() {
    argumentCaptor = new ArgumentCaptor<>();
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService,
        stockQueryService, viewFactory);
    pageController = new MainPageController(stockQueryService, pageControllerFactory, viewFactory);
  }

  @Test
  public void failToInit() {
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService,
        stockQueryService, viewFactory);
    pageController = new MainPageController(stockQueryService, pageControllerFactory, viewFactory);

    pageController.getView();
    assertNull(argumentCaptor.getArguments().get(0));
    assertTrue((boolean) argumentCaptor.getArguments().get(1));
  }

  @Test
  public void getView() {
    pageController.getView();
    assertNull(argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handleInput_toCreate() {
    PageController nextPage = pageController.handleInput("1");
    assertEquals(CreatePageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_toDetermine() {
    PageController nextPage = pageController.handleInput("2");
    assertEquals(LoadPageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_wrongInput1() {
    PageController nextPage = pageController.handleInput("3");
    assertEquals(MainPageController.class, nextPage.getClass());
    pageController.getView();
    assertEquals("Please enter the correct number!", argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handleInput_wrongInput2() {
    PageController nextPage = pageController.handleInput("abc");
    assertEquals(MainPageController.class, nextPage.getClass());
    pageController.getView();
    assertEquals("Please enter the correct number!", argumentCaptor.getArguments().get(0));
    assertFalse((boolean) argumentCaptor.getArguments().get(1));
  }
}
