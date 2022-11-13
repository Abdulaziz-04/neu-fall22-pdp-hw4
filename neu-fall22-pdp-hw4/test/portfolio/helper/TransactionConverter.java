package portfolio.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.Transaction;

public class TransactionConverter {

  public static List<Transaction> convert(Map<String, Integer> map) {
    List<Transaction> list = new ArrayList<>();
    for (var entry: map.entrySet()) {
      list.add(new Transaction(entry.getKey(), entry.getValue()));
    }
    return list;
  }
}
