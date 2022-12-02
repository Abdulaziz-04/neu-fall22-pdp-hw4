package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import portfolio.models.entities.Transaction;

/**
 * Interface for schedule runner. Run the buy schedule based on the given time.
 */
public interface ScheduleRunner {

  /**
   * Return list of transaction to add in the portfolio.
   *
   * @param current LocalDate
   * @param schedule buy schedule
   * @return list of new transactions
   */
  List<Transaction> run(LocalDate current, BuySchedule schedule);
}
