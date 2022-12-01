package portfolio.controllers.gui;

import javax.swing.*;

import portfolio.controllers.FrontController;
import portfolio.controllers.InputHandler;

import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;
import portfolio.views.gui.SwingViewFactory;

public class SwingFrontController implements FrontController, InputHandler {

  private SwingPageController pageController;

  /**
   * Contructs the SwingFrontController for GUI first page.
   *
   * @param portfolioModel PortfolioModel
   */
  public SwingFrontController(PortfolioModel portfolioModel) {
    JFrame frame = new JFrame();
    frame.setVisible(true);
    ViewFactory viewFactory = new SwingViewFactory(frame, this);
    this.pageController = new MainPageSwingController(portfolioModel, viewFactory);
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
