package portfolio.controllers.datastore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * This is a class that represent the file service, which implements the IOService interface. This
 * class will read and write string to file.
 */
public class FileIOService implements IOService {

  @Override
  public String read(String fileName) throws IOException {
    String s;
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
      }
      s = sb.toString();
    }
    return s;
  }

  @Override
  public boolean saveTo(String text, String fileName, boolean allowOverride) throws IOException {
    File f = new File(fileName);
    if (!allowOverride && f.exists() && !f.isDirectory()) {
      throw new IOException(
              "There is a file or a directory exists with filename: " + fileName);
    }
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
      writer.write(text);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
