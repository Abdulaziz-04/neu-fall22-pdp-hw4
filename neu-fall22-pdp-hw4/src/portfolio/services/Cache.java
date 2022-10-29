package portfolio.services;

import java.util.function.Function;

public interface Cache<T>{
  T get(String key, Function<String, T> func);
}
