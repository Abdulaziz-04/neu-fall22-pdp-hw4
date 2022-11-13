package portfolio.models.cache;

import java.util.function.Function;

/**
 * Service interface for generic cache.
 */
public interface Cache<T> {

  /**
   * Get value from cache. If there is a value for the key in the cache, the cache will return that
   * value, otherwise it will create a new entry with a new value retrieved from Function func.
   *
   * @param key  key as a string
   * @param func get function
   * @return value as type T
   */
  T get(String key, Function<String, T> func);

}
