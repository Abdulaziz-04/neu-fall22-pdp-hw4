package portfolio.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a test class to test PortfolioEntry class.
 */
public class PortfolioEntryTest {

  private PortfolioEntry portfolioEntry;
  @Before
  public void setup() {
    portfolioEntry = new PortfolioEntry("AAA",100);
  }

  @Test
  public void test() {
    assertEquals("AAA",portfolioEntry.getSymbol());
    assertEquals(100,portfolioEntry.getAmount());
  }

}
