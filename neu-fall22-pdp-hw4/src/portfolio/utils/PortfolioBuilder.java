package portfolio.utils;

import java.util.HashMap;
import java.util.Map;
import portfolio.entities.Portfolio;

public class PortfolioBuilder {

  private Map<String, Integer> map = new HashMap();

  public void clear(){
    map = new HashMap();
  }
  public void add(String symbol, int amount){
      amount = amount + map.getOrDefault(symbol, 0);
      map.put(symbol, amount);
  }

  public Portfolio getResult(){
    return new Portfolio(map);
  }
}
