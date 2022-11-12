package portfolio.controllers.datastore;

import portfolio.models.portfolio.Portfolio;

public interface PortfolioParser {

  Portfolio parse(String str);

  /**
   * Transfer the portfolio into a string format. The format is "symbol,amount". The amount means
   * shares.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  String toString(Portfolio portfolio) ;

}

