package portfolio.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalTime;
import org.junit.Test;
import portfolio.models.cache.SimpleCache;

/**
 * This is a test class to test SimpleCache class.
 */
public class SimpleCacheTest {

  private final SimpleCache<Integer> cache = new SimpleCache<>(1);

  int func(int x) {
    return x;
  }

  int failFunc(int x) throws RuntimeException {
    throw new RuntimeException("Something fail");
  }

  @Test
  public void get() throws Exception {
    assertEquals(1, (int) cache.get("key", x -> func(1)));
    assertEquals(1, (int) cache.get("key", x -> func(2)));
    Thread.sleep(2000);
    assertEquals(3, (int) cache.get("key", x -> func(3)));
  }

  @Test
  public void getWithTime() throws Exception {
    LocalTime time = LocalTime.now();
    assertEquals(1, (int) cache.get("key", x -> func(1), time));
    assertEquals(1, (int) cache.get("key", x -> func(2), time.plusSeconds(1)));
    assertEquals(3, (int) cache.get("key", x -> func(3), time.plusSeconds(2)));
  }

  @Test
  public void get_fail() {
    try {
      cache.get("key", x -> failFunc(1));
      fail("should fail");
    } catch (Exception e) {
      assertEquals("Something fail", e.getMessage());
    }
  }
}
