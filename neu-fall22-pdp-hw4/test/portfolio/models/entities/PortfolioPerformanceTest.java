package portfolio.models.entities;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for PortfolioPerformance.
 */
public class PortfolioPerformanceTest {

  private PortfolioPerformance portfolioPerformance;

  @Before
  public void setup() {
    Map<String, Integer> numStars = new LinkedHashMap<>();
    numStars.put("1", 1);
    numStars.put("2", 2);
    portfolioPerformance = new PortfolioPerformance(numStars, "this is scale.",
            Double.parseDouble("2"), Double.parseDouble("1"));
  }

  @Test
  public void test() {
    Map<String, Integer> map = portfolioPerformance.getPerformance();
    assertEquals(2, map.size());
    assertEquals(1, (int) map.get("1"));
    assertEquals(2, (int) map.get("2"));
  }
}
