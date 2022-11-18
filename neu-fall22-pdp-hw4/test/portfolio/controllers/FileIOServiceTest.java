package portfolio.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import portfolio.controllers.datastore.IOService;
import portfolio.controllers.datastore.FileIOService;

/**
 * This is a test class to test FileIOService class.
 */
public class FileIOServiceTest {

  IOService ioService = new FileIOService();

  @Before
  public void setUp() throws IOException {
    File file = new File("a.txt");

    if (file.exists()) {
      file.delete();
    }

    File file2 = new File("b.txt");
    if (!file.exists()) {
      file2.createNewFile();
    }

  }

  @Test
  public void testReadWriteFile() throws IOException {
    ioService.saveTo("abc", "a.txt", false);
    String str = ioService.read("a.txt");
    assertEquals("abc\r\n", str);
  }

  @Test
  public void testWriteFileWithSameName() {
    try {
      ioService.saveTo("abc", "b.txt", false);
      fail("should throw exception");
    } catch (IOException e) {
      assertEquals("There is a file or a directory exists with filename: b.txt", e.getMessage());
    }
  }

  @Test
  public void testReadFileNotExist() {
    try {
      ioService.read("c.txt");
      fail("should fail");
    } catch (Exception e) {
      assertEquals("c.txt (The system cannot find the file specified)", e.getMessage());
    }
  }

}
