package portfolio.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.TransactionConverter;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.PortfolioEntryWithValue;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;

/**
 * This is a test class to test Portfolio class.
 */
public class InflexiblePortfolioTest {

  private final Map<String, Integer> stocks = new HashMap<>();
  private final Map<String, StockPrice> prices = new HashMap<>();
  private final double EPSILON = 0.000000001;
  private Portfolio portfolio;

  @Before
  public void setUp() {
    stocks.put("AAA", 100);
    stocks.put("AAPL", 1000);
    prices.put("AAA", new StockPrice(1, 2, 3, 4, 5));
    prices.put("AAPL", new StockPrice(11, 22, 33, 44, 55));
    portfolio = new InflexiblePortfolio(TransactionConverter.convert(stocks));
  }

  @Test
  public void getFormat() {
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolio.getFormat());
  }

  @Test
  public void create() {
    try {
      portfolio.create(null);
      fail("should fail");
    }
    catch (Exception e) {
      assertEquals("Modifying inflexible portfolio is not supported.", e.getMessage());
    }
  }

  @Test
  public void getCostBasis() {
    try {
      portfolio.getCostBasis(LocalDate.now(), null, 0.0);
      fail("should fail");
    }
    catch (Exception e) {
      assertEquals("Cost basis function is not supported.", e.getMessage());
    }
  }

  @Test
  public void isReadOnly() {
    assertTrue(portfolio.isReadOnly());
  }

  @Test
  public void getStocks() {
    Map<String, Integer> portfolioEntries = portfolio.getStocks();
    assertEquals(2, portfolioEntries.size());
  }

  @Test
  public void getTransactions() {
    List<Transaction> portfolioEntries = portfolio.getTransaction();
    assertEquals(2, portfolioEntries.size());
    assertEquals("AAA", portfolioEntries.get(0).getSymbol());
    assertEquals("AAPL", portfolioEntries.get(1).getSymbol());
    assertEquals(100, portfolioEntries.get(0).getAmount());
    assertEquals(1000, portfolioEntries.get(1).getAmount());
  }

  @Test
  public void getSymbols() {
    List<String> symbols = portfolio.getSymbols(null);
    assertEquals("[AAA, AAPL]", symbols.toString());
  }

  @Test
  public void getPortfolioWithPrice() {
    LocalDate date = LocalDate.parse("2022-10-10");
    PortfolioWithValue portfolioWithValue = portfolio.getPortfolioWithValue(date, prices);
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
    portfolio = new InflexiblePortfolio(TransactionConverter.convert(stocks));
    LocalDate date = LocalDate.parse("2022-10-10");
    PortfolioWithValue portfolioWithValue = portfolio.getPortfolioWithValue(date, prices);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getStocks();
    assertNull(list.get(2).getValue());
  }

}
