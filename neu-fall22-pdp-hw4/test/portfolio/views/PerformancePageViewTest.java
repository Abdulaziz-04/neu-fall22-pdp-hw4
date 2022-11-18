package portfolio.views;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.views.impl.PerformancePageView;


/**
 * This is a test for performance page.
 */
public class PerformancePageViewTest {

  private Map<String, Integer> map;

  private final ByteArrayOutputStream outputStreamCaptor =
      new ByteArrayOutputStream();
  private PrintStream printStream;

  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
    map = new LinkedHashMap<>();
  }

  @Test
  public void testRender_First() {
    View view = new PerformancePageView(printStream, "name1",
        null, null, null, null, false,
        null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the start date of the timespan that you want to performance.\r\n"
        + "--EX.2020-10-09\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_startDate() {
    View view = new PerformancePageView(printStream, "name1",
        LocalDate.parse("2013-01-10"),
        null, null, null, false,
        null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "+---------------+\r\n"
        + "|     start date|\r\n"
        + "+---------------+\r\n"
        + "|     2013-01-10|\r\n"
        + "+---------------+\r\n"
        + "Please enter the end date of the timespan that you want to performance.\r\n"
        + "--EX.2022-10-11\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Year() {
    setUp();
    map.put("2013: ", 5);
    map.put("2014: ", 14);
    map.put("2015: ", 15);
    map.put("2016: ", 14);
    map.put("2017: ", 10);
    map.put("2018: ", 7);
    map.put("2019: ", 21);
    map.put("2020: ", 32);
    map.put("2021: ", 17);
    map.put("2022: ", 15);
    View view = new PerformancePageView(printStream, "name1",
        LocalDate.parse("2013-01-10"), LocalDate.parse("2022-10-11"),
        map,
        "one asterisk is $ 100 more than a base amount of $100000", true,
        null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Performance of portfolio name1 from 2013-01-10 to 2022-10-11\r\n"
        + "2013: *****\r\n"
        + "2014: **************\r\n"
        + "2015: ***************\r\n"
        + "2016: **************\r\n"
        + "2017: **********\r\n"
        + "2018: *******\r\n"
        + "2019: *********************\r\n"
        + "2020: ********************************\r\n"
        + "2021: *****************\r\n"
        + "2022: ***************\r\n"
        + "scale: one asterisk is $ 100 more than a base amount of $100000\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the start date of the timespan that you want to performance.\r\n"
        + "--EX.2020-10-09\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_Month() {
    setUp();
    map.put("2022-01: ", 5);
    map.put("2022-02: ", 14);
    map.put("2022-03: ", 15);
    map.put("2022-04: ", 14);
    map.put("2022-05: ", 10);
    map.put("2022-06: ", 7);
    map.put("2022-07: ", 21);
    map.put("2022-08: ", 32);
    map.put("2022-09: ", 17);
    map.put("2022-10: ", 15);
    View view = new PerformancePageView(printStream, "name1",
        LocalDate.parse("2022-01-05"), LocalDate.parse("2022-10-11"),
        map,
        "one asterisk is $ 100 more than a base amount of $100000", true,
        null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Performance of portfolio name1 from 2022-01-05 to 2022-10-11\r\n"
        + "2022-01: *****\r\n"
        + "2022-02: **************\r\n"
        + "2022-03: ***************\r\n"
        + "2022-04: **************\r\n"
        + "2022-05: **********\r\n"
        + "2022-06: *******\r\n"
        + "2022-07: *********************\r\n"
        + "2022-08: ********************************\r\n"
        + "2022-09: *****************\r\n"
        + "2022-10: ***************\r\n"
        + "scale: one asterisk is $ 100 more than a base amount of $100000\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the start date of the timespan that you want to performance.\r\n"
        + "--EX.2020-10-09\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_Day() {
    setUp();
    map.put("2022-02-01: ", 5);
    map.put("2022-02-02: ", 14);
    map.put("2022-02-03: ", 15);
    map.put("2022-02-04: ", 14);
    map.put("2022-02-05: ", 10);
    map.put("2022-02-06: ", 7);
    map.put("2022-02-07: ", 21);
    map.put("2022-02-08: ", 32);
    map.put("2022-02-09: ", 17);
    map.put("2022-02-10: ", 15);
    View view = new PerformancePageView(printStream, "name1",
        LocalDate.parse("2022-02-01"), LocalDate.parse("2022-02-10"),
        map,
        "one asterisk is $ 100 more than a base amount of $100000", true,
        null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Performance of portfolio name1 from 2022-02-01 to 2022-02-10\r\n"
        + "2022-02-01: *****\r\n"
        + "2022-02-02: **************\r\n"
        + "2022-02-03: ***************\r\n"
        + "2022-02-04: **************\r\n"
        + "2022-02-05: **********\r\n"
        + "2022-02-06: *******\r\n"
        + "2022-02-07: *********************\r\n"
        + "2022-02-08: ********************************\r\n"
        + "2022-02-09: *****************\r\n"
        + "2022-02-10: ***************\r\n"
        + "scale: one asterisk is $ 100 more than a base amount of $100000\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the start date of the timespan that you want to performance.\r\n"
        + "--EX.2020-10-09\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_Error1() {
    setUp();
    View view = new PerformancePageView(printStream, "name1",
        null, null, null,
        null, false,
        "Error: Please choose input new timespan. "
            + "This start date maybe the holiday or "
            + "weekend!");
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "---------------------ERROR--------------------------------\r\n"
        + "! Error message: Error: Please choose input new timespan. This start date maybe "
        + "the holiday or weekend!\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n" +
        "!!! If you input back, you will back to the load page.\r\n" +
        "!!! If you want to exit, please input exit\r\n" +
        "----------------------------------------------------------\r\n"
        + "Please enter the start date of the timespan that you want to performance.\r\n"
        + "--EX.2020-10-09\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_Error2() {
    setUp();
    View view = new PerformancePageView(printStream, "name1",
        null, null, null,
        null, false,
        "Error start date format!");
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "---------------------ERROR--------------------------------\r\n"
        + "! Error message: Error start date format!\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the start date of the timespan that you want to performance.\r\n"
        + "--EX.2020-10-09\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_Error3() {
    setUp();
    View view = new PerformancePageView(printStream, "name1",
        LocalDate.parse("2013-01-10"),
        null, null, null, false,
        "Error end date format!");
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
        + "---------------------ERROR--------------------------------\r\n"
        + "! Error message: Error end date format!\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you input back, you will back to the load page.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "+---------------+\r\n"
        + "|     start date|\r\n"
        + "+---------------+\r\n"
        + "|     2013-01-10|\r\n"
        + "+---------------+\r\n"
        + "Please enter the end date of the timespan that you want to performance.\r\n"
        + "--EX.2022-10-11\r\n"
        + "--The format of date needs to be 2022-10-11\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

}