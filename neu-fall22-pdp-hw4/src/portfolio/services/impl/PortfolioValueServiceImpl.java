package portfolio.services.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioValue;
import portfolio.entities.StockPrice;
import portfolio.services.PortfolioValueService;
import portfolio.services.StockQueryService;

public class PortfolioValueServiceImpl implements PortfolioValueService {

  private StockQueryService stockQueryService;

  public PortfolioValueServiceImpl(StockQueryService stockQueryService){
    this.stockQueryService = stockQueryService;
  }

  @Override
  public PortfolioValue getValue(Portfolio portfolio, LocalDate date) {

    Map<String, Double> priceMap = new HashMap<>();

    for (String symbol: portfolio.getStockMap().keySet()
    ) {
      StockPrice price = stockQueryService.getStockPrice(symbol).get(date.toString());
      priceMap.put(symbol, price.getClose());
    }

    return new PortfolioValue(date, priceMap);
  }
}
