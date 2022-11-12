package portfolio.models.stockprice;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.StockListEntry;
import portfolio.models.entities.StockPrice;

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
   * @throws Exception is there is any error
   */
  Map<String, StockPrice> getStockPrice(LocalDate date, List<String> symbols) throws Exception;

  /**
   * Get all stocks listing.
   *
   * @return a list of StockListEntry object
   * @throws Exception is there is any error
   */
  List<StockListEntry> getStockList() throws Exception;
}
