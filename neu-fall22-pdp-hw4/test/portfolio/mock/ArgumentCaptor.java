package portfolio.mock;

import java.util.ArrayList;
import java.util.List;

public class ArgumentCaptor {
  private List<String> arguments = new ArrayList<>();

  public List<String> getArguments(){
    return arguments;
  }

  public void addArgument(String arg){
    arguments.add(arg);
  }
}
