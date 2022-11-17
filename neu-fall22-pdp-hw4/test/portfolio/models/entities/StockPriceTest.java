package portfolio.models.entities;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import portfolio.models.entities.StockPrice;

/**
 * This is a test class to test StockPrice class.
 */
public class StockPriceTest {

  private StockPrice stockPrice;

  @Before
  public void setup() {
    stockPrice = new StockPrice(100.5, 200.0, 100.0, 400.0, 500);
  }

  @Test
  public void test() {
    assertEquals(100.5, stockPrice.getOpen(), 0.000000000000000000000000001);
    assertEquals(200.0, stockPrice.getHigh(), 0.000000000000000000000000001);
    assertEquals(100.0, stockPrice.getLow(), 0.000000000000000000000000001);
    assertEquals(400.0, stockPrice.getClose(), 0.000000000000000000000000001);
    assertEquals(500, stockPrice.getVolume());
  }
}
