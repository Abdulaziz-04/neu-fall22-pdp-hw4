package portfolio.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Test;
import portfolio.services.datastore.IOService;
import portfolio.services.datastore.FileIOService;

public class FileIOServiceTest {

  IOService ioService = new FileIOService();

  @Test
  public void testReadWriteFile() throws IOException {
    ioService.saveTo("abc", "a.txt");
    String str = ioService.read("a.txt");
    assertEquals("abc\r\n", str);
  }

  @Test
  public void testReadFileNotExist() throws IOException {
    try {
      ioService.read("b.txt");
      fail("should fail");
    }
    catch (Exception e){
      assertEquals("java.io.FileNotFoundException: b.txt (The system cannot find the file specified)", e.getMessage());
    }
  }

}
