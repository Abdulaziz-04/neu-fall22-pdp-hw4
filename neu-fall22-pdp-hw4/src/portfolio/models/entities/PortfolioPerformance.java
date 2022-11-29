package portfolio.models.entities;

import java.util.Collections;
import java.util.Map;

/**
 * This is a class represent a portfolio performance on specific date range for showing user. The
 * value stores are scaled.
 */
public class PortfolioPerformance {

  private final Map<String, Integer> performance;
  private final String scale;

  private double max;
  private double min;

  /**
   * This is a constructor to construct a PortfolioEntryWithValue object.
   *
   * @param performance Map of date and scaled value
   * @param scale  scale
   */
  public PortfolioPerformance(Map<String, Integer> performance, String scale, double max,
                              double min) {
    this.performance = performance;
    this.scale = scale;
    this.max = max;
    this.min = min;
  }

  public Map<String, Integer> getPerformance() {
    return Collections.unmodifiableMap(performance);
  }

  public String getScale() {
    return scale;
  }

  public double getMax() {
    return max;
  }

  public double getMin() {
    return min;
  }
}
