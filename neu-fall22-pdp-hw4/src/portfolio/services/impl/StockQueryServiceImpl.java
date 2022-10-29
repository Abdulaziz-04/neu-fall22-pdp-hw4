package portfolio.services.impl;

import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;
import portfolio.services.Cache;
import portfolio.services.StockQueryService;

public class StockQueryServiceImpl implements StockQueryService {

  AlphaVantageAPI api;
  Cache<List<StockListEntry>> stockListCache = new SimpleCache<>(100);
  Cache<Map<String,StockPrice>> stockPriceCache = new SimpleCache<>(100);

  public StockQueryServiceImpl(AlphaVantageAPI api) {
    this.api = api;
  }

  @Override
  public Map<String, StockPrice> getStockPrice(String symbol) {
    return stockPriceCache.get(symbol, x -> api.getStockPrice(symbol));
  }

  @Override
  public List<StockListEntry> getStockList() {
    return stockListCache.get("list", x -> api.getStockList());
  }
}
