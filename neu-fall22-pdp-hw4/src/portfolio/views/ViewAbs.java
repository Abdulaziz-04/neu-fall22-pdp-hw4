package portfolio.views;

import java.io.PrintStream;

public abstract class ViewAbs implements View {
  protected final PrintStream printStream;

  public ViewAbs(){
    this.printStream = System.out;
  }

  public ViewAbs(PrintStream printStream){
    this.printStream = printStream;
  }

  public abstract void render();

  protected abstract String getConstructor();
}
