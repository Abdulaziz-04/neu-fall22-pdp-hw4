package portfolio.views;

import java.io.PrintStream;

public abstract class ViewAbs implements View {

  protected final PrintStream printStream;

  public ViewAbs() {
    this.printStream = System.out;
  }

  public ViewAbs(PrintStream printStream) {
    this.printStream = printStream;
  }

  public abstract void render();

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
