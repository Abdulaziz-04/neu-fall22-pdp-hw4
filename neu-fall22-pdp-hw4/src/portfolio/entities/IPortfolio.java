package portfolio.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPortfolio {

  List<PortfolioEntry> getStocks();

  List<String> getSymbols();

  PortfolioWithValue getPortfolioWithPrice(LocalDate date, Map<String, StockPrice> prices);

}
