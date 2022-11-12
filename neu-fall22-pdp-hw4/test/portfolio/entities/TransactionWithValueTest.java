package portfolio.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.PortfolioEntryWithValue;


/**
 * This is a test class to test PortfolioEntryWithValue class.
 */
public class TransactionWithValueTest {

  private PortfolioEntryWithValue portfolioEntryWithValue;

  @Before
  public void setup() {
    Transaction transaction = new Transaction("AAA", 100);
    double price = 100.2;
    portfolioEntryWithValue = new PortfolioEntryWithValue(transaction, price);
  }

  @Test
  public void test() {
    assertEquals("AAA", portfolioEntryWithValue.getSymbol());
    assertEquals(100, portfolioEntryWithValue.getAmount());
    assertEquals(Double.valueOf(100.2), portfolioEntryWithValue.getValue());
  }

}
