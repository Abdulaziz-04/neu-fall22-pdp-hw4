package portfolio.controllers.external;

import portfolio.entities.StockListResponse;
import portfolio.entities.StockPriceRequest;
import portfolio.entities.StockPriceResponse;

public interface StockPriceAPI {
  StockPriceResponse getStockPrice(StockPriceRequest request);
  public StockListResponse getStockList();
}
