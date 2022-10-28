package portfolio.controllers;

import portfolio.entities.StockPriceRequest;
import portfolio.entities.StockPriceResponse;

public interface StockPriceAPI {
  StockPriceResponse getPrice(StockPriceRequest request);
}
