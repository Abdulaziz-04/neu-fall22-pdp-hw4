package portfolio.services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IOService {
  String read(String path) throws IOException;

  boolean saveTo(String text, String path) throws FileNotFoundException;
}
