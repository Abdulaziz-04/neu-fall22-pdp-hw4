package portfolio.models.entities;

import java.util.Collections;
import java.util.Map;

public class PortfolioPerformance {
  private final Map<String, Integer> performance;
  private final String scale;

  public PortfolioPerformance(Map<String, Integer> performance, String scale){
    this.performance = performance;
    this.scale = scale;
  }

  public Map<String, Integer> getPerformance() {
    return Collections.unmodifiableMap(performance);
  }

  public String getScale() {
    return scale;
  }
}
