package portfolio.views;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import portfolio.views.impl.MainPageView;

/**
 * This is a test class to test MainPageView class.
 */
public class MainPageViewTest {

  private final ByteArrayOutputStream outputStreamCaptor =
      new ByteArrayOutputStream();
  private PrintStream printStream;


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
  }

  @Test
  public void testRender_NullErrorMessage() {
    View view = new MainPageView(printStream, null, false);
    view.render();
    assertEquals("********************************************Main Menu"
            + "**********************************************\r\n"
            + "1. Create an inflexible portfolio\r\n"
            + "2. Create a flexible portfolio\r\n"
            + "3. Load a portfolio\r\n"
            + "****************************************************************"
            + "************************************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            + "!!! If you want to determine portfolio's value\r\n"
            + "    or modify a portfolio, you need to load or\r\n"
            +  "    create a portfolio first.\r\n"
            + "!!! If you want to show the performance, you\r\n"
            + "    need to load or create a portfolio first.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            + "----------------------------------------------------------\r\n"
            + "Please enter the number 1,2 or 3.\r\n"
            + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_ErrorMessage() {
    View view = new MainPageView(printStream,
        "Please enter the correct number!", false);
    view.render();
    assertEquals("---------------------ERROR----------------"
        + "----------------\r\n"
        + "! Error message: Please enter the correct number!\r\n"
        + "----------------------------------------------------------\r\n"
        + "********************************************Main Menu"
            + "**********************************************\r\n"
            + "1. Create an inflexible portfolio\r\n"
            + "2. Create a flexible portfolio\r\n"
            + "3. Load a portfolio\r\n"
            + "****************************************************************"
            + "************************************\r\n"
            + "-------------------------Tips-----------------------------\r\n"
            + "!!! If you want to determine portfolio's value\r\n"
            + "    or modify a portfolio, you need to load or\r\n"
            +  "    create a portfolio first.\r\n"
            + "!!! If you want to show the performance, you\r\n"
            + "    need to load or create a portfolio first.\r\n"
            + "!!! If you want to exit, please input exit\r\n"
            + "----------------------------------------------------------\r\n"
            + "Please enter the number 1,2 or 3.\r\n"
        + "input > ", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_InitializeError() {
    View view = new MainPageView(printStream, null, true);
    view.render();
    assertEquals("Something wrong with external "
        + "API, cannot initialize the application. "
        + "Please try again in few minutes.\r\n", outputStreamCaptor.toString());
  }


}
