package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import portfolio.models.entities.Transaction;

public interface BuySchedule {

  BuySchedule create(String name, double amount, int frequencyDays,
      LocalDate startDate,
      LocalDate endDate, double transactionFee, LocalDate lastRunDate,
      List<Transaction> buyingList);

  String getName();

  String getType();

  double getAmount();

  int getFrequencyDays();

  LocalDate getStartDate();

  LocalDate getEndDate();

  double getTransactionFee();

  LocalDate getLastRunDate();

  List<Transaction> getBuyingList();

}
