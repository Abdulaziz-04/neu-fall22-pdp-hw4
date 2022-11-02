package portfolio.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;
import portfolio.services.stockprice.StockPriceApi;

/**
 * Mock class for StockPriceApi interface.
 */
public class StockApiMock implements StockPriceApi {

  private final Map<String, StockPrice> map = new HashMap<>();
  private final List<StockListEntry> list = new ArrayList<>();
  private final boolean shouldFail;

  public StockApiMock(boolean shouldFail) {
    this.shouldFail = shouldFail;
  }

  @Override
  public Map<String, StockPrice> getStockPrice(String symbol) {
    if (shouldFail) {
      throw new RuntimeException("Something wrong.");
    }
    if (symbol.equals("AAPL")) {
      map.put("2022-10-10", new StockPrice(1, 2, 3, 4, 5));
      map.put("2022-10-11", new StockPrice(6, 7, 8, 9, 10));
      return map;
    } else if (symbol.equals("AAA")) {
      map.put("2022-10-10", new StockPrice(11, 22, 33, 44, 55));
      map.put("2022-10-11", new StockPrice(66, 77, 88, 99, 0));
      return map;
    }
    throw new RuntimeException("Something wrong.");
  }

  @Override
  public List<StockListEntry> getStockList() {
    if (shouldFail) {
      throw new RuntimeException("Something wrong.");
    }
    list.add(new StockListEntry("AAPL", "Apple", "a", "b", "c", "d", "e"));
    list.add(new StockListEntry("AAA", "Apple", "a", "b", "c", "d", "e"));
    return list;
  }
}
