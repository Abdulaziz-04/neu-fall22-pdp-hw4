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
import portfolio.models.entities.PortfolioWithCostBasis;
import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.entities.StockListEntry;
import portfolio.models.entities.StockPrice;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.PortfolioService;
import portfolio.models.stockprice.StockQueryService;

/**
 * This is a class that represent a portfolio service, which creating and retrieving portfolio.
 */
public class PortfolioServiceImpl implements PortfolioService {

  private final StockQueryService stockQueryService;
  private final PortfolioParser portfolioParser;
  private Portfolio portfolio = null;
  private double commissionFee = 0;

  public PortfolioServiceImpl(StockQueryService stockQueryService,
      PortfolioParser portfolioParser) {
    this.stockQueryService = stockQueryService;
    this.portfolioParser = portfolioParser;
  }

  @Override
  public Portfolio getPortfolio() {
    return portfolio;
  }

  @Override
  public Portfolio create(PortfolioFormat format, List<Transaction> transactions) throws Exception {
    List<String> stockSymbolList = stockQueryService.getStockList().stream().map(
        StockListEntry::getSymbol).collect(
        Collectors.toList());
    for (var entry : transactions) {
      if (!stockSymbolList.contains(entry.getSymbol())) {
        throw new IllegalArgumentException("Symbol [" + entry.getSymbol() + "] not found.");
      }
    }

    switch (format) {
      case INFLEXIBLE:
        return new InflexiblePortfolio(transactions);
      case FLEXIBLE:
        return new FlexiblePortfolio(transactions);
    }
    return null;
  }

  @Override
  public void createAndSet(PortfolioFormat format, List<Transaction> transactions)
      throws Exception {
    portfolio = create(format, transactions);
  }

  @Override
  public void load(String text) throws Exception {
    var format = portfolioParser.parseFormat(text);
    createAndSet(format, portfolioParser.parseTransaction(text));
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
    Map<String, StockPrice> prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
    return portfolio.getPortfolioWithValue(date, prices);
  }

  @Override
  public PortfolioWithCostBasis getCostBasis(LocalDate date) throws Exception {
    Map<String, StockPrice> prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
    return portfolio.getCostBasis(date, prices, commissionFee);
  }

  @Override
  public Map<String, PortfolioWithValue> getValues(LocalDate from, LocalDate to) {
    Map<String, PortfolioWithValue> map = new HashMap<>();
    for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
      Map<String, StockPrice> prices;
      try {
        prices = stockQueryService.getStockPrice(date, portfolio.getSymbols());
        map.put(date.toString(), portfolio.getPortfolioWithValue(date, prices));
      } catch (Exception ignored) {
      }
    }
    return map;
  }
}
