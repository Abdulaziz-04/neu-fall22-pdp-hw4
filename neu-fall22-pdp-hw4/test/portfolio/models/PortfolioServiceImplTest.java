package portfolio.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import portfolio.helper.StockApiMock;
import portfolio.helper.TransactionConverter;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.impl.InflexiblePortfolio;
import portfolio.helper.ArgumentCaptor;
import portfolio.helper.IOServiceMock;
import portfolio.controllers.datastore.IOService;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.portfolio.impl.PortfolioServiceImpl;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.models.stockprice.StockQueryService;
import portfolio.models.stockprice.StockQueryServiceImpl;

/**
 * This is a test class to test PortfolioServiceImpl class.
 */
public class PortfolioServiceImplTest {

  private ArgumentCaptor argumentCaptor;
  private PortfolioService portfolioService;
  private final Map<String, Integer> map = new HashMap<>();
  private InflexiblePortfolio portfolio;

  @Before
  public void setUp() {
    argumentCaptor = new ArgumentCaptor();
    StockQueryService stockQueryService = new StockQueryServiceImpl(new StockApiMock(false));
    portfolioService = new PortfolioServiceImpl(stockQueryService, new PortfolioTextParser());

    map.put("AAPL", 100);
    map.put("AAA", 10000);
    portfolio = new InflexiblePortfolio(TransactionConverter.convert(map));
  }

  @Test
  public void getPortfolio() throws Exception {
    portfolioService.load(new IOServiceMock().read("pass.txt"));
    Portfolio actual = portfolioService.getPortfolio();
    var entries = portfolio.getTransaction();
    var actualEntries = actual.getTransaction();

    assertEquals(entries.size(), actualEntries.size());
    for (int i = 0; i < entries.size(); i++) {
      assertEquals(entries.get(i).getSymbol(), actualEntries.get(i).getSymbol());
      assertEquals(entries.get(i).getAmount(), actualEntries.get(i).getAmount());
    }
  }

}
