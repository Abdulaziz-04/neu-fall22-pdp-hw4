package portfolio;

/**
 * EventLoop is an interface to continue running the program.
 */
public interface EventLoop {

  /**
   * Receive action from the user, render the page and then redirect user to the next page.
   */
  void run();
}
