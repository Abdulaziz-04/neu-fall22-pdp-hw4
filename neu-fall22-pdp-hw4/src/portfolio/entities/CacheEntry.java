package portfolio.entities;

import java.time.LocalTime;

public class CacheEntry<T> {
  private LocalTime time;
  private T data;

  public CacheEntry( LocalTime dateTime, T data){
    this.time = dateTime;
    this.data = data;
  }

  public LocalTime getTime() {
    return time;
  }

  public T getData() {
    return data;
  }
}
