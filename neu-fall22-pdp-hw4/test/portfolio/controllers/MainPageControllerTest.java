package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
  private final StockQueryService stockQueryService = new StockQueryServiceImpl(
      new StockApiMock(false));
  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;

  @Before
  public void setUp() {
    argumentCaptor = new ArgumentCaptor<>();
    ViewFactory viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService,
        stockQueryService, viewFactory);
    pageController = new MainPageController(pageControllerFactory, viewFactory);
  }

  @Test
  public void getView() {
    pageController.getView();
    assertNull(argumentCaptor.getArguments().get(0));
  }

  @Test
  public void handleCommand_toCreate() throws Exception {
    PageController nextPage = pageController.handleCommand("1");
    assertEquals(CreatePageController.class, nextPage.getClass());
  }

  @Test
  public void handleCommand_toDetermine() throws Exception {
    PageController nextPage = pageController.handleCommand("2");
    assertEquals(LoadPageController.class, nextPage.getClass());
  }

  @Test
  public void handleCommand_wrongInput1() throws Exception {
    PageController nextPage = pageController.handleCommand("3");
    assertEquals(MainPageController.class, nextPage.getClass());
    pageController.getView();
    assertEquals("Please enter the correct number!", argumentCaptor.getArguments().get(0));
  }

  @Test
  public void handleCommand_wrongInput2() throws Exception {
    PageController nextPage = pageController.handleCommand("abc");
    assertEquals(MainPageController.class, nextPage.getClass());
    pageController.getView();
    assertEquals("Please enter the correct number!", argumentCaptor.getArguments().get(0));
  }
}
