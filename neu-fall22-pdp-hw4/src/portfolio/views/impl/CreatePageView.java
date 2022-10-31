package portfolio.views.impl;

import java.io.IOException;
import java.util.Map;

import portfolio.entities.Portfolio;
import portfolio.views.View;

public class CreatePageView implements View {

  private final Map<String, Integer> map;
  private final String errorMessage;
  Boolean isEnd;
  Boolean isNamed;

  public CreatePageView(Boolean isEnd, Boolean isNamed, Map<String, Integer> map, String errorMessage){
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.map = map;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() throws IOException {
    if (errorMessage != null){
      System.out.println(errorMessage);
    }
    System.out.println("!!! If you enter back, you will back to the main menu.");
    if(!isEnd) {
      if (map.size() > 0) {
        System.out.println("Selected stock and shares:");
        for (var entry: map.entrySet()) {
          System.out.println(entry.getKey() + " ," + entry.getValue());
        }
      }
      System.out.println("Enter symbol and number of shares for one stock. " +
              "The format is: AAPL,100.");
      System.out.println("1.The symbol must be capital letters and " +
      "the shares need to be numbers.");
      System.out.println("2.The shares cannot be 0 and " +
      "negative number.");
      System.out.println("3.Between the symbol and shares must have a comma" +
      "with no spaces.");
    } else {
      if(!isNamed) {
        System.out.println("Please enter the file name of this portfolio." +
                "The name cannot be end, back, no and yes");
      } else {
        System.out.println("Do you want to determine the total value of this portfolio?");
        System.out.println("Please enter yes if you want to determine. " +
                "Other input will be back to the main menu.");
      }
    }

  }

}
