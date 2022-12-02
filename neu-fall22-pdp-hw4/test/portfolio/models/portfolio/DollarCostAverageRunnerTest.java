package portfolio.models.portfolio;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.StockApiMock;
import portfolio.models.portfolio.impl.DollarCostAverageSchedule;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.impl.DollarCostAverageRunner;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;

/**
 * Test for DollarCostAverageRunner.
 */
public class DollarCostAverageRunnerTest {

  private ScheduleRunner runner;
  private final double EPSILON = 0.000000001;

  @Before
  public void setUp() throws Exception {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    runner = new DollarCostAverageRunner(stockQueryService);
  }

  @Test
  public void run() {
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-10-05"),
        LocalDate.parse("2022-10-10"),
        5,
        null,
        buyingList
    );

    List<Transaction> transactions = runner.run(LocalDate.parse("2022-10-20"), schedule);
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction(TransactionType.BUY, "AAPL", 110.55555555555556,
        LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 9.95, LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 248.75, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(new Transaction(TransactionType.BUY, "AAA", 22.613636363636363,
        LocalDate.parse("2022-10-10"), 5.0));

    assertEquals(4, transactions.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), transactions.get(i).getType());
      assertEquals(expected.get(i).getDate(), transactions.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), transactions.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), transactions.get(i).getCommissionFee());
    }
  }

  @Test
  public void run_noEndDate() {
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-10-05"),
        null,
        5,
        null,
        buyingList
    );

    List<Transaction> transactions = runner.run(LocalDate.parse("2022-10-11"), schedule);
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction(TransactionType.BUY, "AAPL", 110.55555555555556,
        LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 9.95, LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 248.75, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(new Transaction(TransactionType.BUY, "AAA", 22.613636363636363,
        LocalDate.parse("2022-10-10"), 5.0));

    assertEquals(4, transactions.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), transactions.get(i).getType());
      assertEquals(expected.get(i).getDate(), transactions.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), transactions.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), transactions.get(i).getCommissionFee());
    }
  }

  @Test
  public void run_useNextDayPrice() {
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-10-04"),
        LocalDate.parse("2022-10-10"),
        5,
        null,
        buyingList
    );

    List<Transaction> transactions = runner.run(LocalDate.parse("2022-10-11"), schedule);
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction(TransactionType.BUY, "AAPL", 110.55555555555556,
        LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 9.95, LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 248.75, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(new Transaction(TransactionType.BUY, "AAA", 22.613636363636363,
        LocalDate.parse("2022-10-10"), 5.0));

    assertEquals(4, transactions.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), transactions.get(i).getType());
      assertEquals(expected.get(i).getDate(), transactions.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), transactions.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), transactions.get(i).getCommissionFee());
    }
  }

  @Test
  public void run_skipNoPriceDate() {
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        null,
        buyingList
    );

    List<Transaction> transactions = runner.run(LocalDate.parse("2022-10-11"), schedule);
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction(TransactionType.BUY, "AAPL", 110.55555555555556,
        LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 9.95, LocalDate.parse("2022-10-05"), 5.0));
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 248.75, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(new Transaction(TransactionType.BUY, "AAA", 22.613636363636363,
        LocalDate.parse("2022-10-10"), 5.0));

    assertEquals(4, transactions.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), transactions.get(i).getType());
      assertEquals(expected.get(i).getDate(), transactions.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), transactions.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), transactions.get(i).getCommissionFee());
    }
  }

  @Test
  public void run_runAfterLastRundate() {
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList
    );

    List<Transaction> transactions = runner.run(LocalDate.parse("2022-10-11"), schedule);
    List<Transaction> expected = new ArrayList<>();
    expected.add(
        new Transaction(TransactionType.BUY, "AAPL", 248.75, LocalDate.parse("2022-10-10"), 5.0));
    expected.add(new Transaction(TransactionType.BUY, "AAA", 22.613636363636363,
        LocalDate.parse("2022-10-10"), 5.0));

    assertEquals(2, transactions.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getSymbol(), transactions.get(i).getSymbol());
      assertEquals(expected.get(i).getType(), transactions.get(i).getType());
      assertEquals(expected.get(i).getDate(), transactions.get(i).getDate());
      assertEquals(expected.get(i).getAmount(), transactions.get(i).getAmount(), 0.001);
      assertEquals(expected.get(i).getCommissionFee(), transactions.get(i).getCommissionFee());
    }
  }


  @Test
  public void run_apiThrowError() {
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(true));
    runner = new DollarCostAverageRunner(stockQueryService);
    List<Transaction> buyingList = new ArrayList<>();
    buyingList.add(new Transaction("AAPL", 10));
    buyingList.add(new Transaction("AAA", 10));
    BuySchedule schedule = new DollarCostAverageSchedule(
        "name",
        2000,
        5,
        LocalDate.parse("2022-09-05"),
        LocalDate.parse("2022-10-10"),
        5,
        LocalDate.parse("2022-10-05"),
        buyingList
    );

    List<Transaction> transactions = runner.run(LocalDate.parse("2022-10-11"), schedule);
    assertEquals(0, transactions.size());
  }
}
