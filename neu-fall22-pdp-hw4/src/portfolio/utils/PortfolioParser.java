package portfolio.utils;
import java.util.Map;
import portfolio.entities.Portfolio;
public class PortfolioParser {

  public static Portfolio parse(String str) {

    PortfolioBuilder builder = new PortfolioBuilder();

    for (String line: str.split("\n")) {
      String[] stock = line.split(",");
      builder.add(stock[0], Integer.parseInt(stock[1]));
    }

    return builder.getResult();
  }

  public static String toString(Portfolio portfolio) {
    Map<String, Integer> map = portfolio.getStockMap();
    StringBuilder builder = new StringBuilder();

    for (Map.Entry<String,Integer> entry: map.entrySet())
    {
      builder.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
    }

    return builder.toString();
  }
}
