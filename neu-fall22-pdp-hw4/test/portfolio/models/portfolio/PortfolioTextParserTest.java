package portfolio.models.portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.impl.FlexiblePortfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.models.portfolio.impl.PortfolioTextParser;

/**
 * This is a test class to test PortfolioTextParser class.
 */
public class PortfolioTextParserTest {

  private final PortfolioParser portfolioParser = new PortfolioTextParser();
  private final double EPSILON = 0.000000001;

  @Test
  public void parse_v2_inflexible() throws Exception {
    String str = "FORMAT=INFLEXIBLE\r\na,100\r\nb,100\r\n";
    Portfolio portfolio = portfolioParser.parse(str);
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolio.getFormat());

    List<Transaction> actual = portfolio.getTransactions();
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction("a", 100));
    expected.add(new Transaction("b", 100));

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount(), EPSILON);
    }
  }

  @Test
  public void parse_v2_flexible() throws Exception {
    String str = "FORMAT=FLEXIBLE\r\n2022-10-10,BUY,AAA,100,12\r\n"
        + "2022-10-11,SELL,AAA,100,34\r\n";
    Portfolio portfolio = portfolioParser.parse(str);
    assertEquals(PortfolioFormat.FLEXIBLE, portfolio.getFormat());

    List<Transaction> actual = portfolioParser.parse(str).getTransactions();
    List<Transaction> expected = new ArrayList<>();
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 100,
            LocalDate.parse("2022-10-10"), 12));
    expected.add(
        new Transaction(TransactionType.SELL, "AAA", 100,
            LocalDate.parse("2022-10-11"), 34));

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getType(), expected.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount(), EPSILON);
      assertEquals(actual.get(i).getDate(), expected.get(i).getDate());
      assertEquals(actual.get(i).getCommissionFee(), expected.get(i).getCommissionFee(), 0.000001);
    }
  }

  @Test
  public void parse_v1_inflexible() throws Exception {
    String str = "a,100\r\nb,100\r\n";
    Portfolio portfolio = portfolioParser.parse(str);
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolio.getFormat());

    List<Transaction> actual = portfolio.getTransactions();
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction("a", 100));
    expected.add(new Transaction("b", 100));

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount(), EPSILON);
    }
  }

  @Test
  public void parseTransaction_parseTypeError() throws Exception {
    String str = "2022-10-10,ABC,a,100,12";
    try {
      portfolioParser.parse(str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Transaction type is not supported.", e.getMessage());
    }
  }

  @Test
  public void parseTransaction_parseDateError() {
    String str = "10/10/2022,BUY,a,100,12";
    try {
      portfolioParser.parse(str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Text '10/10/2022' could not be parsed at index 0", e.getMessage());
    }
  }

  @Test
  public void parseTransaction_wrongArguments() {
    String str = "10/10/2022,BUY,a";
    try {
      portfolioParser.parse(str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Wrong Transaction format.", e.getMessage());
    }
  }

  @Test
  public void toString_fiveArguments() throws Exception {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(
        new Transaction(TransactionType.BUY, "a", 100, LocalDate.parse("2022-10-10"), 12));
    transactions.add(
        new Transaction(TransactionType.SELL, "a", 50, LocalDate.parse("2022-10-11"), 34));

    assertEquals("[INFO]\n"
            + "FORMAT=FLEXIBLE\n"
            + "VERSION=3\n"
            + "\n"
            + "[TRANSACTION]\n"
            + "2022-10-10,BUY,a,100.0,12.0\n"
            + "2022-10-11,SELL,a,50.0,34.0\n\n",
        portfolioParser.toString(new FlexiblePortfolio("name", transactions)));
  }

  @Test
  public void toString_twoArguments() throws Exception {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction("a", 100));
    transactions.add(
        new Transaction("b", 100));

    assertEquals("[INFO]\n"
            + "FORMAT=INFLEXIBLE\n"
            + "VERSION=3\n"
            + "\n"
            + "[TRANSACTION]\n"
            + "a,100.0\n"
            + "b,100.0\n\n",
        portfolioParser.toString(new InflexiblePortfolio("name", transactions)));
  }

  @Test
  public void parse_v3_flexible() throws Exception {
    String str = "[INFO]\r\n"
        + "FORMAT=FLEXIBLE\r\n"
        + "VERSION=3\r\n"
        + "\r\n"
        + "[SCHEDULE]\r\n"
        + "NAME=DOLLAR_COST_AVG\r\n"
        + "AMOUNT=2000\r\n"
        + "SCHEDULE=30,2012-01-01,2015-01-01\r\n"
        + "TRANSACTION_FEE=5\r\n"
        + "LAST_RUN_DATE=2022-10-17\r\n"
        + "AAPL,40\r\n"
        + "GOOGL,20\r\n"
        + "BKNG,20\r\n"
        + "NFLX,20\r\n"
        + "\r\n"
        + "[TRANSACTION]\r\n"
        + "2022-10-10,BUY,AAA,100,12.3\r\n"
        + "2022-10-11,SELL,AAA,10,4\r\n"
        + "2022-10-12,BUY,AA,123,5\r\n"
        + "\r\n";
    Portfolio portfolio = portfolioParser.parse(str);
    assertEquals(PortfolioFormat.FLEXIBLE, portfolio.getFormat());

    List<Transaction> actual = portfolio.getTransactions();
    List<Transaction> expected = new ArrayList<>();
    expected.add(
        new Transaction(TransactionType.BUY, "AAA", 100, LocalDate.parse("2022-10-10"), 12.3));
    expected.add(
        new Transaction(TransactionType.SELL, "AAA", 10, LocalDate.parse("2022-10-11"), 4));
    expected.add(
        new Transaction(TransactionType.BUY, "AA", 123, LocalDate.parse("2022-10-12"), 5));

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount(), EPSILON);
    }
  }
}
