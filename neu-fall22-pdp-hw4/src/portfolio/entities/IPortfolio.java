package portfolio.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * This is an interface for the portfolio class. The portfolio class will implement this class.
 */
public interface IPortfolio {

  /**
   * This is a method to get the portfolio list. Return a list with the portfolio entry.
   *
   * @return a list with the portfolio entry.
   */
  List<PortfolioEntry> getStocks();

  /**
   * This is the method to get the symbol list for a portfolio.
   *
   * @return the symbol list of the portfolio.
   */
  List<String> getSymbols();

  /**
   * This is the method to calculate the portfolio price on a certain date. It will return
   * a PortfolioWithValue class that has the total value of this portfolio.
   *
   * @param date the date that we want to determine the price
   * @param prices the price for every stock in this portfolio
   * @return PortfolioWithValue object that has the total value of this portfolio
   */
  PortfolioWithValue getPortfolioWithPrice(LocalDate date, Map<String, StockPrice> prices);

}
