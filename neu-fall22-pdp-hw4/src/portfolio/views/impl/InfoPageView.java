package portfolio.views.impl;

import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioWithValue;
import portfolio.views.View;

public class InfoPageView implements View {

  private final Portfolio portfolio;
  private final PortfolioWithValue portfolioWithPrice;
  private final String errorMessage;

  public InfoPageView(Portfolio portfolio, PortfolioWithValue portfolioWithPrice, String errorMessage){
    this.portfolio = portfolio;
    this.portfolioWithPrice = portfolioWithPrice;
    this.errorMessage = errorMessage;
  }

  @Override
  public void render() {
    System.out.println("Portfolio value as of: " + portfolioWithPrice.getDate());
    for (var entry: portfolioWithPrice.getStocks()){
      String symbol = entry.getSymbol();
      int amount = entry.getAmount();
      double value = entry.getValue();
      System.out.println(symbol + "," + amount +"," + value);
    }
    System.out.println("Total value: " + portfolioWithPrice.getTotalValue());
  }
}
