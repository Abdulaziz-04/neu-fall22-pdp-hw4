package portfolio.services.portfolio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioEntry;
import portfolio.services.datastore.IOService;

/**
 *
 */
public class PortfolioServiceImpl implements PortfolioService {
  private IOService ioService;

  public PortfolioServiceImpl(IOService ioService){
    this.ioService = ioService;
  }

  @Override
  public Portfolio getPortfolio(String fileName){
    try {
      String str = ioService.read(fileName);
      return parse(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean saveToFile(Portfolio portfolio, String fileName) {
    String str = toString(portfolio);
    try {
      return ioService.saveTo(str, fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  private static Portfolio parse(String str) {

    Map<String, Integer> map = new HashMap<>();

    for (String line: str.split("\n")) {
      line = line.replace("\r", "");
      String[] stock = line.split(",");
      map.put(stock[0], map.getOrDefault(stock[0], 0) + Integer.parseInt(stock[1]));
    }

    return new Portfolio(map);
  }

  private static String toString(Portfolio portfolio) {
    List<PortfolioEntry> stocks = portfolio.getStocks();
    StringBuilder builder = new StringBuilder();

    for (var entry: stocks)
    {
      builder.append(entry.getSymbol()).append(",").append(entry.getAmount()).append("\n");
    }

    return builder.toString();
  }
}
