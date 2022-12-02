package portfolio.models.portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
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
import portfolio.models.portfolio.impl.DollarCostAverageRunner;
import portfolio.models.portfolio.impl.PortfolioModelImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;

/**
 * This is a test class to test PortfolioServiceImpl class.
 */
public class PortfolioModelImplTest {

  private PortfolioModel portfolioModel;
  private final List<Transaction> transactions = new ArrayList<>();
  private final List<Transaction> transactions2 = new ArrayList<>();
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    ScheduleRunner runner = new DollarCostAverageRunner(stockQueryService);
    portfolioModel = new PortfolioModelImpl(stockQueryService, new PortfolioTextParser(), runner);

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
      assertEquals(actual.get(i).getAmount(), transactions.get(i).getAmount(), EPSILON);
      assertEquals(actual.get(i).getDate(), transactions.get(i).getDate());
    }
    Map<String, Double> actualMap = actualPortfolio.getComposition();
    assertEquals(2, actualMap.size());
  }

  @Test
  public void create_flexible_noTransaction() throws Exception {
    Portfolio actualPortfolio = portfolioModel.create("name", PortfolioFormat.FLEXIBLE,
        new ArrayList<>());

    List<Transaction> actual = actualPortfolio.getTransactions();

    assertEquals(PortfolioFormat.FLEXIBLE, actualPortfolio.getFormat());
    assertEquals(0, actual.size());
    Map<String, Double> actualMap = actualPortfolio.getComposition();
    assertEquals(0, actualMap.size());
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
      assertEquals(actual.get(i).getAmount(), transactions.get(i).getAmount(), EPSILON);
      assertEquals(actual.get(i).getDate(), transactions.get(i).getDate());
    }
    Map<String, Double> actualMap = actualPortfolio.getComposition();
    assertEquals(2, actualMap.size(), EPSILON);
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
  public void load_v3_flexible() throws Exception {
    String str = "[INFO]\n"
        + "FORMAT=FLEXIBLE\n"
        + "VERSION=3\n"
        + "\n"
        + "[TRANSACTION]\n"
        + "2022-10-10,BUY,AAA,100,12.3\n"
        + "2022-10-11,SELL,AAA,10,4\n"
        + "2022-10-11,BUY,AAPL,123,5\n\n";
    portfolioModel.load("name", str);
    assertEquals(PortfolioFormat.FLEXIBLE, portfolioModel.getPortfolio().getFormat());
  }

  @Test
  public void load_v3_inflexible() throws Exception {
    String str = "[INFO]\r\nFORMAT=INFLEXIBLE\r\nVERSION=3\r\n\r\n"
        + "[TRANSACTION]\r\nAAA,100\r\nAAPL,123\r\n\r\n";
    portfolioModel.load("name", str);
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolioModel.getPortfolio().getFormat());
  }

  @Test
  public void load_v3_flexible_withSchedule() throws Exception {
    String str = "[INFO]\r\n"
        + "FORMAT=FLEXIBLE\r\n"
        + "VERSION=3\r\n"
        + "\r\n"
        + "[SCHEDULE]\r\n"
        + "NAME=DOLLAR_COST_AVG\r\n"
        + "AMOUNT=2000\r\n"
        + "SCHEDULE=5,2022-10-05,2022-10-10\r\n"
        + "TRANSACTION_FEE=10\r\n"
        + "LAST_RUN_DATE=2022-09-30\r\n"
        + "AAPL,40\r\n"
        + "AAA,20\r\n"
        + "\r\n"
        + "[TRANSACTION]\r\n"
        + "2022-10-10,BUY,AAA,100,12.3\r\n"
        + "2022-10-11,SELL,AAA,10,4\r\n"
        + "2022-10-11,BUY,AAPL,123,5\r\n\r\n";

    portfolioModel.load("name", str);

    List<Transaction> expected = new ArrayList<>();
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 6.6000, LocalDate.parse("2022-10-05"), 10.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 146.666666, LocalDate.parse("2022-10-05"),
            10.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 15.0, LocalDate.parse("2022-10-10"), 10.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 100.0, LocalDate.parse("2022-10-10"), 12.3));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 330.000, LocalDate.parse("2022-10-10"), 10.0));
    expected.add(
        new Transaction(TransactionType.SELL, "AAA", 10.0, LocalDate.parse("2022-10-11"), 4));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 123.0, LocalDate.parse("2022-10-11"), 5));

    List<Transaction> actual = portfolioModel.getPortfolio().getTransactions();
    assertEquals(PortfolioFormat.FLEXIBLE, portfolioModel.getPortfolio().getFormat());
    assertEquals(7, actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), actual.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), actual.get(i).getType());
      assertEquals(expected.get(i).getDate(), actual.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), actual.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), actual.get(i).getCommissionFee());
    }
    List<BuySchedule> schedules = portfolioModel.getPortfolio().getBuySchedules();
    assertEquals(1, schedules.size());
    assertEquals(LocalDate.now(), schedules.get(0).getLastRunDate());
  }

  @Test
  public void load_v3_flexible_withMultipleSchedule() throws Exception {
    String str = "FORMAT=INFLEXIBLE\r\nAAA,100\r\nAAPL,100\r\n";
    portfolioModel.load("name", str);
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolioModel.getPortfolio().getFormat());
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
    portfolioModel = new PortfolioModelImpl(stockQueryService, new PortfolioTextParser(), null);
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
      assertEquals("[AAAAA] is not a valid stock symbol.", e.getMessage());
    }
  }

  @Test
  public void checkTransactions_apiUnavailable() {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    portfolioModel = new PortfolioModelImpl(stockQueryService, new PortfolioTextParser(), null);
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

    assertEquals(4400.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getValues();
    assertEquals(1, list.size());
    assertEquals(4400, list.get(0).getValue(), EPSILON);
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

    assertEquals(4400.0, portfolioWithValue.get(LocalDate.parse("2022-10-10")), EPSILON);
    assertEquals(18900.0, portfolioWithValue.get(LocalDate.parse("2022-10-11")), EPSILON);
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
        "one asterisk is $322.22222222222223 more than a base amount of $4076.777777777778",
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
    assertEquals("one asterisk is $18900.0", performance.getScale());
  }

  @Test
  public void getPerformance_month() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2023-12-15"));
    assertEquals(15, performance.getPerformance().size());
    assertEquals(1, performance.getPerformance().get("2022-10: "), EPSILON);
    assertEquals("one asterisk is $18900.0", performance.getScale());
  }

  @Test
  public void getPerformance_quarter() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2025-12-15"));
    assertEquals(13, performance.getPerformance().size());
    assertEquals(1, performance.getPerformance().get("2022-Quarter 5: "), EPSILON);
    assertEquals("one asterisk is $18900.0", performance.getScale());
  }

  @Test
  public void getPerformance_years() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    PortfolioPerformance performance = portfolioModel.getPerformance(LocalDate.parse("2022-10-08"),
        LocalDate.parse("2032-12-15"));
    assertEquals(11, performance.getPerformance().size());
    assertEquals(1, performance.getPerformance().get("2022: "), EPSILON);
    assertEquals("one asterisk is $18900.0", performance.getScale());
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

  @Test
  public void addSchedule() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
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

    List<Transaction> expected = new ArrayList<>();
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 22.61363, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 110.0, LocalDate.parse("2022-10-10"), 10.0));
    expected.add(
        new Transaction(TransactionType.SELL, "AAA", 10.0, LocalDate.parse("2022-10-10"), 20.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 248.75, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 1000.0, LocalDate.parse("2022-10-11"), 30.0));

    List<Transaction> actual = portfolioModel.getPortfolio().getTransactions();
    assertEquals(PortfolioFormat.FLEXIBLE, portfolioModel.getPortfolio().getFormat());
    assertEquals(5, actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), actual.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), actual.get(i).getType());
      assertEquals(expected.get(i).getDate(), actual.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), actual.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), actual.get(i).getCommissionFee());
    }
    List<BuySchedule> schedules = portfolioModel.getPortfolio().getBuySchedules();
    assertEquals(1, schedules.size());
    assertEquals(LocalDate.now(), schedules.get(0).getLastRunDate());
  }

  @Test
  public void addSchedule_duplicateName() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
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

    try {
      portfolioModel.addSchedule(
          "name",
          2000,
          5,
          LocalDate.parse("2022-09-05"),
          LocalDate.parse("2022-10-10"),
          5,
          LocalDate.parse("2022-10-05"),
          buyingList);
      fail();
    } catch (Exception e) {
      assertEquals("Duplicate schedule name in the same portfolio.", e.getMessage());
    }
  }

  @Test
  public void addSchedule_multipleSchedules() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    List<Transaction> buyingList1 = new ArrayList<>();
    buyingList1.add(new Transaction("AAPL", 10));
    List<Transaction> buyingList2 = new ArrayList<>();
    buyingList2.add(new Transaction("AAA", 10));
    portfolioModel.addSchedule(
        "s1",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList1);
    assertEquals(4, portfolioModel.getPortfolio().getTransactions().size());
    portfolioModel.addSchedule(
        "s2",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList2);
    assertEquals(5, portfolioModel.getPortfolio().getTransactions().size());
    assertEquals(2, portfolioModel.getPortfolio().getBuySchedules().size());
  }

  @Test
  public void addSchedule_removeSchedule() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    List<Transaction> buyingList1 = new ArrayList<>();
    buyingList1.add(new Transaction("AAPL", 10));
    List<Transaction> buyingList2 = new ArrayList<>();
    buyingList2.add(new Transaction("AAA", 10));
    portfolioModel.addSchedule(
        "s1",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList1);
    portfolioModel.addSchedule(
        "sss",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList1);
    List<BuySchedule> schedules = portfolioModel.getPortfolio().getBuySchedules();
    assertEquals("AAPL", schedules.get(0).getBuyingList().get(0).getSymbol());
    assertEquals(2, portfolioModel.getPortfolio().getBuySchedules().size());

    portfolioModel.removeSchedule("s1");
    schedules = portfolioModel.getPortfolio().getBuySchedules();
    assertEquals("sss", schedules.get(0).getName());
    assertEquals(1, portfolioModel.getPortfolio().getBuySchedules().size());
  }

  @Test
  public void addSchedule_noScheduleToRemove() throws Exception {
    portfolioModel.create("name", PortfolioFormat.FLEXIBLE, transactions);
    List<Transaction> buyingList1 = new ArrayList<>();
    buyingList1.add(new Transaction("AAPL", 10));
    List<Transaction> buyingList2 = new ArrayList<>();
    buyingList2.add(new Transaction("AAA", 10));
    portfolioModel.addSchedule(
        "s1",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList1);
    portfolioModel.addSchedule(
        "sss",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList1);

    portfolioModel.removeSchedule("s3");
    assertEquals(2, portfolioModel.getPortfolio().getBuySchedules().size());
  }
}
