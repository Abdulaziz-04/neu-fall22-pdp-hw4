package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioPerformance;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;

/**
 * This is an interface for Portfolio model which include all method for portfolio.
 */
public interface PortfolioModel {

  /**
   * Initialize the model of portfolio.
   *
   * @throws Exception is there any error
   */
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
   * @param name         the name of this portfolio
   * @param format       the format of this portfolio
   * @param transactions the list of transaction
   * @return a portfolio that we want to modify
   * @throws Exception inflexible portfolio cannot modify
   */
  Portfolio create(String name, PortfolioFormat format, List<Transaction> transactions)
      throws Exception;

  void load(String name, String text) throws Exception;

  /**
   * This is a method to check the transaction for a stock on a certain date is valid or not.
   *
   * @param date the date to check
   * @param symbol the symbol to check
   *
   * @return true for valid, false for invalid
   * @throws Exception excetion error
   */
  boolean checkTransaction(LocalDate date, String symbol) throws Exception;

  /**
   * This is a method to check the transaction list is valid or not.
   *
   * @param transactions the transaction list
   * @throws Exception not valid
   */
  void checkTransactions(List<Transaction> transactions) throws Exception;

  /**
   * This is a method to add the transactions to a flexible portfolio.
   *
   * @param transactions the new transactions
   * @throws Exception the portfolio is inflexible
   */
  void addTransactions(List<Transaction> transactions) throws Exception;

  void addSchedule(String name, double amount, int frequencyDays,
      LocalDate startDate,
      LocalDate endDate, double transactionFee, LocalDate lastRunDate,
      List<Transaction> buyingList) throws Exception;

  void modifySchedule(String name, double amount, int frequencyDays,
      LocalDate startDate,
      LocalDate endDate, double transactionFee, LocalDate lastRunDate,
      List<Transaction> buyingList) throws Exception;

  /**
   * This is a method to get the portfolio with value on a certain date.
   *
   * @param date the date to get the value
   * @return PortfolioWithValue object that have the value on that date
   * @throws Exception when date is not valid
   */
  PortfolioWithValue getValue(LocalDate date) throws Exception;

  /**
   * This is a method to get the cost of basis of a portfolio on a certain date.
   *
   * @param date the date that we want to get
   * @return the cost of basis in a double format
   * @throws Exception when cost of basis is not available
   */
  double getCostBasis(LocalDate date) throws Exception;

  /**
   * This is a method that to get the value of a portfolio from a date to another date.
   *
   * @param from the start date to get the value
   * @param to   the end date to get the value
   * @return a map that contain the value from "from" to "to" date
   * @throws Exception is there some error
   */
  Map<LocalDate, Double> getValues(LocalDate from, LocalDate to) throws Exception;

  /**
   * This is a method to the performance for a portfolio on a certain timespan.
   *
   * @param from the start date
   * @param to the end date
   * @return the PortfolioPerformance entity
   */
  PortfolioPerformance getPerformance(LocalDate from, LocalDate to);

  /**
   * To get the string of a portfolio by using the portfolio parser.
   * @return
   * @throws Exception
   */
  String getString() throws Exception;
}
