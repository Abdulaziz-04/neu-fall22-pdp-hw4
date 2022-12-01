package portfolio.controllers.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.portfolio.Portfolio;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a page controller for the GUI flexible portfolio create page, which is implement the Gui
 * page controller. CreatePageController handles input send by action command and is responsible for
 * checking valid stock input, creating portfolio, saving portfolio and generate View. The
 * controller can hold states while user creating their portfolio.
 */
public class ScheduleCreatePageSwingController implements SwingPageController {

  private final PortfolioModel portfolioModel;
  private final IOService ioService = new FileIOService();
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean isEnd = false;
  private final boolean addToPortfolio;
  private List<Transaction> transactions = new ArrayList<>();
  private final List<String> inputBuffer = new ArrayList<>();

  private Map<String, Double> stockList = new LinkedHashMap<>();
  private final Portfolio portfolioTmp;

  /**
   * This is a constructor to construct a FlexibleCreatePageSwingController. It will initialize the
   * model and view. And also for modify page will use the same page for this one.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public ScheduleCreatePageSwingController(
      PortfolioModel portfolioModel,
      ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
    portfolioTmp = portfolioModel.getPortfolio();
    if (portfolioTmp != null) {
      addToPortfolio = true;
    } else {
      addToPortfolio = false;
    }
  }

  @Override
  public View getView() {
    return viewFactory.newScheduleCreatePageView(stockList, isEnd, inputBuffer, transactions,
        errorMessage);
  }

  /**
   * Handle user input for creating portfolio. User will input in GUI text fields and this input
   * will be all of them.(The input receive here is different from last one. The input for Gui
   * controller will include all inputs in text field) After user click finish, it will check the
   * transactions are valid or not. If not, the user need to input all transactions again. If all
   * the transactions are valid, it will handle the name input after the user click create and save
   * to file button.(if it is not modify mode). In the end, it will create new one for name and save
   * into file (for create) or save the original file.
   *
   * @param input the action command send from GUI
   * @return PageController as a next page to be redirected
   */
  @Override
  public SwingPageController handleInput(String input) {
    input = input.trim();
    if (input.equals("back")) {
      return new MainPageSwingController(portfolioModel, viewFactory);
    }

    if (!isEnd && !input.equals("yes")) {
      errorMessage = null;
      inputBuffer.clear();
      try {
        String[] cmd = input.split(",");
        String symbol = cmd[0];
        inputBuffer.add(cmd[0]);
        inputBuffer.add(cmd[1]);
        if (cmd.length != 2) {
          errorMessage = "Error Format!";
          return this;
        }
        double amount = 0;
        try {
          amount = Double.parseDouble(cmd[1]);
        } catch (Exception e) {
          errorMessage = "The weight is not a number.";
          return this;
        }
        if (amount <= 0) {
          errorMessage = "The weight cannot be negative and 0.";
          return this;
        }
        stockList.put(symbol, amount);
      } catch (Exception e) {
        errorMessage = e.getMessage();
        return this;
      }
      return this;
    }
    errorMessage = null;
    if (input.equals("yes") && stockList.size() == 0) {
      errorMessage = "No stock entered. Please input stock.";
      return this;
    } else if (input.equals("yes") && !isEnd) {
      try {
        // Check amount valid
        transactions = stockList.entrySet().stream()
            .map(x -> new Transaction(x.getKey(), x.getValue())).collect(
                Collectors.toList());
        portfolioModel.checkTransactions(transactions);
        isEnd = true;
        return this;
      } catch (Exception e) {
        errorMessage = e.getMessage();
        inputBuffer.clear();
        transactions.clear();
        return this;
      }
    } else {
      String[] cmd = input.split(",");
      String pname = portfolioTmp != null ? portfolioTmp.getName() : cmd[0];
      String sname = cmd[0];
      try {
        if (Double.parseDouble(cmd[5]) < 0) {
          errorMessage = "Commission cannot be negative.";
          return this;
        }
      } catch (Exception e) {
        errorMessage = e.getMessage();
        return this;
      }
      try {
        if (!addToPortfolio) {
          portfolioModel.create(pname, PortfolioFormat.FLEXIBLE, new ArrayList<>());
        }
        portfolioModel.addSchedule(
            sname,
            Double.parseDouble(cmd[1]),
            Integer.parseInt(cmd[2]),
            LocalDate.parse(cmd[3]),
            cmd[4].isEmpty() ? null : LocalDate.parse(cmd[4]),
            Double.parseDouble(cmd[5]),
            null,
            transactions
        );
        ioService.saveTo(portfolioModel.getString(), pname + ".txt", addToPortfolio);
        return new LoadPageSwingController(portfolioModel, viewFactory);
      } catch (RuntimeException e) {
        errorMessage = e.getMessage() + " Please enter transaction list again.";
        inputBuffer.clear();
        transactions.clear();
      } catch (Exception e) {
        try {
          portfolioModel.init();
        } catch (Exception ignored) {
        }
        errorMessage = e.getMessage();
      }
    }
    return this;
  }

}
