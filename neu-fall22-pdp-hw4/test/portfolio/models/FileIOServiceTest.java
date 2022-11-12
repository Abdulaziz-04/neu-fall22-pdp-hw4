package portfolio.models;

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
    ioService.saveTo("abc", "a.txt");
    String str = ioService.read("a.txt");
    assertEquals("abc\r\n", str);
  }

  @Test
  public void testWriteFileWithSameName() throws IOException {
    try {
      ioService.saveTo("abc", "b.txt");
      fail("should throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("There is a file or a directory exists with filename: b.txt", e.getMessage());
    }
  }

  @Test
  public void testReadFileNotExist() throws IOException {
    try {
      ioService.read("c.txt");
      fail("should fail");
    } catch (Exception e) {
      assertEquals("c.txt (The system cannot find the file specified)", e.getMessage());
    }
  }

}
