package portfolio.entities;

import java.util.Map;

public class Portfolio {

  private final Map<String, Integer> stockMap;

  public Portfolio(Map<String, Integer> map){
    stockMap = map;
  }

  public Map<String, Integer> getStockMap() {
    return stockMap;
  }
}
