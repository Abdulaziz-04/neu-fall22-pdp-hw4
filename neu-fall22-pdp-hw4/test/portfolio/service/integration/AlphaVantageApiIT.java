package portfolio.service.integration;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;
import portfolio.services.stockprice.AlphaVantageApi;

public class AlphaVantageApiIT {

  AlphaVantageApi api = new AlphaVantageApi();

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
