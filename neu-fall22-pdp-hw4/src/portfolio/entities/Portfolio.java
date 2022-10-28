package portfolio.entities;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

  List<Stock> stockList;

  public Portfolio(List<Stock> list){
    stockList = list;
  }

  List<Stock> getStockList () {
    return new ArrayList<>();
  }
}
