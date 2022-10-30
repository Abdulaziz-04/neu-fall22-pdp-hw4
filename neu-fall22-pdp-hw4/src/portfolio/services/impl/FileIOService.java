package portfolio.services.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import portfolio.services.IOService;

public class FileIOService implements IOService {
  @Override
  public String read(String fileName) {
    String s;
    try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
      }
      s = sb.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return s;
  }

  @Override
  public boolean saveTo(String text, String fileName) {
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
      writer.write(text);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
