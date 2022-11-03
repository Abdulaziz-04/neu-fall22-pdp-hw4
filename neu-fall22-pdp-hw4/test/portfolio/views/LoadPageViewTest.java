package portfolio.views;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import portfolio.views.impl.LoadPageView;


import portfolio.entities.Portfolio;


/**
 * This is a test class to test LoadPageView class.
 */
public class LoadPageViewTest {

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private PrintStream printStream;
  private Map<String, Integer> stocks;


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
    stocks = new HashMap<>();
  }

  @Test
  public void testRender_First() {
    setUp();
    View view = new LoadPageView(printStream, null, null);
    view.render();
    assertEquals("*************************************************"
        + "********\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "*********************************************************\r\n"
        + "--Please enter the name of the portfolio that you want to examine. "
        + "The name cannot be end,yes,no,back.--\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Error1() {
    setUp();
    View view = new LoadPageView(printStream, null,
        "Cannot read portfolio. It may have a wrong format.");
    view.render();
    assertEquals("---------------------ERROR-----------------"
        + "---------------\r\n"
        + "! Error message: Cannot read portfolio. It may have a wrong format.\r\n"
        + "----------------------------------------------------------\r\n"
        + "*********************************************************\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "*********************************************************\r\n"
        + "--Please enter the name of the portfolio that you want to examine. "
        + "The name cannot be end,yes,no,back.--\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Error2() {
    setUp();
    View view = new LoadPageView(printStream, null,
        "file not found.");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: file not found.\r\n"
        + "----------------------------------------------------------\r\n" +
        "*********************************************************\r\n" +
        "!!! If you enter back, you will back to the main menu.\r\n" +
        "*********************************************************\r\n" +
        "--Please enter the name of the portfolio that you want to examine. " +
        "The name cannot be end,yes,no,back.--\r\n" +
        "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_PortfolioGet() {
    setUp();
    stocks.put("AAA", 100);
    stocks.put("AA", 1000);
    Portfolio portfolio = new Portfolio(stocks);
    View view = new LoadPageView(printStream, portfolio,
        null);
    view.render();
    assertEquals("*********************************************************\r\n" +
        "!!! If you enter back, you will back to the main menu.\r\n" +
        "*********************************************************\r\n" +
        "           +---------+---------------+\r\n" +
        "Portfolio: |    Stock|  No. of shares|\r\n" +
        "           +---------+---------------+\r\n" +
        "           |       AA|           1000|\r\n" +
        "           |      AAA|            100|\r\n" +
        "           +---------+---------------+\r\n" +
        "Do you want to determine the total value of current portfolio?\r\n" +
        "Please enter yes if you want to determine. Other input will be back to the " +
        "main menu.\r\n" +
        "input > ", outputStreamCaptor.toString());

  }

}
