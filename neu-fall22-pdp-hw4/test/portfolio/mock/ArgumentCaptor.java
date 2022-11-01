package portfolio.mock;

import java.util.ArrayList;
import java.util.List;

public class ArgumentCaptor<T> {
  private final List<T> arguments = new ArrayList<>();

  public List<T> getArguments(){
    return arguments;
  }

  public void addArgument(T arg){
    arguments.add(arg);
  }
}
