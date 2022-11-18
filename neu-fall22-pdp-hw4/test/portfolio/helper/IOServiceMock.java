package portfolio.helper;

import java.io.IOException;
import portfolio.controllers.datastore.IOService;

/**
 * Mock class for IOService interface.
 */
public class IOServiceMock implements IOService {

  private final ArgumentCaptor<String> argumentCaptor;

  public IOServiceMock(ArgumentCaptor<String> argumentCaptor) {
    this.argumentCaptor = argumentCaptor;
  }

  public IOServiceMock() {
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
  public boolean saveTo(String text, String path, boolean allowOverride)
      throws IllegalArgumentException {
    argumentCaptor.addArgument(text);
    if (path.equals("a.txt")) {
      return true;
    } else if (path.equals("otherioerror.txt")) {
      // Other IOException
      return false;
    } else if (!allowOverride) {
      throw new IllegalArgumentException("File already exists.");
    } else {
      throw new IllegalArgumentException("Something went wrong.");
    }
  }

}
