package portfolio.models.stockprice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.StockListEntry;
import portfolio.models.entities.StockPrice;
import portfolio.models.cache.Cache;
import portfolio.models.cache.SimpleCache;

/**
 * StockQueryServiceImpl implement StockQueryService interface. The class utilizes SimpleCache to
 * cache the data that has been retrieved from StockPriceApi.
 */
public class StockQueryServiceImpl implements StockQueryService {

  private final StockPriceApi api;
  private final Cache<List<StockListEntry>> stockListCache = new SimpleCache<>(10000);
  private final Cache<Map<String, StockPrice>> stockPriceCache = new SimpleCache<>(10000);

  /**
   * This is a constructor to construct the StockQueryServiceImpl object, which contains the
   * StockPriceApi.
   *
   * @param api the StockPriceApi object
   */
  public StockQueryServiceImpl(StockPriceApi api) {
    this.api = api;
  }

  /**
   * This is a method to know the symbol of a stock is in the list or not.
   *
   * @param symbol the symbol of a stock that we want to find
   * @return if it is in the list, true. Otherwise, false.
   */
  private boolean isInList(String symbol) {
    for (var entry : getStockList()) {
      if (symbol.equals(entry.getSymbol())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Map<String, StockPrice> getStockPrice(LocalDate date, List<String> symbols) {
    Map<String, StockPrice> map = new HashMap<>();
    for (var symbol : symbols
    ) {
      StockPrice price = null;
      if (isInList(symbol)) {
        price = stockPriceCache.get(symbol, x -> api.getStockPrice(symbol)).get(date.toString());
        if (price == null) {
          throw new IllegalArgumentException("Date not found.");
        }
      }
      map.put(symbol, price);
    }
    return map;
  }

  @Override
  public List<StockListEntry> getStockList() {
    return stockListCache.get("list", x -> api.getStockList());
  }
}
