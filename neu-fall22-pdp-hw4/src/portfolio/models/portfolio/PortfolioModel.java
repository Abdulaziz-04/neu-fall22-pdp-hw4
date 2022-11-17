package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioPerformance;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;

/**
 * This is an interface for Portfolio model.
 */
public interface PortfolioModel {

  void init() throws Exception;

  /**
   * Return the portfolio that we want to get.
   *
   * @return the portfolio that we want to get.
   */
  Portfolio getPortfolio();

  /**
   * This is a method to modify a portfolio.
   *
   * @param name the name of this portfolio
   * @param format the format of this portfolio
   * @param transactions the list of transaction
   * @return a portfolio that we want to modify
   * @throws Exception inflexible portfolio cannot modify
   */
  Portfolio create(String name, PortfolioFormat format, List<Transaction> transactions)
      throws Exception;

  void load(String name, String text) throws Exception;

  boolean checkTransaction(LocalDate date, String symbol) throws Exception;

  void checkTransactions(List<Transaction> transactions) throws Exception;
  /**
   * This is a method to add the transactions to a flexible portfolio.
   *
   * @param transactions the new transactions
   * @throws Exception the portfolio is inflexible
   */
  void addTransactions(List<Transaction> transactions) throws Exception;

  /**
   * This is a method to get the portfolio with value on a certain date.
   *
   * @param date the date to get the value
   * @return PortfolioWithValue object that have the value on that date
   * @throws Exception
   */
  PortfolioWithValue getValue(LocalDate date) throws Exception;

  /**
   * This is a method to get the cost of basis of a portfolio on a certain date.
   *
   * @param date the date that we want to get
   * @return the cost of basis in a double format
   * @throws Exception
   */
  double getCostBasis(LocalDate date) throws Exception;

  /**
   * This is a method that to get the value of a portfolio from a date to another date.
   *
   * @param from the start date to get the value
   * @param to the end date to get the value
   * @return a map that contain the value from "from" to "to" date
   * @throws Exception is there some error
   */
  Map<LocalDate, Double> getValues(LocalDate from, LocalDate to) throws Exception;

  PortfolioPerformance getPerformance(LocalDate from, LocalDate to);

  String getString() throws Exception;
}
