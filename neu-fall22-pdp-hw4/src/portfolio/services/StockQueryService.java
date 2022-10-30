package portfolio.services;

import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

/**
 * Service interface for querying stock price data from external source.
 */
public interface StockQueryService {

  /**
   * Get all time daily prices of the stock. The result will be in a format of a map of (date:
   * String, price: StockPrice).
   *
   * @param symbol stock symbol
   * @return a map of date and StockPrice
   */
  Map<String, StockPrice> getStockPrice(String symbol);

  /**
   * Get all stock listed in the US stock market.
   *
   * @return a list of StockListEntry object
   */
  List<StockListEntry> getStockList();
}
