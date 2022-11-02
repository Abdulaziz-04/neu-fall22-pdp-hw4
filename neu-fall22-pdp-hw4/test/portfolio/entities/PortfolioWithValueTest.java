package portfolio.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


/**
 * This is a test class to test PortfolioWithValue class.
 */
public class PortfolioWithValueTest {
  private List<PortfolioEntryWithValue> stocksWithValue = new ArrayList<>();
  LocalDate date;
  private PortfolioWithValue portfolioWithValue;

  @Before
  public void setup() {
    date = LocalDate.parse("2022-10-10");
    stocksWithValue.add(new PortfolioEntryWithValue(new PortfolioEntry("AAA", 100), 100.1));
    stocksWithValue.add(new PortfolioEntryWithValue(new PortfolioEntry("AA", 200), 200.2));
    portfolioWithValue = new PortfolioWithValue(date, stocksWithValue, 50050);
  }

  @Test
  public void test() {
    assertEquals(LocalDate.parse("2022-10-10"), portfolioWithValue.getDate());
    assertEquals(50050, portfolioWithValue.getTotalValue(),0.0000001);
  }
}
