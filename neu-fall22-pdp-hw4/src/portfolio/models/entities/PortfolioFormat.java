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
   * This is a parse the portfolio type is flexible or inflexible
   *
   * @param str the string that we want to determine
   * @return the format for current portfolio
   * @throws Exception the format is not flexible or inflexible
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
