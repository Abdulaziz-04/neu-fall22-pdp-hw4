package portfolio.services.stockprice;

import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

public interface StockPriceApi {
  Map<String, StockPrice> getStockPrice(String symbol) throws RuntimeException;
  List<StockListEntry> getStockList() throws RuntimeException;
}
