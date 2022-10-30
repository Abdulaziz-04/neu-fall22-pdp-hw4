package portfolio.services;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Service interface for reading and writing string to file.
 */
public interface IOService {

  /**
   * Read string from file.
   *
   * @param path filepath
   * @return string content of the file
   */
  String read(String path) throws IOException;

  /**
   * Write string to file.
   *
   * @param text content to write
   * @param path filepath
   * @return boolean true if successfully wrote to the file otherwise false
   */
  boolean saveTo(String text, String path) throws FileNotFoundException;
}
