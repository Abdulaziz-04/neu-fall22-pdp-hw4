package portfolio.controllers;

/**
 * Interface for callback send to View.
 */
public interface InputHandler {

  /**
   * Handle input from user.
   *
   * @param input string input
   */
  void handleInput(String input);

}
