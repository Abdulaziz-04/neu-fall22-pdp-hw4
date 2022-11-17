package portfolio.views;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.TransactionConverter;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.impl.FlexiblePortfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;
import portfolio.views.impl.InfoPageView;

/**
 * This is a test class to test InfoPageView class.
 */
public class InfoPageViewTest {

  private final ByteArrayOutputStream outputStreamCaptor =
      new ByteArrayOutputStream();
  private PrintStream printStream;
  private Map<String, Integer> stocks;
  private Map<String, StockPrice> prices;
  private LocalDate date;
  private LocalDate date2;
  private InflexiblePortfolio portfolio;
  private FlexiblePortfolio portfolio2;
  private PortfolioWithValue portfolioWithValue;

  private PortfolioWithValue portfolioWithValue2;
  private PortfolioWithValue portfolioWithValue3;

  private final List<Transaction> transactions = new ArrayList<>();


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
    stocks = new HashMap<>();
    prices = new HashMap<>();
    stocks.put("AAA", 100);
    stocks.put("AAPL", 1000);
    prices.put("AAA",
        new StockPrice(1, 2, 3, 4, 5));
    prices.put("AAPL",
        new StockPrice(11, 22, 33, 44, 55));
    portfolio = new InflexiblePortfolio("name", TransactionConverter.convert(stocks));
    date = LocalDate.parse("2022-10-10");
    portfolioWithValue = portfolio.getPortfolioWithValue(date, prices);
    transactions.add(new Transaction(TransactionType.BUY, "AAA", 110,
            LocalDate.parse("2022-10-10"), 12));
    transactions.add(new Transaction(TransactionType.SELL, "AAA", 10,
            LocalDate.parse("2022-10-10"), 34));
    transactions.add(new Transaction(TransactionType.BUY, "AAPL", 1000,
            LocalDate.parse("2022-10-11"), 56));

    portfolio2 = new FlexiblePortfolio("name2", transactions);
    date2 = LocalDate.parse("2022-10-12");
    portfolioWithValue2 = portfolio2.getPortfolioWithValue(date, prices);
    portfolioWithValue3 = portfolio2.getPortfolioWithValue(date2, prices);
  }

  @Test
  public void testRender_First() {
    setUp();
    View view = new InfoPageView(printStream, null, null, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
            +  "!!! If you enter back, you will go to the load page.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            +  "----------------------------------------------------------\r\n"
        + "Please enter the date that you want to determine. The format is"
        + " year-month-day, ex: 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Error() {
    setUp();
    View view = new InfoPageView(printStream, null, null,
        "Error! Please input the correct date.");
    view.render();
    assertEquals("---------------------ERROR----------"
        + "----------------------\r\n"
        + "! Error message: Error! Please input the correct date.\r\n"
        + "----------------------------------------------------------\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            +  "!!! If you enter back, you will go to the load page.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            +  "----------------------------------------------------------\r\n"
        + "Please enter the date that you want to determine. "
        + "The format is year-month-day, ex: 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_ShowInFlexible() {
    setUp();

    View view = new InfoPageView(printStream, null, portfolioWithValue,
        null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
            +  "!!! If you enter back, you will go to the load page.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            +  "----------------------------------------------------------\r\n"
        + "If stock price not found, the value with be N/A and will not be "
        + "include in the total value.\r\n"
        + "\r\n"
        + "Portfolio composition and value as of: 2022-10-10\r\n"
            +  "+---------+---------------+--------------------+\r\n"
            +  "|    Stock|  No. of shares|       Current value|\r\n"
            + "+---------+---------------+--------------------+\r\n"
            + "|      AAA|            100|              $400.0|\r\n"
            + "|     AAPL|           1000|            $44000.0|\r\n"
            + "+---------+---------------+--------------------+\r\n"
            + "Total value: $44400.0\r\n"
            + "Cost of basis is not available for this portfolio.\r\n"
        + "\r\n"
        + "Please enter the date again if you want to determine value "
        + "for another date. "
        + "The format is year-month-day, ex: 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_ShowFlexible1() {
    setUp();

    View view = new InfoPageView(printStream, 1000.0, portfolioWithValue2,
            null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
            +  "!!! If you enter back, you will go to the load page.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            +  "----------------------------------------------------------\r\n"
            + "If stock price not found, the value with be N/A and will not be "
            + "include in the total value.\r\n"
            + "\r\n"
            + "Portfolio composition and value as of: 2022-10-10\r\n"
            +  "+---------+---------------+--------------------+\r\n"
            +  "|    Stock|  No. of shares|       Current value|\r\n"
            + "+---------+---------------+--------------------+\r\n"
            + "|      AAA|            200|              $800.0|\r\n"
            + "+---------+---------------+--------------------+\r\n"
            + "Total value: $800.0\r\n"
            + "Cost of basis as of 2022-10-10: $1000.0\r\n"
            + "\r\n"
            + "Please enter the date again if you want to determine value "
            + "for another date. "
            + "The format is year-month-day, ex: 2022-10-11\r\n"
            + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_ShowFlexible2() {
    setUp();

    View view = new InfoPageView(printStream, 1000.0, portfolioWithValue3,
            null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
            +  "!!! If you enter back, you will go to the load page.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            +  "----------------------------------------------------------\r\n"
            + "If stock price not found, the value with be N/A and will not be "
            + "include in the total value.\r\n"
            + "\r\n"
            + "Portfolio composition and value as of: 2022-10-12\r\n"
            +  "+---------+---------------+--------------------+\r\n"
            +  "|    Stock|  No. of shares|       Current value|\r\n"
            + "+---------+---------------+--------------------+\r\n"
            + "|      AAA|            200|              $800.0|\r\n"
            + "|     AAPL|           2000|            $88000.0|\r\n"
            + "+---------+---------------+--------------------+\r\n"
            + "Total value: $88800.0\r\n"
            + "Cost of basis as of 2022-10-12: $1000.0\r\n"
            + "\r\n"
            + "Please enter the date again if you want to determine value "
            + "for another date. "
            + "The format is year-month-day, ex: 2022-10-11\r\n"
            + "input > ", outputStreamCaptor.toString());
  }

}
