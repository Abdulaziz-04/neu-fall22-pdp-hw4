package portfolio.services.stockprice;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

/**
 * Service interface for querying stock price data from external source.
 */
public interface StockQueryService {

  /**
   * Get all time daily prices of the stock. The result will be in a format of a map of (symbol:
   * String, price: StockPrice).
   *
   * @param date date
   * @param symbols stock symbol
   * @return a map of date and StockPrice
   */
  Map<String, StockPrice> getStockPrice(LocalDate date, List<String> symbols);

  /**
   * Get all stock listed in the US stock market.
   *
   * @return a list of StockListEntry object
   */
  List<StockListEntry> getStockList();
}
