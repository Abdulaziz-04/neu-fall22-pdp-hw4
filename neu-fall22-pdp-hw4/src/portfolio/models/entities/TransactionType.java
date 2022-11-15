package portfolio.models.entities;

public enum TransactionType {
  /**
   * The transaction type is buy.
   */
  BUY,
  /**
   * The transaction type is sell.
   */
  SELL;

  /**
   * This is a method to parse the transaction type in string format to a
   * TransactionType object.
   *
   * @param str the string that we want to parse
   * @return a TransactionType object after parsing
   * @throws Exception Transaction type is not supported.
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

  /**
   * This is a method to change the transaction type in TransactionType format to a string format.
   *
   * @param txType the transaction type in TransactionType format
   * @return the transaction type in String format
   */
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

  /**
   * @param txType
   * @return
   */
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


