package portfolio.mock;

import java.io.IOException;
import portfolio.services.datastore.IOService;

public class IOServiceMock implements IOService {

  private final ArgumentCaptor<String> argumentCaptor;
  public IOServiceMock(ArgumentCaptor<String> argumentCaptor){
    this.argumentCaptor = argumentCaptor;
  }

  public IOServiceMock(){
    this.argumentCaptor = new ArgumentCaptor<>();
  }

  @Override
  public String read(String path) throws IOException {
    if (path.equals("pass.txt")) {
      return "AAPL,100\n" + "AAA,10000";
    } else if (path.equals("parsefail.txt")) {
      return "AAPL";
    } else {
      throw new IOException("file not found.");
    }
  }

  @Override
  public boolean saveTo(String text, String path) throws IllegalArgumentException {
    argumentCaptor.addArgument(text);
    if (path.equals("a.txt")) {
      return true;
    } else if (path.equals("otherioerror.txt")) {
      // Other IOException
      return false;
    } else {
      throw new IllegalArgumentException("File already exists.");
    }
  }

}
