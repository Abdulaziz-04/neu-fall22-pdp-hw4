package portfolio.models.portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.models.entities.PortfolioEntryWithValue;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.impl.FlexiblePortfolio;

/**
 * This is a test class to test Portfolio class.
 */
public class FlexiblePortfolioTest {

  private final List<Transaction> transactions = new ArrayList<>();
  private final Map<String, StockPrice> prices = new HashMap<>();
  private final double EPSILON = 0.000000001;
  private Portfolio portfolio;

  @Before
  public void setUp() throws Exception {
    transactions.add(new Transaction(TransactionType.BUY, "AAA", 110, LocalDate.parse("2022-10-10"), 12));
    transactions.add(new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-10"), 34));
    transactions.add(new Transaction(TransactionType.BUY, "AAPL", 1000, LocalDate.parse("2022-10-11"), 56));
    prices.put("AAA", new StockPrice(1, 2, 3, 4, 5));
    prices.put("AAPL", new StockPrice(11, 22, 33, 44, 55));
    portfolio = new FlexiblePortfolio("name", transactions);
  }

  @Test
  public void getName(){
    assertEquals("name", portfolio.getName());
  }

  @Test
  public void getFormat() {
    assertEquals(PortfolioFormat.FLEXIBLE, portfolio.getFormat());
  }

  @Test
  public void create() throws Exception {
    Portfolio actualPortfolio = portfolio.create(transactions);
    List<Transaction> expected = portfolio.getTransactions();
    List<Transaction> actual = actualPortfolio.getTransactions();

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getType(), expected.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount());
      assertEquals(actual.get(i).getDate(), expected.get(i).getDate());
    }
    Map<String,Integer> expectedMap = portfolio.getComposition();
    Map<String,Integer> actualMap = actualPortfolio.getComposition();
    assertEquals(expectedMap.size(), actualMap.size());
  }

  @Test
  public void create_fail(){
    transactions.add(new Transaction(TransactionType.SELL, "AAPL", 2000, LocalDate.parse("2022-10-11"), 56));
    try {
     portfolio.create(transactions);
     fail("should fail");
    }
   catch (Exception e){
      assertEquals("There is a conflict in the input transaction.", e.getMessage());
   }
  }

  @Test
  public void getCostBasis_latest() throws Exception {
    double actual = portfolio.getCostBasis(LocalDate.now(), prices);
    assertEquals(44582.0, actual, EPSILON);
  }

  @Test
  public void getCostBasis_mid() throws Exception {
    double actual = portfolio.getCostBasis(LocalDate.parse("2022-10-10"), prices);
    assertEquals(0.0, actual, EPSILON);
  }

  @Test
  public void getCostBasis_noStock() throws Exception {
    double actual = portfolio.getCostBasis(LocalDate.parse("1999-01-01"), prices);
    assertEquals(0.0, actual, EPSILON);
  }

  @Test
  public void isReadOnly() {
    assertFalse(portfolio.isReadOnly());
  }

  @Test
  public void getComposition_latest() {
    Map<String, Integer> portfolioEntries = portfolio.getComposition();
    assertEquals(2, portfolioEntries.size());
    assertEquals(100, (int) portfolioEntries.get("AAA"));
    assertEquals(1000, (int) portfolioEntries.get("AAPL"));
  }

  @Test
  public void getComposition_mid() {
    Map<String, Integer> portfolioEntries = portfolio.getComposition(LocalDate.parse("2022-10-10"));
    assertEquals(1, portfolioEntries.size());
    assertEquals(100, (int) portfolioEntries.get("AAA"));
  }

  @Test
  public void getComposition_noStock() {
    Map<String, Integer> portfolioEntries = portfolio.getComposition(LocalDate.parse("1999-01-01"));
    assertEquals(0, portfolioEntries.size());
  }

  @Test
  public void getTransactions() {
    List<Transaction> portfolioEntries = portfolio.getTransactions();
    assertEquals(3, portfolioEntries.size());
    assertEquals("AAA", portfolioEntries.get(0).getSymbol());
    assertEquals("AAA", portfolioEntries.get(1).getSymbol());
    assertEquals("AAPL", portfolioEntries.get(2).getSymbol());
    assertEquals(TransactionType.BUY, portfolioEntries.get(0).getType());
    assertEquals(TransactionType.SELL, portfolioEntries.get(1).getType());
    assertEquals(TransactionType.BUY, portfolioEntries.get(2).getType());
    assertEquals(LocalDate.parse("2022-10-10"), portfolioEntries.get(0).getDate());
    assertEquals(LocalDate.parse("2022-10-10"), portfolioEntries.get(1).getDate());
    assertEquals(LocalDate.parse("2022-10-11"), portfolioEntries.get(2).getDate());
    assertEquals(110, portfolioEntries.get(0).getAmount());
    assertEquals(10, portfolioEntries.get(1).getAmount());
    assertEquals(1000, portfolioEntries.get(2).getAmount());
  }

  @Test
  public void getSymbols_latest() {
    List<String> symbols = portfolio.getSymbols(null);
    assertEquals("[AAA, AAPL]", symbols.toString());
  }

  @Test
  public void getSymbols_mid() {
    List<String> symbols = portfolio.getSymbols(LocalDate.parse("2022-10-10"));
    assertEquals("[AAA]", symbols.toString());
  }

  @Test
  public void getSymbols_noStock() {
    List<String> symbols = portfolio.getSymbols(LocalDate.parse("1999-01-01"));
    assertEquals("[]", symbols.toString());
  }

  @Test
  public void getPortfolioWithValue() {
    LocalDate date = LocalDate.parse("2022-10-10");
    PortfolioWithValue portfolioWithValue = portfolio.getPortfolioWithValue(date, prices);
    assertEquals(date, portfolioWithValue.getDate());
    assertEquals(400.0, portfolioWithValue.getTotalValue(), EPSILON);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getValues();
    assertEquals(400, list.get(0).getValue(), EPSILON);
  }

  @Test
  public void getPortfolioWithPrice_withNull() {
    transactions.add(new Transaction(TransactionType.BUY, "ABC", 1000, LocalDate.parse("2022-10-11"), 12));
    prices.put("ABC", null);
    portfolio = new FlexiblePortfolio("name", transactions);
    LocalDate date = LocalDate.parse("2022-10-11");
    PortfolioWithValue portfolioWithValue = portfolio.getPortfolioWithValue(date, prices);

    List<PortfolioEntryWithValue> list = portfolioWithValue.getValues();
    assertNull(list.get(2).getValue());
  }

}
