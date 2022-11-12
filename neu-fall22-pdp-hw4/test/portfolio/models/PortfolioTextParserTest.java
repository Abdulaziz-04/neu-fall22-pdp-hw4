package portfolio.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.FlexiblePortfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.models.portfolio.impl.PortfolioTextParser;

public class PortfolioTextParserTest {

  PortfolioParser portfolioParser = new PortfolioTextParser();

  @Test
  public void parseFormat_inflexible() throws Exception {
    String str = "FORMAT=INFLEXIBLE\r\na,100\r\nb,100\r\n";
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolioParser.parseFormat(str));
  }

  @Test
  public void parseFormat_flexible() throws Exception {
    String str = "FORMAT=FLEXIBLE\r\na,100\r\nb,100\r\n";
    assertEquals(PortfolioFormat.FLEXIBLE, portfolioParser.parseFormat(str));
  }

  @Test
  public void parseFormat_default() throws Exception {
    String str = "a,100\r\nb,100\r\n";
    assertEquals(PortfolioFormat.INFLEXIBLE, portfolioParser.parseFormat(str));
  }

  @Test
  public void parseTransaction_twoArguments() throws Exception {
    String str = "a,100\r\nb,100\r\n";
    List<Transaction> actual = portfolioParser.parseTransaction(str);
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction("a", 100));
    expected.add(new Transaction("b", 100));

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount());
    }
  }

  @Test
  public void parseTransaction_fourArguments() throws Exception {
    String str = "2022-10-10,BUY,a,100\r\n2022-10-11,SELL,b,100\r\n";
    List<Transaction> actual = portfolioParser.parseTransaction(str);
    List<Transaction> expected = new ArrayList<>();
    expected.add(new Transaction(TransactionType.BUY, "a", 100, LocalDate.parse("2022-10-10")));
    expected.add(new Transaction(TransactionType.SELL, "b", 100, LocalDate.parse("2022-10-11")));

    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(actual.get(i).getType(), expected.get(i).getType());
      assertEquals(actual.get(i).getSymbol(), expected.get(i).getSymbol());
      assertEquals(actual.get(i).getAmount(), expected.get(i).getAmount());
      assertEquals(actual.get(i).getDate(), expected.get(i).getDate());
    }
  }

  @Test
  public void parseTransaction_parseTypeError() throws Exception {
    String str = "2022-10-10,ABC,a,100";
    try {
      portfolioParser.parseTransaction(str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Transaction type is not supported.", e.getMessage());
    }
  }

  @Test
  public void parseTransaction_parseDateError() {
    String str = "10/10/2022,BUY,a,100";
    try {
      portfolioParser.parseTransaction(str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Text '10/10/2022' could not be parsed at index 0", e.getMessage());
    }
  }

  @Test
  public void parseTransaction_wrongArguments() {
    String str = "10/10/2022,BUY,a";
    try {
      portfolioParser.parseTransaction(str);
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Wrong Transaction format.", e.getMessage());
    }
  }

  @Test
  public void toString_fourArguments() throws Exception {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(TransactionType.BUY, "a", 100, LocalDate.parse("2022-10-10")));
    transactions.add(
        new Transaction(TransactionType.SELL, "a", 50, LocalDate.parse("2022-10-11")));

    assertEquals("FORMAT=FLEXIBLE\n2022-10-10,BUY,a,100\n2022-10-11,SELL,a,50\n",
        portfolioParser.toString(new FlexiblePortfolio(transactions)));
  }

  @Test
  public void toString_twoArguments() throws Exception {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction("a", 100));
    transactions.add(
        new Transaction("b", 100));

    assertEquals("FORMAT=INFLEXIBLE\na,100\nb,100\n",
        portfolioParser.toString(new InflexiblePortfolio(transactions)));
  }
}
