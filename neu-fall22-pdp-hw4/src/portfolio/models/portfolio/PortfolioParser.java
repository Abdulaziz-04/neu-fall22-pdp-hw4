package portfolio.models.portfolio;

/**
 * This is an interface for portfolio parser to parse the format, transactions or portfolio into a
 * given format.
 */
public interface PortfolioParser {
  Portfolio parse(String str) throws Exception;

  /**
   * Transfer the portfolio into a string format.
   *
   * @param portfolio the portfolio that we want to transfer
   * @return the portfolio in a string format
   */
  String toString(Portfolio portfolio) throws Exception;

}

