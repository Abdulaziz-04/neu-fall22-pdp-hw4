package portfolio.controllers;

import portfolio.entities.Portfolio;

public class PortfolioController {

  public Portfolio getPortfolio(String fileName) throws Exception {
    String json = FileIO.read(fileName);
    return PortfolioParser.parse(json);
  }

  public int saveToFile(Portfolio portfolio, String fileName) throws Exception {
    String json = PortfolioParser.toJson(portfolio);
    return FileIO.saveTo(json, fileName);
  }

}
