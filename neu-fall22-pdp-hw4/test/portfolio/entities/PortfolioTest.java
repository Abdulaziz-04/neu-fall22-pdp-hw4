package portfolio.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class PortfolioTest {

  private final Map<String, Integer> stocks = new HashMap<>();
  private final Map<String, StockPrice> prices = new HashMap<>();
  private final double EPSILON = 0.000000001;
  private IPortfolio portfolio;

  @Before
  public void setUp(){
    stocks.put("AAA", 100);
    stocks.put("AAPL", 1000);
    prices.put("AAA", new StockPrice(1, 2, 3, 4, 5));
    prices.put("AAPL", new StockPrice(11, 22, 33, 44, 55));
    portfolio = new Portfolio(stocks);
  }

  @Test
  public void getStocks() {
    List<PortfolioEntry> portfolioEntries = portfolio.getStocks();
    assertEquals(2, portfolioEntries.size());
    assertEquals("AAA", portfolioEntries.get(0).getSymbol());
    assertEquals("AAPL", portfolioEntries.get(1).getSymbol());
    assertEquals(100, portfolioEntries.get(0).getAmount());
    assertEquals(1000, portfolioEntries.get(1).getAmount());
  }

  @Test
  public void getSymbols() {
    List<String> symbols = portfolio.getSymbols();
    assertEquals("[AAA, AAPL]", symbols.toString());
  }

  @Test
  public void getPortfolioWithPrice() {
    LocalDate date = LocalDate.parse("2022-10-10");
    PortfolioWithValue portfolioWithValue = portfolio.getPortfolioWithPrice(date, prices);
    assertEquals(date, portfolioWithValue.getDate());
    assertEquals(44400.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getStocks();
    assertEquals(400, list.get(0).getValue(), EPSILON);
    assertEquals(44000, list.get(1).getValue(), EPSILON);
  }

  @Test
  public void getPortfolioWithPrice_withNull() {
    stocks.put("ABC", 1000);
    prices.put("ABC", null);
    portfolio = new Portfolio(stocks);
    LocalDate date = LocalDate.parse("2022-10-10");
    PortfolioWithValue portfolioWithValue = portfolio.getPortfolioWithPrice(date, prices);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getStocks();
    assertNull(list.get(2).getValue());
  }

}
