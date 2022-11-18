package portfolio.models.portfolio;

import java.util.List;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;

/**
 * This is an interface for portfolio parser to parse the format, transactions or portfolio into a
 * given format.
 */
public interface PortfolioParser {

  /**
   * This is a method to parse a String format portfolio format to a PortfolioFormat format.
   *
   * @param str portfolio format as a string
   * @return PortfolioFormat format of the portfolio format
   * @throws Exception when format is not supported.
   */
  PortfolioFormat parseFormat(String str) throws Exception;

  /**
   * Parse string into transactions.
   *
   * @param str string
   * @return a list of transactions
   * @throws Exception when transaction is invalid.
   */
  List<Transaction> parseTransaction(String str) throws Exception;

  /**
   * Transfer the portfolio into a string format.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  String toString(Portfolio portfolio) throws Exception;

}

