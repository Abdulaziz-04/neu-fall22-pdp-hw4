package portfolio.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

public interface StockQueryService {
  Map<String, StockPrice> getStockPrice(String symbol);

  List<StockListEntry> getStockList();
}
