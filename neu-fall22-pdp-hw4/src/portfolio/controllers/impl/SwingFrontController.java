package portfolio.controllers.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;
import portfolio.controllers.FrontController;
import portfolio.controllers.PageController;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.ViewFactory;

public class SwingFrontController implements FrontController {

  private PageController pageController;
  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  /**
   * Contructs the controller.
   *
   * @param portfolioModel PortfolioModel
   * @param viewFactory    viewFactory
   */
  public SwingFrontController(PortfolioModel portfolioModel, ViewFactory viewFactory) {
    this.pageController = new MainPageController(portfolioModel, viewFactory);
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
  }

  @Override
  public void run() {
    JFrame frame = new JFrame();
    frame.add(pageController.getView().getJPanel());
    frame.setVisible(true);
    while (true) {
      // Render the page
      JPanel panel = pageController.getView().getJPanel();
      frame.setContentPane(panel);
      frame.repaint();
      frame.revalidate();

      try {
        Thread.sleep(30000);
      }
      catch (Exception e) {

      }

      pageController = pageController.handleInput("");
    }
  }
}
