package portfolio.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * This is a test class to test PortfolioEntryWithValue class.
 */
public class PortfolioEntryWithValueTest {

  private PortfolioEntryWithValue portfolioEntryWithValue;

  @Before
  public void setup() {
    PortfolioEntry portfolioEntry = new PortfolioEntry("AAA", 100);
    double price = 100.2;
    portfolioEntryWithValue = new PortfolioEntryWithValue(portfolioEntry, price);
  }

  @Test
  public void test() {
    assertEquals("AAA", portfolioEntryWithValue.getSymbol());
    assertEquals(100, portfolioEntryWithValue.getAmount());
    assertEquals(Double.valueOf(100.2), portfolioEntryWithValue.getValue());
  }

}
