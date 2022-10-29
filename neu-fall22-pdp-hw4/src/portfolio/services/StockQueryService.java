package portfolio.services;

import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

public interface StockQueryService {
  // Map<symbol, StockPrice>
  Map<String, StockPrice> getStockPrice(String symbol);

  List<StockListEntry> getStockList();
}
