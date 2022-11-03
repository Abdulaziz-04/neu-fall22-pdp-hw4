package portfolio.services.cache;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * SimpleCache implements Cache. The cache compares last storing time of each entry. If the cache
 * gets a query with the new time that is more than specified timeout duration. It will update the
 * entry with new value retrieved from specified source.
 *
 * @param <T> a type
 */
public class SimpleCache<T> implements Cache<T> {

  static class CacheEntry<T> {

    private final LocalTime time;
    private final T data;

    CacheEntry(LocalTime dateTime, T data) {
      this.time = dateTime;
      this.data = data;
    }

    LocalTime getTime() {
      return time;
    }

    T getData() {
      return data;
    }
  }

  private final int timeout;
  private final Map<String, CacheEntry<T>> map;

  /**
   * This is a constructor to construct a simple cache object with the timeout duration.
   *
   * @param timeout the duration for getting new data
   */
  public SimpleCache(int timeout) {
    this.timeout = timeout;
    this.map = new HashMap<>();
  }

  /**
   * Get value from cache. If there is a value for the key in the cache and within the timeout
   * duration, the cache will return that value, otherwise it will create a new entry with a new
   * value retrieved from Function func.
   *
   * @param key  key as a string
   * @param func get function
   * @return value as type T
   */
  @Override
  public T get(String key, Function<String, T> func) {
    return get(key, func, LocalTime.now());
  }

  /**
   * Get value from cache by comparing with input time instead of using current time. If there is a
   * value for the key in the cache and within the timeout, the cache will return that value,
   * otherwise it will create a new entry with a new value retrieved from Function func.
   *
   * @param key  key as a string
   * @param func get function
   * @param time input time as LocalTime
   * @return value as type T
   */
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
