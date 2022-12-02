package portfolio.controllers.gui;

import org.junit.Before;
import org.junit.Test;


import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
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


/**
 * Test class for MainPageSwingController.
 */
public class MainPageSwingControllerTest {
  private final PortfolioParser parser = new PortfolioTextParser();
  private PortfolioModel portfolioModel;
  private ViewFactory viewFactory;
  private StockQueryService stockQueryService;
  private ArgumentCaptor<Object> argumentCaptor;
  private SwingPageController pageController;

  @Before
  public void setUp() throws Exception {
    argumentCaptor = new ArgumentCaptor<>();
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);
    pageController = new MainPageSwingController(portfolioModel, viewFactory);
  }

  @Test
  public void failToInit() {
    stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, null);
    pageController = new MainPageSwingController(portfolioModel, viewFactory);

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
  public void handleInput_toCreateFlexible() {
    SwingPageController nextPage = pageController.handleInput("2");
    assertEquals(FlexibleCreatePageSwingController.class, nextPage.getClass());
  }

  @Test
  public void handleInput_toDetermine() {
    SwingPageController nextPage = pageController.handleInput("3");
    assertEquals(LoadPageSwingController.class, nextPage.getClass());
  }

}