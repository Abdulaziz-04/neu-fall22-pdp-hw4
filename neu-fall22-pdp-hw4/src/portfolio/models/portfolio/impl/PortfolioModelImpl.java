package portfolio.models.portfolio.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockListEntry;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.stockprice.StockQueryService;

/**
 * This is a class that represent a portfolio service, which creating and retrieving portfolio.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private final StockQueryService stockQueryService;
  private final PortfolioParser portfolioParser;
  private Portfolio portfolio = null;

  public PortfolioModelImpl(StockQueryService stockQueryService,
      PortfolioParser portfolioParser) {
    this.stockQueryService = stockQueryService;
    this.portfolioParser = portfolioParser;
  }

  @Override
  public Portfolio getPortfolio() {
    return portfolio;
  }

  @Override
  public Portfolio create(String name, PortfolioFormat format, List<Transaction> transactions) throws Exception {
    Map<String, LocalDate> map = new HashMap<>();
    for (var stock : stockQueryService.getStockList()) {
      map.put(stock.getSymbol(), stock.getIpoDate());
    }
    for (var entry : transactions) {
      List<String> symbols = new ArrayList<>();
      symbols.add(entry.getSymbol());
      if (!map.containsKey(entry.getSymbol())) {
        throw new IllegalArgumentException("Symbol [" + entry.getSymbol() + "] not found.");
      }
      if (entry.getDate() != null) {
        stockQueryService.getStockPrice(entry.getDate(), symbols).containsKey(entry.getSymbol());
      }
    }

    switch (format) {
      case INFLEXIBLE:
        return new InflexiblePortfolio(name, transactions);
      case FLEXIBLE:
        return new FlexiblePortfolio(name, transactions);
    }
    return null;
  }

  @Override
  public Portfolio createAndSet(String name, PortfolioFormat format, List<Transaction> transactions)
      throws Exception {
    portfolio = create(name, format, transactions);
    return portfolio;
  }

  @Override
  public void init() throws Exception {
    portfolio = null;
    stockQueryService.getStockList();
  }

  @Override
  public void load(String name, String text) throws Exception {
    var format = portfolioParser.parseFormat(text);
    createAndSet(name, format, portfolioParser.parseTransaction(text));
  }

  private List<Transaction> mergeTransactions(List<Transaction> transactions,
      List<Transaction> newTransaction) {
    Map<String, Integer> stocks = new HashMap<>();
    List<Transaction> list = Stream.concat(transactions.stream(), newTransaction.stream())
        .sorted(Comparator.comparing(Transaction::getDate)).collect(Collectors.toList());
    for (var tx : list) {
      int current = stocks.getOrDefault(tx.getSymbol(), 0);
      int multiplier = tx.getType() == TransactionType.BUY ? 1 : -1;
      int newShare = current + multiplier * tx.getAmount();
      if (newShare < 0) {
        throw new IllegalArgumentException("Transaction invalid.");
      }
      stocks.put(tx.getSymbol(), newShare);
    }
    return list;
  }

  @Override
  public void addTransactions(List<Transaction> newTransactions) throws Exception {
    if (portfolio.isReadOnly()) {
      throw new IllegalArgumentException("Portfolio is not modifiable.");
    }
    List<Transaction> transactions = portfolio.getTransaction();

    // Create same class of portfolio with new set of transactions
    portfolio = portfolio.create(mergeTransactions(transactions, newTransactions));
  }

  @Override
  public PortfolioWithValue getValue(LocalDate date) throws Exception {
    Map<String, StockPrice> prices = stockQueryService.getStockPrice(date, portfolio.getSymbols(date));
    return portfolio.getPortfolioWithValue(date, prices);
  }

  @Override
  public double getCostBasis(LocalDate date) throws Exception {
    Map<String, StockPrice> prices = stockQueryService.getStockPrice(date, portfolio.getSymbols(date));
    return portfolio.getCostBasis(date, prices);
  }

  @Override
  public Map<LocalDate, Double> getValues(LocalDate from, LocalDate to) {
    Map<LocalDate, Double> map = new HashMap<>();
    for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
      Map<String, StockPrice> prices;
      try {
        prices = stockQueryService.getStockPrice(date, portfolio.getSymbols(date));
        map.put(date, portfolio.getPortfolioWithValue(date, prices).getTotalValue());
      } catch (Exception ignored) {
      }
    }
    return map;
  }
}
