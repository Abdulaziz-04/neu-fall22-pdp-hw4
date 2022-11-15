package portfolio.models.entities;

public enum TransactionType {
  /**
   * The type is buy.
   */
  BUY,
  /**
   * The type is sell.
   */
  SELL;

  /**
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static TransactionType parse(String str) throws Exception {
    switch (str) {
      case "BUY":
        return TransactionType.BUY;
      case "SELL":
        return TransactionType.SELL;
      default:
        throw new Exception("Transaction type is not supported.");
    }
  }

  public static String toString(TransactionType txType) {
    if (txType == null) {
      return null;
    }
    switch (txType) {
      case BUY:
        return "BUY";
      case SELL:
        return "SELL";
      default:
        return null;
    }
  }

  public static int getMultiplier(TransactionType txType) {
    if (txType == null) {
      return 1;
    }
    switch (txType) {
      case BUY:
        return 1;
      case SELL:
        return -1;
      default:
        return 0;
    }
  }
};


