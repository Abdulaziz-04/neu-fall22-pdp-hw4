package portfolio.models.portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import portfolio.models.entities.PortfolioPerformance;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;

/**
 * This is a test class to test PortfolioServiceImpl class.
 */
public class PortfolioModelImplTest {

  private PortfolioModel portfolioModel;
  private final Map<String, Integer> map = new HashMap<>();
  private final List<Transaction> transactions = new ArrayList<>();
  private final List<Transaction> transactions2 = new ArrayList<>();
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioModel = new PortfolioModelImpl(stockQueryService, new PortfolioTextParser());

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

    portfolioModel.create("name", PortfolioFormat.INFLEXIBLE, transactions);
  }

  @Test
  public void getPortfolio() throws Exception {
    Portfolio actual = portfolioModel.getPortfolio();
    assertNotNull(actual);
  }

  @Test
  public void create_flexible() throws Exception {
    Portfolio actualPortfolio = portfolioModel.create("name", PortfolioFormat.FLEXIBLE,
        transactions);

    List<Transaction> actual = actualPortfolio.getTransactions();

    assertEquals(PortfolioFormat.FLEXIBLE, actualPortfolio.getFormat());
    assertEquals(transactions.size(), actual.size());
    for (int i = 0; i < transactions.size(); i++) {
      assertEquals(actual.get(i).getType(), transactions.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), transactions.get(i).getAmount());
      assertEquals(actual.get(i).getDate(), transactions.get(i).getDate());
    }
    Map<String, Integer> actualMap = actualPortfolio.getComposition();
    assertEquals(2, actualMap.size());
  }

  @Test
  public void create_inflexible() throws Exception {
    Portfolio actualPortfolio = portfolioModel.create("name", PortfolioFormat.INFLEXIBLE,
        transactions);

    List<Transaction> actual = actualPortfolio.getTransactions();

    assertEquals(PortfolioFormat.INFLEXIBLE, actualPortfolio.getFormat());
    assertEquals(transactions.size(), actual.size());
    for (int i = 0; i < transactions.size(); i++) {
      assertEquals(actual.get(i).getType(), transactions.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), transactions.get(i).getAmount());
      assertEquals(actual.get(i).getDate(), transactions.get(i).getDate());
    }
    Map<String, Integer> actualMap = actualPortfolio.getComposition();
    assertEquals(2, actualMap.size());
  }

  @Test
  public void create_transactionConflict() {
    transactions.add(
        new Transaction(TransactionType.SELL, "AAPL", 10000, LocalDate.parse("2022-10-11"), 123));
    try {
      portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    } catch (Exception e) {
      assertEquals("There is a conflict in the input transaction.", e.getMessage());
    }
  }

  @Test
  public void load_inflexible() throws Exception {
    String str = "FORMAT=INFLEXIBLE\r\nAAA,100\r\nAAPL,100\r\n";
    portfolioModel.load("name", str);
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolioModel.getPortfolio().getFormat());
  }

  @Test
  public void load_inflexible_oldFormat() throws Exception {
    String str = "AAA,100\r\nAAPL,100\r\n";
    portfolioModel.load("name", str);
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolioModel.getPortfolio().getFormat());
  }

  @Test
  public void load_flexible() throws Exception {
    String str = "FORMAT=FLEXIBLE\r\nAAA,100\r\nAAPL,100\r\n";
    portfolioModel.load("name", str);
    assertEquals(PortfolioFormat.FLEXIBLE, portfolioModel.getPortfolio().getFormat());
  }

  @Test
  public void load_fail_wrongFormat() {
    String str = "FORMAT=AAA\r\na,100\r\nb,100\r\n";
    try {
      portfolioModel.load("name", str);
    } catch (Exception e) {
      assertEquals("Format is not supported.", e.getMessage());
    }
  }

  @Test
  public void load_fail_noSymbol() {
    String str = "FORMAT=FLEXIBLE\r\na,100\r\nb,100\r\n";
    try {
      portfolioModel.load("name", str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Symbol [a] not found.", e.getMessage());
    }
  }

  @Test
  public void checkTransaction() throws Exception {
    assertTrue(portfolioModel.checkTransaction(LocalDate.parse("2022-10-11"), "AAA"));
  }

  @Test
  public void checkTransaction_noPriceOnThatDate() throws Exception {
    try {
      portfolioModel.checkTransaction(LocalDate.parse("2022-10-12"), "AAA");
      fail();
    } catch (Exception e) {
      assertEquals("Stock [AAA] is not available at date 2022-10-12.", e.getMessage());
    }
  }

  @Test
  public void checkTransaction_symbolInvalid() {
    try {
      portfolioModel.checkTransaction(LocalDate.parse("2022-10-11"), "AAAAA");
      fail();
    } catch (Exception e) {
      assertEquals("[AAAAA] is not a valid stock symbol.", e.getMessage());
    }
  }

  @Test
  public void checkTransaction_apiUnavailable() {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    portfolioModel = new PortfolioModelImpl(stockQueryService, new PortfolioTextParser());
    try {
      portfolioModel.checkTransaction(LocalDate.parse("2022-10-11"), "AAAAA");
      fail();
    } catch (Exception e) {
      assertEquals("Something wrong.", e.getMessage());
    }
  }

  @Test
  public void checkTransactions_noPriceOnThatDate() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(
        new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 10));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-12"), 20));
    try {
      portfolioModel.checkTransactions(transactions);
      fail();
    } catch (Exception e) {
      assertEquals("Stock [AAA] is not available at date 2022-10-12.", e.getMessage());
    }
  }

  @Test
  public void checkTransactions_symbolInvalid() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(
        new Transaction(TransactionType.BUY, "AAAAA", 110, LocalDate.parse("2022-10-10"), 10));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-12"), 20));
    try {
      portfolioModel.checkTransactions(transactions);
      fail();
    } catch (Exception e) {
      assertEquals("Symbol [AAAAA] not found.", e.getMessage());
    }
  }

  @Test
  public void checkTransactions_apiUnavailable() {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    portfolioModel = new PortfolioModelImpl(stockQueryService, new PortfolioTextParser());
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(
        new Transaction(TransactionType.BUY, "AAAAA", 110, LocalDate.parse("2022-10-10"), 10));
    transactions.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-12"), 20));
    try {
      portfolioModel.checkTransactions(transactions);
      fail();
    } catch (Exception e) {
      assertEquals("Something wrong.", e.getMessage());
    }
  }

  @Test
  public void addTransactions_flexible() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    portfolioModel.addTransactions(transactions2);

    List<Transaction> actual = portfolioModel.getPortfolio().getTransactions();
    assertEquals(5, actual.size());
  }

  @Test
  public void addTransactions_flexible_error() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    transactions2.add(
        new Transaction(TransactionType.SELL, "AAPL", 10000, LocalDate.parse("2022-10-12"), 123));
    try {
      portfolioModel.addTransactions(transactions2);
    } catch (Exception e) {
      assertEquals("There is a conflict in the input transaction.", e.getMessage());
    }
  }

  @Test
  public void addTransactions_inflexible() throws Exception {
    portfolioModel.create("name", PortfolioFormat.INFLEXIBLE, transactions);
    try {
      portfolioModel.addTransactions(transactions2);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Portfolio is not modifiable.", e.getMessage());
    }
  }

  @Test
  public void getValue() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioWithValue portfolioWithValue = portfolioModel.getValue(
        LocalDate.parse("2022-10-10"));

    assertEquals(400.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getValues();
    assertEquals(1, list.size());
    assertEquals(400, list.get(0).getValue(), EPSILON);
  }

  @Test
  public void getValue_noStock() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioWithValue portfolioWithValue = portfolioModel.getValue(
        LocalDate.parse("2022-10-09"));

    assertEquals(0.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getValues();
    assertEquals(0, list.size());
  }

  @Test
  public void getValue_apiFail() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioWithValue portfolioWithValue = portfolioModel.getValue(
        LocalDate.parse("2022-10-09"));

    assertEquals(0.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getValues();
    assertEquals(0, list.size());
  }

  @Test
  public void getValues() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    Map<LocalDate, Double> portfolioWithValue = portfolioModel.getValues(
        LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-11"));

    assertEquals(400.0, portfolioWithValue.get(LocalDate.parse("2022-10-10")), EPSILON);
    assertEquals(9900.0, portfolioWithValue.get(LocalDate.parse("2022-10-11")), EPSILON);
  }

  @Test
  public void getPerformance_days() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2022-10-15"));
    assertEquals(8, performance.getPerformance().size());
    assertEquals(0, performance.getPerformance().get("2022-10-08: "), EPSILON);
    assertEquals(1, performance.getPerformance().get("2022-10-10: "), EPSILON);
    assertEquals(46, performance.getPerformance().get("2022-10-11: "), EPSILON);
    assertEquals(
        "one asterisk is $ 211.11111111111111 more than a base amount of $187.88888888888889",
        performance.getScale());
  }

  @Test
  public void getPerformance_lengthInsufficient() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);

    try {
      portfolioModel.getPerformance(LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-11"));
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Date range is too short.", e.getMessage());
    }
  }

  @Test
  public void getPerformance_week() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2022-12-15"));
    assertEquals(11, performance.getPerformance().size());
    assertEquals(0, performance.getPerformance().get("Week: 2022-10-08 to 2022-10-09: "), EPSILON);
    assertEquals(1, performance.getPerformance().get("Week: 2022-10-10 to 2022-10-16: "), EPSILON);
    assertEquals("one asterisk is $9900.0", performance.getScale());
  }

  @Test
  public void getPerformance_month() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2023-12-15"));
    assertEquals(15, performance.getPerformance().size());
    assertEquals(1, performance.getPerformance().get("2022-10: "), EPSILON);
    assertEquals("one asterisk is $9900.0", performance.getScale());
  }

  @Test
  public void getPerformance_quarter() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2025-12-15"));
    assertEquals(13, performance.getPerformance().size());
    assertEquals(1, performance.getPerformance().get("2022-Quarter 5: "), EPSILON);
    assertEquals("one asterisk is $9900.0", performance.getScale());
  }

  @Test
  public void getPerformance_years() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2032-12-15"));
    assertEquals(11, performance.getPerformance().size());
    assertEquals(1, performance.getPerformance().get("2022: "), EPSILON);
    assertEquals("one asterisk is $9900.0", performance.getScale());
  }

  @Test
  public void getPerformance_toBeforeFrom() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    try {
      portfolioModel.getPerformance(LocalDate.parse("2022-10-08"), LocalDate.parse("2021-12-15"));
      fail("should fail");
    } catch (Exception e) {
      assertEquals("endDate cannot be before than startDate", e.getMessage());
    }
  }

}
