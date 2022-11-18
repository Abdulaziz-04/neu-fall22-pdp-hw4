package portfolio.models.entities;

/**
 * This enumerated type represents the portfolio format, which is flexible or inflexible.
 */
public enum PortfolioFormat {

  /**
   * Portfolio type.
   */
  FLEXIBLE,
  INFLEXIBLE;

  /**
   * Parse the portfolio type from string.
   *
   * @param str the string that we want to determine
   * @return the format for current portfolio
   * @throws Exception when the format is not flexible or inflexible
   */
  public static PortfolioFormat parse(String str) throws Exception {
    switch (str) {
      case "FLEXIBLE":
      case "flexible":
        return PortfolioFormat.FLEXIBLE;
      case "INFLEXIBLE":
      case "inflexible":
        return PortfolioFormat.INFLEXIBLE;
      default:
        throw new Exception("Format is not supported.");
    }
  }
}
