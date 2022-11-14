package portfolio.controllers.datastore;

import java.io.IOException;

/**
 * Service interface for reading and writing string to external storage.
 */
public interface IOService {

  /**
   * Read string from path.
   *
   * @param path filepath
   * @return string content of the file
   * @throws IOException if path is not valid
   */
  String read(String path) throws IOException;

  /**
   * Write string to path.
   *
   * @param text content to write
   * @param path filepath
   * @return boolean true if successfully wrote to the file otherwise false
   * @throws IllegalArgumentException if path is not valid
   */
  boolean saveTo(String text, String path, boolean allowOverride) throws IllegalArgumentException;
}
