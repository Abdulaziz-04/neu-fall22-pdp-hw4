package portfolio.views.impl;

import java.util.Map;
import portfolio.views.View;

public class CreatePageView implements View {

  private final Map<String, Integer> map;
  private final String errorMessage;

  public CreatePageView(Map<String, Integer> map, String errorMessage){
    this.map = map;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() {
    System.out.println("--This is Create the portfolios interface--");

    if (map.size() > 0) {
      System.out.println("Selected stock and shares:");
      for (var entry: map.entrySet()) {
        System.out.println(entry.getKey() + " ," + entry.getValue());
      }
    }
    if (errorMessage != null){
      System.out.println(errorMessage);
    }
    System.out.println("Enter symbol, number of shares. EX: AAPL,100");
  }

}
