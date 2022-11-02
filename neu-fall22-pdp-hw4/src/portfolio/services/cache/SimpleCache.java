package portfolio.services.cache;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is a class that represent a simple cache.
 *
 * @param <T> a type
 */
public class SimpleCache<T> implements Cache<T> {

  /**
   * This is the inner class for cache entry, which contains the local time and the data in
   * this cache entry.
   *
   * @param <T> a type
   */
  static class CacheEntry<T> {
    private final LocalTime time;
    private final T data;

    /**
     * This is a constructor to construct a cache entry, which contains the time and data
     * stored in it.
     *
     * @param dateTime the time for date
     * @param data the data is stored in it
     */
    CacheEntry( LocalTime dateTime, T data){
      this.time = dateTime;
      this.data = data;
    }

    /**
     * Return the time for this cache entry.
     *
     * @return the time for this cache entry
     */
    LocalTime getTime() {
      return time;
    }

    /**
     * Return the data which is stored in this cache entry.
     *
     * @return the data which is stored in this cache entry
     */
    T getData() {
      return data;
    }
  }

  private final int timeout;
  private final Map<String, CacheEntry<T>> map;

  /**
   * This is a constructor to construct a simple cache object, which contains the limited
   * time and the map.
   *
   * @param timeout the limited time for get data
   */
  public SimpleCache(int timeout) {
    this.timeout = timeout;
    this.map = new HashMap<>();
  }

  @Override
  public T get(String key, Function<String, T> func) {
    return get(key, func, LocalTime.now());
  }

  @Override
  public T get(String key, Function<String, T> func, LocalTime time) {
    CacheEntry<T> entry = map.getOrDefault(key, null);

    if (entry == null
        || Math.abs(time.toSecondOfDay() - entry.getTime().toSecondOfDay()) > timeout) {
      T data = func.apply(key);
      CacheEntry<T> newEntry = new CacheEntry<>(LocalTime.now(), data);
      map.put(key, newEntry);
      return data;
    }
    return entry.getData();
  }

}
