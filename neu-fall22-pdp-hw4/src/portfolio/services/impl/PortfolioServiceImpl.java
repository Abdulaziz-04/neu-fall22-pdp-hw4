package portfolio.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import portfolio.entities.Portfolio;
import portfolio.services.IOService;
import portfolio.services.PortfolioService;
import portfolio.services.StockQueryService;
import portfolio.utils.PortfolioParser;

public class PortfolioServiceImpl implements PortfolioService {
  private IOService ioService;
  private StockQueryService stockQueryService;

  public PortfolioServiceImpl(IOService ioService, StockQueryService stockQueryService){
    this.ioService = ioService;
    this.stockQueryService = stockQueryService;
  }

  @Override
  public Portfolio getPortfolio(String fileName){
    try {
      String str = ioService.read(fileName);
      return PortfolioParser.parse(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean saveToFile(Portfolio portfolio, String fileName) {
    String json = PortfolioParser.toString(portfolio);
    try {
      return ioService.saveTo(json, fileName);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
