package portfolio.controllers;

import java.util.ArrayList;
import java.util.List;
import portfolio.entities.Portfolio;
import portfolio.entities.Stock;

public class PortfolioBuilder {

  private List<Stock> list = new ArrayList<>();

  public void init(){
    list = new ArrayList<>();
  }
  public void add(String stockSymbol, int amount){
    Stock stock = new Stock(stockSymbol,amount);
    list.add(stock);
  }

  public Portfolio result(){
    return new Portfolio(list);
  }
}
