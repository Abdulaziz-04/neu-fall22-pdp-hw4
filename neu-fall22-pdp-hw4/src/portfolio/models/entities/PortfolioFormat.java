package portfolio.models.entities;

public enum PortfolioFormat {
  FLEXIBLE,
  INFLEXIBLE;

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
