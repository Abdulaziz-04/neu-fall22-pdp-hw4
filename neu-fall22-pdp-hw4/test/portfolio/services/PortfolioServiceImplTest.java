package portfolio.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.entities.Portfolio;
import portfolio.mock.ArgumentCaptor;
import portfolio.mock.IOServiceMock;
import portfolio.services.datastore.IOService;
import portfolio.services.portfolio.PortfolioService;
import portfolio.services.portfolio.PortfolioServiceImpl;

public class PortfolioServiceImplTest {

  private ArgumentCaptor argumentCaptor;
  private PortfolioService portfolioService;
  private final Map<String, Integer> map = new HashMap<>();
  private Portfolio portfolio;

  @Before
  public void setUp() {
    argumentCaptor = new ArgumentCaptor();
    IOService ioService = new IOServiceMock(argumentCaptor);
    portfolioService = new PortfolioServiceImpl(ioService);

    map.put("AAPL", 100);
    map.put("AAA", 10000);
    portfolio = new Portfolio(map);
  }

  @Test
  public void getPortfolio() throws IOException {
    Portfolio actual = portfolioService.getPortfolio("pass.txt");
    var entries = portfolio.getStocks();
    var actualEntries = actual.getStocks();

    assertEquals(entries.size(), actualEntries.size());
    for (int i = 0; i < entries.size(); i++) {
      assertEquals(entries.get(i).getSymbol(), actualEntries.get(i).getSymbol());
      assertEquals(entries.get(i).getAmount(), actualEntries.get(i).getAmount());
    }
  }

  @Test
  public void getPortfolio_fileNotFound() {
    try {
      portfolioService.getPortfolio("abc.txt");
      fail("should fail");
    } catch (IOException e) {
      assertEquals("file not found.", e.getMessage());
    }
  }

  @Test
  public void getPortfolio_parseFail() {
    try {
      portfolioService.getPortfolio("parsefail.txt");
      fail("should fail");
    } catch (IOException e) {
      assertEquals("Cannot read portfolio. It may have a wrong format.", e.getMessage());
    }
  }

  @Test
  public void saveTo() {
    boolean result = portfolioService.saveToFile(portfolio, "a.txt");
    assertEquals("AAA,10000\n" + "AAPL,100\n", argumentCaptor.getArguments().get(0));
    assertTrue(result);
  }

  @Test
  public void saveTo_ioError() {
    boolean result = portfolioService.saveToFile(portfolio, "otherioerror.txt");
    assertFalse(result);
  }

  @Test
  public void saveTo_fileExists() {
    try {
      portfolioService.saveToFile(portfolio, "abc.txt");
      fail("should fail");
    }
    catch (IllegalArgumentException e) {
      assertEquals("File already exists.", e.getMessage());
    }
  }

}
