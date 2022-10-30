package portfolio.controllers;

import portfolio.views.View;

/**
 * PageController store the logic, render view and handle input of each user interface page.
 */
public interface PageController {

  /**
   * Handle user input command. Return Page enum as a next page to be directed. Return null if the page
   * will not be changed after receiving the input.
   *
   * @param command user input command as a string
   * @return next page to be redirected
   */
  PageController handleCommand(String command) throws Exception;

  View getView();
}
