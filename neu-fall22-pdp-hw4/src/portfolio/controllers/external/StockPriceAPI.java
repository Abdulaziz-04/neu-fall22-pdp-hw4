package portfolio.controllers.external;

import portfolio.entities.StockListResponse;
import portfolio.entities.StockPriceRequest;
import portfolio.entities.StockPriceResponse;

public interface StockPriceAPI {
  public StockPriceResponse getStockPrice(StockPriceRequest request);
  public StockListResponse getStockList();
}
