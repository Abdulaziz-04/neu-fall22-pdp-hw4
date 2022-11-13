package portfolio.models.entities;

import java.time.LocalDate;

/**
 * This is a class for portfolio entry.
 */
public class Transaction {

  private final TransactionType txType;
  private final String symbol;
  private final int amount;
  private final LocalDate date;
  private final Double commissionFee;

  /**
   * This is a constructor to construct a portfolio entry, which contains the symbol and amount. The
   * amount means shares.
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

  public Transaction(TransactionType txType, String symbol, int amount, LocalDate date, double commissionFee) throws Exception {
    this.txType = txType;
    this.symbol = symbol;
    this.amount = amount;
    this.date = date;
    this.commissionFee = commissionFee;
  }

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

  public LocalDate getDate() {
    return date;
  }
  public Double getCommissionFee(){
    return commissionFee;
  }
}
