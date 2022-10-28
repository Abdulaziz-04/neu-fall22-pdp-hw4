package portfolio.controllers.external;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import portfolio.entities.DailyStockPrice;
import portfolio.entities.StockListResponse;
import portfolio.entities.StockPriceRequest;
import portfolio.entities.StockPriceResponse;

public class AlphaVantage {
  public StockPriceResponse getStockPrice(StockPriceRequest stockPriceRequest) {
    String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = stockPriceRequest.stockSymbol; //ticker symbol for Google
    URL url = null;

    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + stockSymbol + "&apikey="+apiKey+"&datatype=csv");
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
      ArrayList<DailyStockPrice> list;

      while ((b=in.read())!=-1) {
        output.append((char)b);
        System.out.println(output);
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }
    System.out.println("Return value: ");
    System.out.println(output.toString());
    return new StockPriceResponse();
  }

  public StockListResponse getStockList() {
    return new StockListResponse();
  }
}
