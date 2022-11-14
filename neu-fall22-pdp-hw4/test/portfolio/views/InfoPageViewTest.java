package portfolio.views;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.TransactionConverter;
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
  Map<String, Integer> stocks;
  Map<String, StockPrice> prices;
  LocalDate date;
  InflexiblePortfolio portfolio;
  PortfolioWithValue portfolioWithValue;


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
  }

  @Test
  public void testRender_First() {
    setUp();
    View view = new InfoPageView(printStream, null, null, null);
    view.render();
    assertEquals("*********************"
        + "************************************\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "*********************************************************\r\n"
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
        + "*********************************************************\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "*********************************************************\r\n"
        + "Please enter the date that you want to determine. "
        + "The format is year-month-day, ex: 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Show() {
    setUp();

    View view = new InfoPageView(printStream, null, portfolioWithValue,
        null);
    view.render();
    assertEquals("***********************************************"
        + "**********\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "*********************************************************\r\n"
        + "If stock price not found, the value with be N/A and will not be "
        + "include in the total value.\r\n"
        + "\r\n"
        + "Portfolio value as of: 2022-10-10\r\n"
        + "+---------+---------------+--------------------+\r\n"
        + "|    Stock|  No. of shares|       Current value|\r\n"
        + "+---------+---------------+--------------------+\r\n"
        + "|      AAA|            100|               400.0|\r\n"
        + "|     AAPL|           1000|             44000.0|\r\n"
        + "+---------+---------------+--------------------+\r\n"
        + "Total value: 44400.0\r\n"
        + "\r\n"
        + "Please enter the date again if you want to determine value "
        + "for another date. "
        + "The format is year-month-day, ex: 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

}
