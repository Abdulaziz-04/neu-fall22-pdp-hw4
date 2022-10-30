package portfolio.controllers.impl;

import java.time.LocalDate;
import portfolio.controllers.PageController;
import portfolio.entities.Page;
import portfolio.entities.Portfolio;
import portfolio.entities.PortfolioValue;
import portfolio.services.PortfolioValueService;
import portfolio.services.impl.PortfolioStore;
import portfolio.views.InfoPageView;

public class InfoPageController implements PageController {
  private PortfolioValueService portfolioValueService;
  private InfoPageView infoPageView;
  private String errorMessage;
  private Portfolio portfolio;
  private PortfolioValue portfolioValue ;

  public InfoPageController(InfoPageView infoPageView, PortfolioValueService portfolioValueService, PortfolioStore portfolioStore){
    this.infoPageView = infoPageView;
    this.portfolio = portfolioStore.getPortfolio();
    this.portfolioValueService = portfolioValueService;
  }
  @Override
  public void render() {
    if (portfolioValue == null) {
      portfolioValue = portfolioValueService.getValue(portfolio, LocalDate.now().minusDays(1));
    }
    infoPageView.showPortfolioValue(portfolio, portfolioValue, errorMessage);
  }

  @Override
  public Page gotCommand(String command) throws Exception {
    try {
      portfolioValue = portfolioValueService.getValue(portfolio, LocalDate.parse(command));
      return null;
    }
    catch (Exception e){
      errorMessage = "Error! Cannot load file. Please try again.";
      return null;
    }
  }
}
