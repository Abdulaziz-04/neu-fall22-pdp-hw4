package portfolio.controllers.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.StockApiMock;
import portfolio.helper.ViewFactoryWithArgumentCaptor;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.DollarCostAverageRunner;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;
import portfolio.views.ViewFactory;


public class ScheduleInfoSwingControllerTest {
  private final PortfolioParser parser = new PortfolioTextParser();
  private PortfolioModel portfolioModel;
  private ViewFactory viewFactory;
  private StockQueryService stockQueryService;
  private ArgumentCaptor<Object> argumentCaptor;
  private SwingPageController pageController;
  private List<Transaction> transactions = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    PortfolioParser parser = new PortfolioTextParser();
    portfolioModel = new PortfolioModelImpl(stockQueryService, parser, new DollarCostAverageRunner(stockQueryService));
    argumentCaptor = new ArgumentCaptor<>();
    viewFactory = new ViewFactoryWithArgumentCaptor(argumentCaptor);

    transactions.add(
        new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 12));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 34));
    transactions.add(
        new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 56));

    portfolioModel.create("a", PortfolioFormat.FLEXIBLE, transactions);
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    portfolioModel.addSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList);
    pageController = new ScheduleInfoSwingController("name", portfolioModel, viewFactory);
  }

  @Test
  public void handleInput_back() {
    SwingPageController nextPage = pageController.handleInput("back");
    assertEquals(LoadPageSwingController.class, nextPage.getClass());
  }

  @Test
  public void getView() {
    pageController.getView();
    BuySchedule schedule = (BuySchedule) argumentCaptor.getArguments().get(0);
    assertEquals(2, schedule.getBuyingList().size());
    assertEquals("name", schedule.getName());
    assertNull(argumentCaptor.getArguments().get(1));
  }

}