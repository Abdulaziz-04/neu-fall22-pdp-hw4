package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import portfolio.models.portfolio.BuySchedule;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.ScheduleRunner;
import portfolio.models.stockprice.StockQueryService;

public class DollarCostAverageRunner implements ScheduleRunner {

  private final StockQueryService stockQueryService;

  public DollarCostAverageRunner(StockQueryService stockQueryService) {
    this.stockQueryService = stockQueryService;
  }

  @Override
  public List<Transaction> run(LocalDate today, BuySchedule schedule) {
    LocalDate current = schedule.getLastRunDate() == null ? schedule.getStartDate()
        .minusDays(schedule.getFrequencyDays()) : schedule.getLastRunDate();
    List<String> stocks = schedule.getBuyingList().stream().map(Transaction::getSymbol).collect(
        Collectors.toList());
    List<Transaction> transactions = new ArrayList<>();
    while (today.compareTo(current.minusDays(schedule.getFrequencyDays() - 1)) > 0) {
      LocalDate newBuy = current.plusDays(schedule.getFrequencyDays());
      current = current.plusDays(schedule.getFrequencyDays());
      Map<String, StockPrice> prices = null;

      int tryNextDay = 0;
      while (tryNextDay < 7 && tryNextDay < schedule.getFrequencyDays()) {
        try {
          prices = stockQueryService.getStockPrice(newBuy.plusDays(tryNextDay), stocks);
          break;
        } catch (Exception ignored) {
          tryNextDay += 1;
        }
      }
      if (prices == null || prices.isEmpty()) {
        continue;
      }

      for (var p : schedule.getBuyingList()) {
        double shares =
            ((p.getAmount() / 100) * schedule.getAmount()) / prices.get(p.getSymbol()).getClose();
        transactions.add(new Transaction(TransactionType.BUY, p.getSymbol(), shares, newBuy.plusDays(tryNextDay),
            schedule.getTransactionFee()));
      }
    }

    return transactions;
  }
}
