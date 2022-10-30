package portfolio.services.stockprice;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

public class AlphaVantageApi implements StockPriceApi {
  private String apiKey = "W0M1JOKC82EZEQA8";
  public Map<String, StockPrice> getStockPrice(String symbol) {
    Map<String, StockPrice> map = new HashMap<>();
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + symbol + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
          + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+symbol);
    }

    String[] result = output.toString().split("\n");

    for (int i = 1 ; i< result.length; i++) {
      String[] line = result[i].replace("\r", "").split(",");
      StockPrice entry = new StockPrice(
          Double.parseDouble(line[1]),
          Double.parseDouble(line[2]),
          Double.parseDouble(line[3]),
          Double.parseDouble(line[4]),
          Integer.parseInt(line[5])
      );
      map.put(line[0], entry);
    }

    return map;
  }

  public List<StockListEntry> getStockList() {
    List<StockListEntry> list = new ArrayList<>();
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=LISTING_STATUS"
          + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
          + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No data found" );
    }

    String[] result = output.toString().split("\n");

    for (int i = 1 ; i< result.length; i++) {
      String[] line = result[i].replace("\r", "").split(",");
      StockListEntry entry = new StockListEntry(
          line[0],
          line[1],
          line[2],
          line[3],
          line[4],
          line[6],
          line[5]
      );
      list.add(entry);
    }

    return list;
  }
}
