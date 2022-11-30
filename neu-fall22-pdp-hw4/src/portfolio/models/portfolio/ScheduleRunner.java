package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import portfolio.models.entities.Transaction;

public interface ScheduleRunner {

  List<Transaction> run(LocalDate current, BuySchedule schedule);
}
