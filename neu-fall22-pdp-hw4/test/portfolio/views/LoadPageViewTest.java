package portfolio.views;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import portfolio.views.impl.LoadPageView;
import portfolio.views.impl.MainPageView;

/**
 * This is a test class to test LoadPageView class.
 */
public class LoadPageViewTest {

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private PrintStream printStream;


  @Before
  public void setUp() {
    printStream = new PrintStream(outputStreamCaptor);
  }

  @Test
  public void testRender() {
  }

}
