package portfolio.controllers.impl;

import javax.swing.JFrame;
import portfolio.controllers.FrontController;
import portfolio.controllers.InputHandler;
import portfolio.controllers.PageController;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;
import portfolio.views.gui.SwingViewFactory;

public class SwingFrontController implements FrontController, InputHandler {

  private PageController pageController;
  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  /**
   * Contructs the controller.
   *
   * @param portfolioModel PortfolioModel
   */
  public SwingFrontController(PortfolioModel portfolioModel) {
    this.portfolioModel = portfolioModel;
    JFrame frame = new JFrame();
    frame.setVisible(true);
    this.viewFactory = new SwingViewFactory(frame, this);
    this.pageController = new MainPageController(portfolioModel, viewFactory);
  }

  @Override
  public void handleInput(String input) {
    pageController = pageController.handleInput(input);
    View view = pageController.getView();
    view.render();
  }

  @Override
  public void run() {
    View view = pageController.getView();
    view.render();
  }
}
