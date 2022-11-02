package portfolio.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


/**
 * This is a test class to test PortfolioWithValue class.
 */
public class PortfolioWithValueTest {

  private Map<String, Integer> stocks = new HashMap<>();
  private Map<Map<String, Integer>, Double> stocksWithValue =new HashMap<>();
  LocalDate date;
  private PortfolioWithValue portfolioWithValue;

  @Before
  public void setup() {
    date = LocalDate.parse("2022-10-10");
    stocksWithValue.put(Map.of("AAA",100),100.1);
    stocksWithValue.put(Map.of("AA",200),200.2);
    portfolioWithValue = new PortfolioWithValue(date, (List<PortfolioEntryWithValue>) stocksWithValue,50050);
  }

  @Test
  public void test() {
    assertEquals("2022-10-10",portfolioWithValue.getDate());
    assertEquals(50050,portfolioWithValue.getTotalValue());

  }
}
