package portfolio.entities;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import portfolio.views.View;
import portfolio.views.impl.MainPageView;

public class PortfolioTest {

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
    assertEquals("aaa", outputStreamCaptor.toString());
  }

  @Test
  public void testRender_ErrorMessage() {
    View view = new MainPageView(printStream, "this is error message from test.");
    view.render();
    assertEquals("aaa", outputStreamCaptor.toString());
  }
}
