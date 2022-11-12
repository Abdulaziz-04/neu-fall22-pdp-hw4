package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.List;
import portfolio.models.entities.PortfolioWithCostBasis;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;

/**
 * Service interface for creating and retrieving Portfolio.
 */
public interface PortfolioService {

  Portfolio create(List<Transaction> transactions);

  Portfolio load(Portfolio portfolio);

  Portfolio modify(String name, List<Transaction> newTransactions) throws Exception;

  Portfolio getPortfolio(String name);

  List<String> listPortfolios();

  PortfolioWithValue getValue(String name, LocalDate date);

  PortfolioWithCostBasis getCostBasis(String name, LocalDate date);

  List<PortfolioWithValue> getValues(String name, LocalDate from, LocalDate to);

  void setCommissionFee(double amount);
}
