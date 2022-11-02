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

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private PrintStream printStream;


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
  }

  @Test
  public void testRender_NullErrorMessage() {
    View view = new MainPageView(printStream, null);
    view.render();
    assertEquals("********************************************" +
            "********************************************************\r\n" +
            "This is the main menu of the portfolios\r\n" +
            "1.Create a portfolio\r\n" +
            "2.Examine the composition of a portfolio\r\n" +
            "************************************************************" +
            "****************************************\r\n" +
            "!!!If you want to determine a portfolio, you need to go " +
            "to examine page or create page first and then go to determine it.\r\n" +
            "---------------------------------------------------" +
            "-----------------------------------------------------------------------------\r\n" +
            "Please enter the number 1 or 2 that you " +
            "want to choose.\r\n", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_ErrorMessage() {
    View view = new MainPageView(printStream, "Please enter the correct number!");
    view.render();
    assertEquals("!Error message: Please enter the correct number!\r\n"+
            "*******************************************************************************" +
            "*********************\r\n" +
            "This is the main menu of the portfolios\r\n" +
            "1.Create a portfolio\r\n" +
            "2.Examine the composition of a portfolio\r\n" +
            "*********************************************************************" +
            "*******************************\r\n" +
            "!!!If you want to determine a portfolio, you need to go " +
                    "to examine page or create page first and then go to determine it.\r\n" +
            "---------------------------------------------------------------------" +
            "-----------------------------------------------------------\r\n" +
            "Please enter the number 1 or 2 that you want to choose.\r\n",
            outputStreamCaptor.toString());
  }
}
