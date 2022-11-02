package portfolio.services.portfolio;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntry;
import portfolio.services.datastore.IOService;

/**
 * This is a class that represent a portfolio service, which creating and retrieving portfolio.
 */
public class PortfolioServiceImpl implements PortfolioService {

  private final IOService ioService;

  /**
   * This is a constructor that to construct a portfolio service object. It will initialize io
   * service for portfolio service.
   *
   * @param ioService the service to read or write string to the external storage
   */
  public PortfolioServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Portfolio getPortfolio(String fileName) throws IOException {
    String str = ioService.read(fileName);
    try {
      return parse(str);
    } catch (Exception e) {
      throw new IOException("Cannot read portfolio. It may have a wrong format.");
    }
  }

  @Override
  public boolean saveToFile(Portfolio portfolio, String fileName) throws IllegalArgumentException {
    String str = toString(portfolio);
    return ioService.saveTo(str, fileName);
  }

  private static Portfolio parse(String str) {

    Map<String, Integer> map = new HashMap<>();

    for (String line : str.split("\n")) {
      line = line.replace("\r", "");
      String[] stock = line.split(",");
      map.put(stock[0], map.getOrDefault(stock[0], 0) + Integer.parseInt(stock[1]));
    }

    return new Portfolio(map);
  }

  /**
   * Transfer the portfolio into a string format. The format is "symbol,amount". The amount means
   * shares.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  private static String toString(Portfolio portfolio) {
    List<PortfolioEntry> stocks = portfolio.getStocks();
    StringBuilder builder = new StringBuilder();

    for (var entry : stocks) {
      builder.append(entry.getSymbol()).append(",").append(entry.getAmount()).append("\n");
    }

    return builder.toString();
  }
}
