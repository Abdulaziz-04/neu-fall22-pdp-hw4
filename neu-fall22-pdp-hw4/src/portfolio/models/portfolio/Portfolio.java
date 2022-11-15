package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockPrice;

/**
 * This is an interface for the portfolio class. The InflexiblePortfolio class,
 *  FlexiblePortfolio and PortfolioAbs class will implement it.
 */
public interface Portfolio {

  /**
   * Return the name of this portfolio.
   *
   * @return the name of this portfolio
   */
  String getName();

  /**
   * Return the format of this portfolio.
   *
   * @return the format of this portfolio
   */
  PortfolioFormat getFormat();

  /**
   *
   * @param transactions
   * @return
   * @throws Exception
   */
  Portfolio create(List<Transaction> transactions) throws Exception;

  /**
   * This is a method to get the composition list of a portfolio. Return a list with the
   * symbol and amount.
   *
   * @return a list with the symbol and amount.
   */
  Map<String, Integer> getComposition();

  /**
   *
   * @param date
   * @return
   */
  Map<String, Integer> getComposition(LocalDate date);

  /**
   * This is a method to return a list of transaction entry.
   *
   * @return a list of transaction entry
   */
  List<Transaction> getTransactions();

  /**
   * This is the method to get the symbol list for a portfolio.
   *
   * @return the symbol list of the portfolio.
   */
  List<String> getSymbols(LocalDate date);

  /**
   * This is the method to calculate the portfolio price on a certain date. It will return a
   * PortfolioWithValue class that has the total value of this portfolio.
   *
   * @param date   the date that we want to determine the price
   * @param prices the price for every stock in this portfolio
   * @return PortfolioWithValue object that has the total value of this portfolio
   */
  PortfolioWithValue getPortfolioWithValue(LocalDate date, Map<String, StockPrice> prices);

  double getCostBasis(LocalDate date, Map<String, StockPrice> prices) throws Exception;

  /**
   * This is a method to determine if this portfolio can only read after creating or not.
   *
   * @return true for inflexible portfolio, false for flexible portfolio
   */
  boolean isReadOnly();

}
