package portfolio.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import portfolio.models.entities.StockListEntry;


/**
 * This is a test class to test StockListEntry class.
 */
public class StockListEntryTest {

  private StockListEntry stockListEntry;

  @Before
  public void setup() {
    stockListEntry = new StockListEntry("AAA", "AAA", "100",
        "type", "2009-11-11", "2010-11-12", "good");
  }

  @Test
  public void test() {
    assertEquals("AAA", stockListEntry.getSymbol());

  }
}
