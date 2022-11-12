package portfolio.models.entities;

public enum TransactionType {
  BUY,
  SELL;

  public static TransactionType parse(String str) throws Exception {
    switch (str) {
      case "BUY" : return TransactionType.BUY;
      case "SELL" : return TransactionType.SELL;
      default: throw new Exception("Something went wrong");
    }
  }
};


