package portfolio.services.stockprice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;
import portfolio.services.cache.Cache;
import portfolio.services.cache.SimpleCache;

public class StockQueryServiceImpl implements StockQueryService {

  private final StockPriceApi api;
  private final Cache<List<StockListEntry>> stockListCache = new SimpleCache<>(10000);
  private final Cache<Map<String, StockPrice>> stockPriceCache = new SimpleCache<>(10000);

  public StockQueryServiceImpl(StockPriceApi api) {
    this.api = api;
  }

  private boolean isInList(String symbol){
    for (var entry: getStockList()){
      if (symbol.equals(entry.getSymbol()))
        return true;
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
