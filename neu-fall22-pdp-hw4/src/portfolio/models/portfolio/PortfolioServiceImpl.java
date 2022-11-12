package portfolio.models.portfolio;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import portfolio.models.entities.PortfolioWithCostBasis;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.stockprice.StockQueryService;

/**
 * This is a class that represent a portfolio service, which creating and retrieving portfolio.
 */
public class PortfolioServiceImpl implements PortfolioService {

  private final StockQueryService stockQueryService;
  private Map<String, Portfolio> portfolios = new LinkedHashMap<>();
  private double commissionFee = 0;

  public PortfolioServiceImpl(StockQueryService stockQueryService){
    this.stockQueryService = stockQueryService;
  }
  @Override
  public Portfolio create(List<Transaction> transactions) {
    return null;
  }

  @Override
  public Portfolio load(Portfolio portfolio) {
    return null;
  }

  private List<Transaction> mergeTransactions(List<Transaction> transactions, List<Transaction> newTransaction) {
    Map<String, Integer> stocks = new HashMap<>();
    transactions.addAll(newTransaction);
    transactions.sort(Comparator.comparing(Transaction::getDate));
    for (var tx: transactions){
      int current = stocks.getOrDefault(tx.getSymbol(), 0);
      int multiplier = tx.getType() == TransactionType.BUY ? 1 : -1;
      int newShare = current + multiplier * tx.getAmount();
      if (newShare < 0) {
        throw new IllegalArgumentException("Transaction invalid.");
      }
      stocks.put(tx.getSymbol(), newShare);
    }
    return transactions;
  }

  @Override
  public Portfolio modify(String name, List<Transaction> newTransactions) throws Exception {
    Portfolio portfolio = getPortfolio(name);
    List<Transaction> transactions = portfolio.getTransaction();
    portfolio.setTransactions(mergeTransactions(transactions, newTransactions));
    return portfolio;
  }

  @Override
  public Portfolio getPortfolio(String name) {
    Portfolio portfolio = portfolios.get(name);
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio name= [" + name + "] not found.");
    }
    return portfolio;
  }

  @Override
  public List<String> listPortfolios() {
    return List.copyOf(portfolios.keySet());
  }

  @Override
  public PortfolioWithValue getValue(String name, LocalDate date) {
    return null;
  }

  @Override
  public PortfolioWithCostBasis getCostBasis(String name, LocalDate date) {
    return null;
  }

  @Override
  public List<PortfolioWithValue> getValues(String name, LocalDate from, LocalDate to) {
    return null;
  }

  @Override
  public void setCommissionFee(double amount) {
    commissionFee = amount;
  }
}
