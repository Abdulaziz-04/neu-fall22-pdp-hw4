package portfolio.services.cache;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @param <T>
 */
public class SimpleCache<T> implements Cache<T> {

  /**
   *
   * @param <T>
   */
  static class CacheEntry<T> {
    private final LocalTime time;
    private final T data;

    /**
     *
     * @param dateTime
     * @param data
     */
    CacheEntry( LocalTime dateTime, T data){
      this.time = dateTime;
      this.data = data;
    }

    /**
     *
     * @return
     */
    LocalTime getTime() {
      return time;
    }

    /**
     *
     * @return
     */
    T getData() {
      return data;
    }
  }

  private final int timeout;
  private final Map<String, CacheEntry<T>> map;

  /**
   *
   * @param timeout
   */
  public SimpleCache(int timeout) {
    this.timeout = timeout;
    this.map = new HashMap<>();
  }

  @Override
  public T get(String key, Function<String, T> func) {
    CacheEntry<T> entry = map.getOrDefault(key, null);

    if (entry == null
        || Math.abs(LocalTime.now().toSecondOfDay() - entry.getTime().toSecondOfDay()) > timeout) {
      T data = func.apply(key);
      CacheEntry<T> newEntry = new CacheEntry<>(LocalTime.now(), data);
      map.put(key, newEntry);
      return data;
    }
    return entry.getData();
  }

}
