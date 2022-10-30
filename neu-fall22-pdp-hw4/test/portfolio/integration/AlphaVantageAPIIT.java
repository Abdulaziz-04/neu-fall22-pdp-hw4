package portfolio.integration;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;
import portfolio.services.impl.AlphaVantageAPI;

public class AlphaVantageAPIIT {

  AlphaVantageAPI api = new AlphaVantageAPI();

  @Test
  public void getStockPrice(){
    Map<String, StockPrice> prices = api.getStockPrice("AAPL");
    assertTrue(prices.size() > 0);
  }

  @Test
  public void getStockList(){
    List<StockListEntry> list = api.getStockList();
    assertTrue(list.size() > 0);
  }
}
