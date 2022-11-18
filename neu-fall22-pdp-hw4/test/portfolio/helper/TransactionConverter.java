package portfolio.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.Transaction;

/**
 * Helper class for transactions.
 */
public class TransactionConverter {

  /**
   * Convert map to transactions.
   *
   * @param map map
   * @return transction list
   */
  public static List<Transaction> convert(Map<String, Integer> map) {
    List<Transaction> list = new ArrayList<>();
    for (var entry : map.entrySet()) {
      list.add(new Transaction(entry.getKey(), entry.getValue()));
    }
    return list;
  }
}
