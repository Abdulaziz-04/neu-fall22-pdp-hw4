package portfolio.models.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import portfolio.models.entities.StockListEntry;
import portfolio.models.entities.StockPrice;
import portfolio.models.stockprice.AlphaVantageApi;

/**
 * This is a test class to test AlphaVantageApiIT class.
 */
public class AlphaVantageApiIT {

  private final AlphaVantageApi api = new AlphaVantageApi();
  private final double EPSILON = 0.0000001;

  @Test
  public void getStockPrice() {
    Map<String, StockPrice> prices = api.getStockPrice("AAPL");
    assertEquals(452.82, prices.get("2020-08-07").getOpen(), EPSILON);
    assertEquals(454.7, prices.get("2020-08-07").getHigh(), EPSILON);
    assertEquals(441.17, prices.get("2020-08-07").getLow(), EPSILON);
    assertEquals(444.45, prices.get("2020-08-07").getClose(), EPSILON);
    assertEquals(49511403, prices.get("2020-08-07").getVolume(), EPSILON);
    assertTrue(prices.size() > 0);
  }

  @Test
  public void getStockPrice_StockNotFound() {
    try {
      api.getStockPrice("APPL");
    } catch (Exception e) {
      assertEquals("No price data not available for APPL", e.getMessage());
    }
  }

  @Test
  public void getStockList() {
    List<StockListEntry> list = api.getStockList();
    assertEquals("AA", list.get(1).getSymbol());
    assertEquals(LocalDate.parse("2016-10-18"), list.get(1).getIpoDate());
    assertTrue(list.size() > 0);
  }
}
