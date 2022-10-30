package portfolio.controllers;

/**
 * FrontController handle request from the user. It receives all the action from the user and
 * dispatches them to PageController.
 */
public interface FrontController {

  /**
   * Receive action from the user, render the page and then redirect user to the next page.
   */
  void run() throws Exception;
}
