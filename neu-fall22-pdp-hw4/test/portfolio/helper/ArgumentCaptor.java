package portfolio.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to help capture argument calling from function.
 */
public class ArgumentCaptor<T> {

  private final List<T> arguments = new ArrayList<>();

  /**
   * Get the list of arguments.
   *
   * @return List of T
   */
  public List<T> getArguments() {
    return arguments;
  }

  /**
   * Add new argument to list.
   *
   * @param arg argument in type T
   */
  public void addArgument(T arg) {
    arguments.add(arg);
  }
}
