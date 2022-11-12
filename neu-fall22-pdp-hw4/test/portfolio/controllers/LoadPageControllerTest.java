package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.impl.InfoPageController;
import portfolio.controllers.impl.LoadPageController;
import portfolio.controllers.impl.MainPageController;
import portfolio.models.portfolio.InflexiblePortfolio;
import portfolio.models.entities.Transaction;
import portfolio.mock.ArgumentCaptor;
import portfolio.mock.IOServiceMock;
import portfolio.mock.ViewFactoryWithArgumentCaptor;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.portfolio.PortfolioServiceImpl;
import portfolio.views.ViewFactory;

/**
 * This is a test class to test LoadPageController class.
 */
public class LoadPageControllerTest {

  private final PortfolioService portfolioService = new PortfolioServiceImpl(new IOServiceMock());
  private ArgumentCaptor<Object> argumentCaptor;
  private PageController pageController;

  @Before
  public void setUp() {
    argumentCaptor = new ArgumentCaptor<>();
    ViewFactory viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    PageControllerFactory pageControllerFactory = new PageControllerFactory(portfolioService,
        null, viewFactory);
    pageController = new LoadPageController(portfolioService, pageControllerFactory, viewFactory);
  }


  @Test
  public void getView_init() {
    pageController.getView();

    assertNull(argumentCaptor.getArguments().get(0));
    assertNull(argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_portfolioFileExists() {
    PageController nextPage = pageController.handleInput("pass");
    assertEquals(pageController, nextPage);

    pageController.getView();

    List<Transaction> actual = ((InflexiblePortfolio) argumentCaptor.getArguments().get(0)).getStocks();
    Map<String, Integer> map = new HashMap<>();
    map.put("AAPL", 100);
    map.put("AAA", 10000);
    List<Transaction> expected = new InflexiblePortfolio(map).getStocks();

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), actual.get(i).getSymbol());
      assertEquals(expected.get(i).getAmount(), actual.get(i).getAmount());
    }
    assertNull(argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_portfolioFileParseFail() {
    PageController nextPage = pageController.handleInput("parsefail");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("Cannot read portfolio. It may have a wrong format.",
        argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_portfolioFileNotFound() {
    PageController nextPage = pageController.handleInput("abc");
    assertEquals(pageController, nextPage);

    pageController.getView();
    assertEquals("file not found.", argumentCaptor.getArguments().get(1));
  }

  @Test
  public void handelInput_portfolioFileExists_confirm() {
    PageController nextPage = pageController.handleInput("pass");
    assertEquals(pageController, nextPage);

    nextPage = pageController.handleInput("yes");
    assertEquals(InfoPageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExists_notConfirm() {
    PageController nextPage = pageController.handleInput("pass");
    assertEquals(pageController, nextPage);

    nextPage = pageController.handleInput("no");
    assertEquals(MainPageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_portfolioFileExists_wrongInput() {
    PageController nextPage = pageController.handleInput("pass");
    assertEquals(pageController, nextPage);

    nextPage = pageController.handleInput("abc");
    assertEquals(MainPageController.class, nextPage.getClass());
  }

  @Test
  public void handelInput_back() {
    PageController nextPage = pageController.handleInput("back");
    assertEquals(MainPageController.class, nextPage.getClass());
  }


}
