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
import portfolio.views.impl.LoadPageView;


import portfolio.models.portfolio.impl.InflexiblePortfolio;


/**
 * This is a test class to test LoadPageView class.
 */
public class LoadPageViewTest {

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private PrintStream printStream;
  private Map<String, Integer> stocks;

  private final List<Transaction> transactions = new ArrayList<>();


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
    stocks = new HashMap<>();
  }

  @Test
  public void testRender_First() {
    setUp();
    View view = new LoadPageView(printStream, null, false, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the name of the portfolio that you want to load. "
        + "The name cannot be back.\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Error1() {
    setUp();
    View view = new LoadPageView(printStream, null, false,
        "Cannot read portfolio. It may have a wrong format.");
    view.render();
    assertEquals("---------------------ERROR-----------------"
        + "---------------\r\n"
        + "! Error message: Cannot read portfolio. It may have a wrong format.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the name of the portfolio that you want to load. "
        + "The name cannot be back.\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Error2() {
    setUp();
    View view = new LoadPageView(printStream, null, false,
        "file not found.");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: file not found.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the name of the portfolio that you want to load. "
        + "The name cannot be back.\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_InFlexiblePortfolioGet() {
    setUp();
    stocks.put("AAA", 100);
    stocks.put("AA", 1000);
    InflexiblePortfolio portfolio = new InflexiblePortfolio("name",
        TransactionConverter.convert(stocks));
    View view = new LoadPageView(printStream, portfolio, false,
        null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Portfolio: name\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "             |        N/A|   N/A|       AA|        1000.00|            N/A|\r\n"
        + "             |        N/A|   N/A|      AAA|         100.00|            N/A|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "Menu:\r\n"
        + "1. View composition, value, cost of basis for specific date\r\n"
        + "2. View performance over time\r\n"
        + "--Please enter the number in above\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_FlexiblePortfolioGet() {
    setUp();
    stocks.put("AAA", 100);
    stocks.put("AA", 1000);
    transactions.add(new Transaction(TransactionType.BUY, "AAA", 110,
        LocalDate.parse("2022-10-10"), 12));
    transactions.add(new Transaction(TransactionType.SELL, "AAA", 10,
        LocalDate.parse("2022-10-10"), 34));
    transactions.add(new Transaction(TransactionType.BUY, "AAPL", 1000,
        LocalDate.parse("2022-10-11"), 56));

    FlexiblePortfolio portfolio2 = new FlexiblePortfolio("name2", transactions);
    View view = new LoadPageView(printStream, portfolio2, true,
        null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Portfolio: name2\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "             | 2022-10-10|   BUY|      AAA|         110.00|          $12.0|\r\n"
        + "             | 2022-10-10|  SELL|      AAA|          10.00|          $34.0|\r\n"
        + "             | 2022-10-11|   BUY|     AAPL|        1000.00|          $56.0|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "Menu:\r\n"
        + "1. View composition, value, cost of basis for specific date\r\n"
        + "2. View performance over time\r\n"
        + "3. Modify portfolio (Add transaction to portfolio)\r\n"
        + "--Please enter the number in above\r\n"

        + "input > ", outputStreamCaptor.toString());

  }

}
