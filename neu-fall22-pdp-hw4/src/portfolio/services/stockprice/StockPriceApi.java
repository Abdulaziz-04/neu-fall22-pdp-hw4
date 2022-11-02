package portfolio.services.stockprice;

import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

/**
 * This is an API interface, which will get the stocks data from internet.
 */
public interface StockPriceApi {

  /**
   * This is the method to get a stock price, which need to know the symbol of this stock.
   *
   * @param symbol the symbol of stock that we want to get price.
   * @return the map type that have the symbol as key and price as value.
   * @throws RuntimeException run error
   */
  Map<String, StockPrice> getStockPrice(String symbol) throws RuntimeException;

  /**
   * This is a method to get the stock list from internet.
   *
   * @return a list of stock list entry
   * @throws RuntimeException run error
   */
  List<StockListEntry> getStockList() throws RuntimeException;
}
