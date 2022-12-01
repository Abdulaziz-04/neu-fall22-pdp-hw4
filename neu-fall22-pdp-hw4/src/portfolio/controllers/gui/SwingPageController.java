package portfolio.controllers.gui;

import portfolio.views.View;

/**
 * SwingPageController store the logic, create view and handle action command send from each user
 * interface page.
 */
public interface SwingPageController {

  /**
   * Handle user input. Return PageController as a next page that user will be directed to. Return
   * itself if the page will not be changed after receiving the input.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  SwingPageController handleInput(String input);

  /**
   * Return the view to be showed in the UI.
   *
   * @return View object.
   */
  View getView();
}
