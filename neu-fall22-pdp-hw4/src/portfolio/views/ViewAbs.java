package portfolio.views;

import java.io.PrintStream;

/**
 * This is an abstract class for CreatePageView, InfoPageView, LoadPageView and MainPageView,
 * which is implement the View interface.
 */
public abstract class ViewAbs implements View {

  protected final PrintStream printStream;

  /**
   * This is a constructor to construct a ViewAbs object,
   * which will initialize the output stream.
   */
  public ViewAbs() {
    this.printStream = System.out;
  }

  /**
   * This is a constructor to construct a ViewAbs object. It will initialize the output stream
   * to the printStream passed to it.
   *
   * @param printStream
   */
  public ViewAbs(PrintStream printStream) {
    this.printStream = printStream;
  }

  /**
   * This is the function to render different pages.
   */
  public abstract void render();

  /**
   * This is a function that we will clean the console.
   */
  protected static void clearConsole() {
    try {
      final String os = System.getProperty("os.name");
      if (os.contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        Runtime.getRuntime().exec("clear");
      }
    } catch (final Exception ignored) {
    }
  }
}
