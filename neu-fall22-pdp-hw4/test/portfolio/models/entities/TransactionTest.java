package portfolio.models.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import portfolio.models.entities.Transaction;

/**
 * This is a test class to test PortfolioEntry class.
 */
public class TransactionTest {

  private Transaction transaction;
  private final double EPSILON = 0.000000001;

  @Before
  public void setup() {
    transaction = new Transaction("AAA", 100);
  }

  @Test
  public void test() {
    assertEquals("AAA", transaction.getSymbol());
    assertEquals(100, transaction.getAmount(), EPSILON);
  }

}
