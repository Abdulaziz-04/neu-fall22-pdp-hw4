package portfolio.views.impl;

import java.util.Map;
import java.util.Map.Entry;
import portfolio.views.CreateMenuView;

public class CreateMenuViewImpl implements CreateMenuView {

  public void print(Map<String, Integer> map, String errorMessage) {
    System.out.println("--This is Create the portfolios interface--");

    if (map.size() > 0) {
      System.out.println("Selected stock and shares:");
      for (Entry entry: map.entrySet()) {
        System.out.println(entry.getKey() + " ," + entry.getValue());
      }

    }
    if (errorMessage != null){
      System.out.println(errorMessage);
    }
    System.out.println("Enter symbol, number of shares. EX: AAPL,100");
  }

}
