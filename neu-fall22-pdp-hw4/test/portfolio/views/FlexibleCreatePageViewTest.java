package portfolio.views;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.views.impl.FlexibleCreatePageView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for FlexibleCreatePageView.
 */
public class FlexibleCreatePageViewTest {

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private PrintStream printStream;

  private List<Transaction> transactions;


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
    transactions = new ArrayList<>();
  }

  @Test
  public void testRender_Date() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 0, transactions, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the date of transaction. Format: yyyy-MM-dd, Ex: 2022-10-10\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_ErrorTime() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 0, transactions,
            "The time format is error");
    view.render();

    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: The time format is error\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the date of transaction. Format: yyyy-MM-dd, Ex: 2022-10-10\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_SymbolError() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 1, transactions,
            "Cannot find the symbol");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: Cannot find the symbol\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter stock symbol. Format: All capital letters, Ex: AAPL\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Symbol() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 1, transactions, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter stock symbol. Format: All capital letters, Ex: AAPL\r\n"
        + "input > ", outputStreamCaptor.toString());

  }


  @Test
  public void testRender_Type() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 2, transactions, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter transaction type (BUY/SELL)\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_TypeError() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 2, transactions,
            "Type error");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: Type error\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter transaction type (BUY/SELL)\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_ShareError1() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 3, transactions,
            "The shares cannot be negative.");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: The shares cannot be negative.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter number of shares. Format: Positive integer, Ex: 100\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_ShareError2() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 3, transactions,
            "The share is not a number or an integer.");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: The share is not a number or an integer.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter number of shares. Format: Positive integer, Ex: 100\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Share() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 3, transactions, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter number of shares. Format: Positive integer, Ex: 100\r\n"
        + "input > ", outputStreamCaptor.toString());

  }


  @Test
  public void testRender_Fee() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 4, transactions, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter commission fee for this transaction. Format: Non-negative "
        + "double, Ex: 123.45\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_FeeError1() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 4, transactions,
            "Commission cannot be negative.");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: Commission cannot be negative.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter commission fee for this transaction. Format: Non-negative "
        + "double, Ex: 123.45\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_FeeError2() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 4, transactions,
            "Commission fee input is not a number.");
    view.render();
    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message: Commission fee input is not a number.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter commission fee for this transaction. Format: Non-negative "
        + "double, Ex: 123.45\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_Choice() {
    setUp();
    transactions.add(new Transaction(TransactionType.BUY, "AAA", 110,
        LocalDate.parse("2022-10-10"), 12));
    View view =
        new FlexibleCreatePageView(printStream, false, false, 5, transactions, null);
    view.render();
    assertEquals("             +-----------+------+---------+---"
        + "------------+---------------+\r\n"
        + "Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "             | 2022-10-10|   BUY|      AAA|            110|          $12.0|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Do you want to enter another transaction? (yes/no)\r\n"
        + "input > ", outputStreamCaptor.toString());

  }

  @Test
  public void testRender_End() {
    setUp();
    transactions.add(new Transaction(TransactionType.BUY, "AAA", 110,
        LocalDate.parse("2022-10-10"), 12));
    View view =
        new FlexibleCreatePageView(printStream, true, false, 5, transactions, null);

    view.render();
    assertEquals("             +-----------+------+---------+---"
        + "------------+---------------+\r\n"
        + "Transaction: |       Date|  Type|    Stock|  No. of shares| Commission fee|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "             | 2022-10-10|   BUY|      AAA|            110|          $12.0|\r\n"
        + "             +-----------+------+---------+---------------+---------------+\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the name of this portfolio.The name cannot"
        + " be end, back, no and yes\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_ErrorEnd() {
    setUp();
    View view =
        new FlexibleCreatePageView(printStream, false, false, 0, transactions,
            " Please enter transaction list again.");
    view.render();

    assertEquals("---------------------ERROR--------------------------------\r\n"
        + "! Error message:  Please enter transaction list again.\r\n"
        + "----------------------------------------------------------\r\n"
        + "-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Please enter the date of transaction. Format: yyyy-MM-dd, Ex: 2022-10-10\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_Named() {
    setUp();
    transactions.add(new Transaction(TransactionType.BUY, "AAA", 110,
        LocalDate.parse("2022-10-10"), 12));
    View view =
        new FlexibleCreatePageView(printStream, true, true, 5, transactions, null);
    view.render();
    assertEquals("-------------------------Tips-----------------------------\r\n"
        + "!!! If you enter back, you will back to the main menu.\r\n"
        + "!!! If you want to exit, please input exit\r\n"
        + "----------------------------------------------------------\r\n"
        + "Portfolio has been saved.\r\n"
        + "Please enter any key to load page or enter \"back\" to go back to main menu.\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

}