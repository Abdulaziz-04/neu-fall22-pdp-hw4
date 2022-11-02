package portfolio.services.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;
import portfolio.services.stockprice.AlphaVantageApi;

/**
 * This is a test class to test AlphaVantageApiIT class.
 */
public class AlphaVantageApiIT {

  AlphaVantageApi api = new AlphaVantageApi();

  @Test
  public void getStockPrice() {
    Map<String, StockPrice> prices = api.getStockPrice("AAPL");
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
    assertTrue(list.size() > 0);
  }
}
