package portfolio.views.impl;

import java.util.Map;
import java.util.Map.Entry;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioValue;
import portfolio.views.InfoPageView;

public class InfoPageViewImpl implements InfoPageView {

  @Override
  public void showPortfolioValue(Portfolio portfolio, PortfolioValue portfolioValue, String errorMessage) {

    Map<String, Double> priceMap = portfolioValue.getPriceMap();

    System.out.println("Portfolio value as of: " + portfolioValue.getDate());
    double total = 0;
    for (Entry<String, Integer> entry: portfolio.getStockMap().entrySet()
    ) {
      String symbol = entry.getKey();
      Integer amount = entry.getValue();
      double value = priceMap.get(symbol) * amount;
      System.out.println(symbol + "," + amount +"," + value);
      total = total + value;
    }
    System.out.println("Total value: " + total);
  }
}
