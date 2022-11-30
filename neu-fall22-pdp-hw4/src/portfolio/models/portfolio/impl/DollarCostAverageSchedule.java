package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.BuySchedule;

public class DollarCostAverageSchedule implements BuySchedule {

  private final String scheduleType = "dollar_cost_average";

  private final String name;
  private final int frequencyDays;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final double amount;
  private final double transactionFee;
  private final LocalDate lastRunDate;
  private final List<Transaction> buyingList;

  public DollarCostAverageSchedule(String name, double amount, int frequencyDays,
      LocalDate startDate,
      LocalDate endDate, double transactionFee, LocalDate lastRunDate,
      List<Transaction> buyingList) throws IllegalArgumentException {
    if (endDate != null && endDate.compareTo(startDate) < 0) {
      throw new IllegalArgumentException("endDate cannot be before startDate");
    }
    if (transactionFee < 0) {
      throw new IllegalArgumentException("Commission fee cannot be negative.");
    }
    if (frequencyDays <= 0) {
      throw new IllegalArgumentException("Frequency day cannot be less than zero.");
    }
    if (buyingList == null || buyingList.size() == 0) {
      throw new IllegalArgumentException("Stock buying list is empty");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount cannot be less than zero");
    }
    double total = 0;
    for (var entry : buyingList) {
      if (entry.getAmount() <= 0) {
        throw new IllegalArgumentException("Share percentage cannot be less than zero");
      }
      total += entry.getAmount();
    }
    List<Transaction> scaledBuyingList = new ArrayList<>();
    for (var entry : buyingList) {
      scaledBuyingList.add(new Transaction(entry.getSymbol(), entry.getAmount() * 100 / total));
    }
    this.name = name;
    this.amount = amount;
    this.frequencyDays = frequencyDays;
    this.startDate = startDate;
    this.endDate = endDate;
    this.transactionFee = transactionFee;
    this.lastRunDate = lastRunDate;
    this.buyingList = scaledBuyingList;
  }

  @Override
  public BuySchedule create(String name, double amount, int frequencyDays, LocalDate startDate,
      LocalDate endDate, double transactionFee, LocalDate lastRunDate,
      List<Transaction> buyingList) {
    return new DollarCostAverageSchedule(name, amount, frequencyDays, startDate, endDate, transactionFee,
        lastRunDate, buyingList);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getType() {
    return scheduleType;
  }

  @Override
  public double getAmount() {
    return amount;
  }

  @Override
  public int getFrequencyDays() {
    return frequencyDays;
  }

  @Override
  public LocalDate getStartDate() {
    return startDate;
  }

  @Override
  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public double getTransactionFee() {
    return transactionFee;
  }

  @Override
  public LocalDate getLastRunDate() {
    return lastRunDate;
  }

  @Override
  public List<Transaction> getBuyingList() {
    return Collections.unmodifiableList(buyingList);
  }
}
