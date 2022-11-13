package portfolio.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.StockApiMock;
import portfolio.models.entities.PortfolioEntryWithValue;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.portfolio.impl.PortfolioServiceImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;

/**
 * This is a test class to test PortfolioServiceImpl class.
 */
public class PortfolioServiceImplTest {

  private PortfolioService portfolioService;
  private final Map<String, Integer> map = new HashMap<>();
  private final List<Transaction> transactions = new ArrayList<>();
  private final List<Transaction> transactions2 = new ArrayList<>();
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioService = new PortfolioServiceImpl(stockQueryService, new PortfolioTextParser());

    transactions.add(
        new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 10));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 20));
    transactions.add(
        new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 30));
    transactions2.add(
        new Transaction(TransactionType.SELL, "AAPL", 123, LocalDate.parse("2022-10-12"), 40));
    transactions2.add(
        new Transaction(TransactionType.BUY, "AA", 45, LocalDate.parse("2022-10-11"), 50));
  }

  @Test
  public void create_flexible() throws Exception {
    Portfolio actualPortfolio = portfolioService.create(PortfolioFormat.FLEXIBLE, transactions);

    List<Transaction> actual = actualPortfolio.getTransaction();

    assertEquals(PortfolioFormat.FLEXIBLE, actualPortfolio.getFormat());
    assertEquals(transactions.size(), actual.size());
    for (int i = 0; i < transactions.size(); i++) {
      assertEquals(actual.get(i).getType(), transactions.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), transactions.get(i).getAmount());
      assertEquals(actual.get(i).getDate(), transactions.get(i).getDate());
    }
    Map<String, Integer> actualMap = actualPortfolio.getStocks();
    assertEquals(2, actualMap.size());
  }

  @Test
  public void create_inflexible() throws Exception {
    Portfolio actualPortfolio = portfolioService.create(PortfolioFormat.INFLEXIBLE, transactions);

    List<Transaction> actual = actualPortfolio.getTransaction();

    assertEquals(PortfolioFormat.INFLEXIBLE, actualPortfolio.getFormat());
    assertEquals(transactions.size(), actual.size());
    for (int i = 0; i < transactions.size(); i++) {
      assertEquals(actual.get(i).getType(), transactions.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), transactions.get(i).getAmount());
      assertEquals(actual.get(i).getDate(), transactions.get(i).getDate());
    }
    Map<String, Integer> actualMap = actualPortfolio.getStocks();
    assertEquals(2, actualMap.size());
  }

  @Test
  public void getPortfolio() throws Exception {
    portfolioService.create(PortfolioFormat.INFLEXIBLE, transactions);
    Portfolio actual = portfolioService.getPortfolio();
    assertNull(actual);

    portfolioService.createAndSet(PortfolioFormat.INFLEXIBLE, transactions);
    actual = portfolioService.getPortfolio();
    assertNotNull(actual);
  }

  @Test
  public void load() {

  }

  @Test
  public void modify_flexible() throws Exception {
    portfolioService.createAndSet(PortfolioFormat.FLEXIBLE, transactions);
    portfolioService.addTransactions(transactions2);

    List<Transaction> actual = portfolioService.getPortfolio().getTransaction();
    assertEquals(5, actual.size());
  }

  @Test
  public void modify_flexible_error() throws Exception {
    portfolioService.createAndSet(PortfolioFormat.FLEXIBLE, transactions);
    transactions2.add(
        new Transaction(TransactionType.SELL, "AAPL", 10000, LocalDate.parse("2022-10-12"),123));
    try {
      portfolioService.addTransactions(transactions2);
    } catch (Exception e) {
      assertEquals("Transaction invalid.", e.getMessage());
    }
  }

  @Test
  public void modify_inflexible() throws Exception {
    portfolioService.createAndSet(PortfolioFormat.INFLEXIBLE, transactions);
    try {
      portfolioService.addTransactions(transactions2);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Portfolio is not modifiable.", e.getMessage());
    }
  }

  @Test
  public void getValue() throws Exception {
    portfolioService.createAndSet(PortfolioFormat.FLEXIBLE, transactions);
    PortfolioWithValue portfolioWithValue = portfolioService.getValue(
        LocalDate.parse("2022-10-10"));

    assertEquals(8400.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getStocks();
    assertEquals(4400, list.get(0).getValue(), EPSILON);
    assertEquals(4000, list.get(1).getValue(), EPSILON);
  }

  @Test
  public void getValues() throws Exception {
    portfolioService.createAndSet(PortfolioFormat.FLEXIBLE, transactions);
    Map<String, PortfolioWithValue> portfolioWithValue = portfolioService.getValues(
        LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-11"));

    assertEquals(8400.0, portfolioWithValue.get("2022-10-10").getTotalValue(), EPSILON);
    assertEquals(9900.0, portfolioWithValue.get("2022-10-11").getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.get("2022-10-10").getStocks();
    assertEquals(4400, list.get(0).getValue(), EPSILON);
    assertEquals(4000, list.get(1).getValue(), EPSILON);

    list = portfolioWithValue.get("2022-10-11").getStocks();
    assertEquals(900, list.get(0).getValue(), EPSILON);
    assertEquals(9000, list.get(1).getValue(), EPSILON);
  }

}
