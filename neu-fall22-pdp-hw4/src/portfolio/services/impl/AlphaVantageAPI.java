package portfolio.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import portfolio.entities.StockListEntry;
import portfolio.entities.StockPrice;

public class AlphaVantageAPI{
  private String apiKey = "W0M1JOKC82EZEQA8";

  public StockPrice getStockPrice(LocalDate date, String symbol) {
    return null;
  }

  public Map<String, StockPrice> getStockPrice(String symbol) {

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
        System.out.println("A: " + output);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+symbol);
    }
    System.out.println("Return value: ");
    System.out.println(output.toString());
    return new HashMap<>();
  }

  public List<StockListEntry> getStockList() {
    return new ArrayList<>();
  }
}
