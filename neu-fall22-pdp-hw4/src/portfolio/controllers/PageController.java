package portfolio.controllers;

import portfolio.views.View;

/**
 * PageController store the logic, render view and handle input of each user interface page.
 */
public interface PageController {

  /**
   * Handle user input. Return Page enum as a next page to be directed. Return null if the page
   * will not be changed after receiving the input.
   *
   * @param input user input as a string
   * @return next page to be redirected
   * @throws Exception some errors in different page
   */
  PageController handleInput(String input) ;

  /**
   * Return the view that want to show.
   * @return the view that want to show.
   */
  View getView();
}
