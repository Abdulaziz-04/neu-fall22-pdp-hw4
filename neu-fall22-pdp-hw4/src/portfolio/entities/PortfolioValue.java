package portfolio.entities;

import java.time.LocalDate;
import java.util.Map;

public class PortfolioValue {

  private LocalDate date;
  private Map<String, Double> priceMap;

  public PortfolioValue(LocalDate date, Map<String, Double> priceMap){
    this.date = date;
    this.priceMap = priceMap;
  }

  public LocalDate getDate() {
    return date;
  }

  public Map<String, Double> getPriceMap() {
    return priceMap;
  }
}
