package portfolio.models.portfolio;

import java.util.List;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;

public interface PortfolioParser {

  /**
   *
   * @param str
   * @return
   * @throws Exception
   */
  PortfolioFormat parseFormat(String str) throws Exception;
  List<Transaction> parseTransaction(String str) throws Exception;

  /**
   * Transfer the portfolio into a string format. The format is "symbol,amount". The amount means
   * shares.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  String toString(Portfolio portfolio) throws Exception;

}

