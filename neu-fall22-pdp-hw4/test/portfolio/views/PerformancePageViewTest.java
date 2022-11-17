package portfolio.views;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import portfolio.views.impl.PerformancePageView;

import static org.junit.Assert.*;



public class PerformancePageViewTest {



  private List<String> listStar;
  private List<String> list;


  private final ByteArrayOutputStream outputStreamCaptor =
          new ByteArrayOutputStream();
  private PrintStream printStream;
  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
    list = new ArrayList<>();
    listStar = new ArrayList<>();
  }

  @Test
  public void testRender_First() {
    View view = new PerformancePageView(printStream,"name1",
            null,null,null,null,null,false,
            null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            +"!!! If you input back, you will back to the load page.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            + "----------------------------------------------------------\r\n"
            + "Please enter the start date of the timespan that you want to performance.\r\n"
            + "--EX.2020-10-09\r\n"
            + "--The format of date needs to be 2022-10-11\r\n"
            + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_startDate() {
    View view = new PerformancePageView(printStream,"name1",
            LocalDate.parse("2013-01-10"),
            null,null,null,null,false,
            null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            +"!!! If you input back, you will back to the load page.\r\n"
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
    list.add("2013: ");
    list.add("2014: ");
    list.add("2015: ");
    list.add("2016: ");
    list.add("2017: ");
    list.add("2018: ");
    list.add("2019: ");
    list.add("2020: ");
    list.add("2021: ");
    list.add("2022: ");
    listStar.add("*****");
    listStar.add("**************");
    listStar.add("***************");
    listStar.add("**************");
    listStar.add("**********");
    listStar.add("*******");
    listStar.add("*********************");
    listStar.add("********************************");
    listStar.add("*****************");
    listStar.add("***************");
    View view = new PerformancePageView(printStream,"name1",
            LocalDate.parse("2013-01-10"),LocalDate.parse("2022-10-11"),
            list, listStar,
            "one asterisk is $ 100 more than a base amount of $100000",true,
            null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            + "!!! If you input back, you will back to the load page.\r\n"
            +  "!!! If you want to exit, please input exit\r\n"
            + "----------------------------------------------------------\r\n"
            +  "Performance of portfolio name1 from 2013-01-10 to 2022-10-11\r\n"
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
    list.add("2022-01: ");
    list.add("2022-02: ");
    list.add("2022-03: ");
    list.add("2022-04: ");
    list.add("2022-05: ");
    list.add("2022-06: ");
    list.add("2022-07: ");
    list.add("2022-08: ");
    list.add("2022-09: ");
    list.add("2022-10: ");
    listStar.add("*****");
    listStar.add("**************");
    listStar.add("***************");
    listStar.add("**************");
    listStar.add("**********");
    listStar.add("*******");
    listStar.add("*********************");
    listStar.add("********************************");
    listStar.add("*****************");
    listStar.add("***************");
    View view = new PerformancePageView(printStream,"name1",
            LocalDate.parse("2022-01-05"),LocalDate.parse("2022-10-11"),
            list, listStar,
            "one asterisk is $ 100 more than a base amount of $100000",true,
            null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            + "!!! If you input back, you will back to the load page.\r\n"
            +  "!!! If you want to exit, please input exit\r\n"
            + "----------------------------------------------------------\r\n"
            +  "Performance of portfolio name1 from 2022-01-05 to 2022-10-11\r\n"
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
    list.add("2022-02-01: ");
    list.add("2022-02-02: ");
    list.add("2022-02-03: ");
    list.add("2022-02-04: ");
    list.add("2022-02-05: ");
    list.add("2022-02-06: ");
    list.add("2022-02-07: ");
    list.add("2022-02-08: ");
    list.add("2022-02-09: ");
    list.add("2022-02-10: ");
    listStar.add("*****");
    listStar.add("**************");
    listStar.add("***************");
    listStar.add("**************");
    listStar.add("**********");
    listStar.add("*******");
    listStar.add("*********************");
    listStar.add("********************************");
    listStar.add("*****************");
    listStar.add("***************");
    View view = new PerformancePageView(printStream,"name1",
            LocalDate.parse("2022-02-01"),LocalDate.parse("2022-02-10"),
            list, listStar,
            "one asterisk is $ 100 more than a base amount of $100000",true,
            null);
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            + "!!! If you input back, you will back to the load page.\r\n"
            +  "!!! If you want to exit, please input exit\r\n"
            + "----------------------------------------------------------\r\n"
            +  "Performance of portfolio name1 from 2022-02-01 to 2022-02-10\r\n"
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
    View view = new PerformancePageView(printStream,"name1",
            null,null,null,null,
            null,false,
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
    View view = new PerformancePageView(printStream,"name1",
            null,null,null,null,
            null,false,
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
    View view = new PerformancePageView(printStream,"name1",
            LocalDate.parse("2013-01-10"),
            null,null,null,null,false,
            "Error end date format!");
    view.render();
    assertEquals("*************This is the interface for Performance**************\r\n"
            + "---------------------ERROR--------------------------------\r\n"
            + "! Error message: Error end date format!\r\n"
            + "----------------------------------------------------------\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            +   "!!! If you input back, you will back to the load page.\r\n"
            +  "!!! If you want to exit, please input exit\r\n"
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