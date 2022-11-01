package portfolio.views;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

public class InfoPageViewTest {

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
