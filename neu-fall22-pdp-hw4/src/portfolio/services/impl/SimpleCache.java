package portfolio.services.impl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import portfolio.entities.CacheEntry;
import portfolio.services.Cache;

public class SimpleCache<T> implements Cache<T> {

  private final int timeout;
  private final Map<String, CacheEntry<T>> map;

  public SimpleCache(int timeout) {
    this.timeout = timeout;
    this.map = new HashMap<>();
  }

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
