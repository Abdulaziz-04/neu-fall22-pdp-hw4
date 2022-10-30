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

  StockPriceApi api;
  Cache<List<StockListEntry>> stockListCache = new SimpleCache<>(100);
  Cache<Map<String, StockPrice>> stockPriceCache = new SimpleCache<>(100);

  public StockQueryServiceImpl(StockPriceApi api) {
    this.api = api;
  }

  @Override
  public Map<String, StockPrice> getStockPrice(LocalDate date, List<String> symbols) {
    Map<String, StockPrice> map = new HashMap<>();
    for (var symbol : symbols
    ) {
      StockPrice price = stockPriceCache.get(symbol, x -> api.getStockPrice(symbol))
          .get(date.toString());
      map.put(symbol, price);
    }
    return map;
  }

  @Override
  public List<StockListEntry> getStockList() {
    return stockListCache.get("list", x -> api.getStockList());
  }
}
