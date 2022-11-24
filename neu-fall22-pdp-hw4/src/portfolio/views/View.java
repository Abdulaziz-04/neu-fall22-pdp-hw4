package portfolio.views;

import javax.swing.JPanel;

/**
 * This is an interface for storing user interface for each page.
 */
public interface View {

  /**
   * Render this view to user.
   */
  void render();

  JPanel getJPanel();
}
