package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;

/**
 * This is an interface for Portfolio model.
 */
public interface PortfolioModel {

  /**
   * To initialize the model of portfolio.
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
   * @param name the name of this portfolio
   * @param format the format of this portfolio
   * @param transactions the list of transaction
   * @return a portfolio that we want to modify
   * @throws Exception inflexible portfolio cannot modify
   */
  Portfolio create(String name, PortfolioFormat format, List<Transaction> transactions)
      throws Exception;

  void set(String name, PortfolioFormat format, List<Transaction> transactions)
      throws Exception;

  void load(String name, String text) throws Exception;

  void addTransactions(List<Transaction> newTransactions) throws Exception;

  PortfolioWithValue getValue(LocalDate date) throws Exception;

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
}
