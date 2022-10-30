package portfolio.controllers;

import portfolio.entities.Page;

/**
 * PageController store the logic, render view and handle input of each user interface page.
 */
public interface PageController {

  /**
   * Render component of this page.
   */
  void render();

  /**
   * Handle user input command. Return Page enum as a next page to be directed. Return null if the page
   * will not be changed after receiving the input.
   *
   * @param command user input command as a string
   * @return next page to be redirected
   */
  Page gotCommand(String command) throws Exception;
}
