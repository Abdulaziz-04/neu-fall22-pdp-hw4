package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.FlexibleCreatePageController;
import portfolio.controllers.impl.InflexibleCreatePageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;

/**
 * This is a test class to test MainPageController class.
 */
public class MainPageControllerTest {

  private final PortfolioParser parser = new PortfolioTextParser();
  private PortfolioModel portfolioModel;
  private ViewFactory viewFactory;
  private StockQueryService stockQueryService;
  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;

  @Before
  public void setUp() throws Exception {
    argumentCaptor = new ArgumentCaptor<>();
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    pageController = new MainPageController(portfolioModel, viewFactory);
  }

  @Test
  public void failToInit() {
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);
    pageController = new MainPageController(portfolioModel, viewFactory);

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
  public void handleInput_toCreateInflexible() {
    PageController nextPage = pageController.handleInput("1");
    assertEquals(InflexibleCreatePageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_toCreateFlexible() {
    PageController nextPage = pageController.handleInput("2");
    assertEquals(FlexibleCreatePageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_toDetermine() {
    PageController nextPage = pageController.handleInput("3");
    assertEquals(LoadPageController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_wrongInput1() {
    PageController nextPage = pageController.handleInput("4");
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
