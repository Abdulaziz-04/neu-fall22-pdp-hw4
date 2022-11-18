package portfolio.models.entities;

import java.time.LocalDate;

/**
 * This is a class for portfolio transaction entry.
 */
public class Transaction {

  private final TransactionType txType;
  private final String symbol;
  private final int amount;
  private final LocalDate date;
  private final Double commissionFee;

  /**
   * This is a constructor to construct a portfolio entry, which contains the symbol and amount. The
   * amount means shares. It will initialize the type of transaction, date and commission fee to
   * null.
   *
   * @param symbol the symbol of stock
   * @param amount the share for this stock
   */
  public Transaction(String symbol, int amount) {
    this.txType = null;
    this.symbol = symbol;
    this.amount = amount;
    this.date = null;
    this.commissionFee = null;
  }

  /**
   * This is a constructor to construct a portfolio entry, which contains the type of transaction,
   * symbol, amount, transaction date, commission fee. The amount means shares.
   *
   * @param txType        the type of transaction
   * @param symbol        the symbol of a stock
   * @param amount        the shares of a stock
   * @param date          the date to do the transaction
   * @param commissionFee the commission fee
   */
  public Transaction(TransactionType txType,
      String symbol,
      int amount,
      LocalDate date,
      double commissionFee) {
    this.txType = txType;
    this.symbol = symbol;
    this.amount = amount;
    this.date = date;
    this.commissionFee = commissionFee;
  }

  /**
   * This is the method that return the transaction type.
   *
   * @return the type of transaction.
   */
  public TransactionType getType() {
    return txType;
  }

  /**
   * This is the method that return the symbol of a stock in a string format.
   *
   * @return the symbol of stock
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * This is the method that return the shares of a stock.
   *
   * @return the shares of a stock
   */
  public int getAmount() {
    return amount;
  }

  /**
   * This is the method that return the date to do the transaction.
   *
   * @return the date of the transaction
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * This is the method that return the commission fee of this transaction.
   *
   * @return the commission fee of this transaction
   */
  public Double getCommissionFee() {
    return commissionFee;
  }
}
