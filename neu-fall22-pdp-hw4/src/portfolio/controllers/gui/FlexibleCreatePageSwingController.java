package portfolio.controllers.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
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
public class FlexibleCreatePageSwingController implements SwingPageController {

  private final PortfolioModel portfolioModel;
  private final IOService ioService = new FileIOService();
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean isEnd = false;
  private boolean isNamed = false;
  private final boolean modifyMode;
  private int state = 0;
  private List<Transaction> transactions = new ArrayList<>();
  private final List<String> inputBuffer = new ArrayList<>();

  private Portfolio portfolioTmp;

  /**
   * This is a constructor to construct a FlexibleCreatePageSwingController. It will initialize the
   * model and view. And also for modify page will use the same page for this one.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public FlexibleCreatePageSwingController(
      PortfolioModel portfolioModel,
      ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
    portfolioTmp = portfolioModel.getPortfolio();
    if (portfolioTmp != null) {
      isNamed = true;
      modifyMode = true;
    } else {
      modifyMode = false;
    }
  }

  @Override
  public View getView() {
    List<Transaction> transactions = new ArrayList<>();
    if (portfolioTmp != null) {
      transactions.addAll(new ArrayList<>(portfolioTmp.getTransactions()));
    }
    transactions.addAll(this.transactions);
    return viewFactory.newFlexibleCreatePageView(isEnd, isNamed, state, inputBuffer,
        transactions,
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
    //errorMessage = null;

    if (input.equals("back")) {
      return new MainPageSwingController(portfolioModel, viewFactory);
    }

    if (!isEnd && !input.equals("yes")) {
      if (input.equals("checkbox")) {
        if (state == 0) {
          state = 1;
        } else {
          state = 0;
        }
        return this;
      }
      errorMessage = null;
      inputBuffer.clear();
      String[] cmd = input.split(",");
      if (cmd.length != 5) {
        errorMessage = "Error for input";
        return this;
      }
      inputBuffer.add(cmd[0]);
      inputBuffer.add(cmd[1]);
      inputBuffer.add(cmd[2]);
      inputBuffer.add(cmd[3]);
      if (cmd[4].equals("") || cmd[4] == null) {
        cmd[4] = "0";
      }
      inputBuffer.add(cmd[4]);

      try {
        try {
          LocalDate.parse(cmd[0]);
        } catch (Exception e) {
          errorMessage = "The format error!";
          return this;
        }
        portfolioModel.checkTransaction(LocalDate.parse(cmd[0]), cmd[1]);
        TransactionType.parse(cmd[2]);
        Integer.parseInt(cmd[3]);
        if (Integer.parseInt(cmd[3]) <= 0) {
          errorMessage = "The shares cannot be negative.";
          return this;
        }

        if (Double.parseDouble(cmd[4]) < 0) {
          errorMessage = "Commission cannot be negative.";
          return this;
        }
        transactions.add(
            new Transaction(
                TransactionType.parse(inputBuffer.get(2)),
                inputBuffer.get(1),
                Integer.parseInt(inputBuffer.get(3)),
                LocalDate.parse(inputBuffer.get(0)),
                Double.parseDouble(inputBuffer.get(4))
            )
        );

      } catch (Exception e) {
        errorMessage = e.getMessage();
        return this;
      }
      return this;
    }
    errorMessage = null;
    if (input.equals("yes") && isEnd == false) {
      try {
        // Check amount valid
        List<Transaction> transactions = new ArrayList<>();
        if (portfolioTmp != null) {
          transactions.addAll(new ArrayList<>(portfolioTmp.getTransactions()));
        }
        transactions.addAll(this.transactions);
        portfolioModel.checkTransactions(transactions);
        portfolioModel.create(null, PortfolioFormat.FLEXIBLE, transactions);
        isEnd = true;
        if (modifyMode) {
          state = 99;
        }
        return this;
      } catch (Exception e) {
        errorMessage = e.getMessage() + " Please enter transaction list again.";
        inputBuffer.clear();
        transactions.clear();
        return this;
      }
    } else {
      String pname = portfolioTmp != null && isNamed ? portfolioTmp.getName() : input;
      try {
        if (modifyMode) {
          List<Transaction> transactions = new ArrayList<>();
          if (portfolioTmp != null) {
            transactions.addAll(new ArrayList<>(portfolioTmp.getTransactions()));
          }
          transactions.addAll(this.transactions);
          portfolioModel.create(pname, PortfolioFormat.FLEXIBLE, transactions);
          for (var schedule: portfolioTmp.getBuySchedules()) {
            portfolioModel.addSchedule(
                schedule.getName(),
                schedule.getAmount(),
                schedule.getFrequencyDays(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getTransactionFee(),
                schedule.getLastRunDate(),
                schedule.getBuyingList());
          }
        } else {
          portfolioModel.create(pname, PortfolioFormat.FLEXIBLE, transactions);
        }
        ioService.saveTo(portfolioModel.getString(), pname + ".txt", modifyMode);
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


    /*if (!isEnd && !input.equals("end")) {
      try {
        int size = inputBuffer.size();
        if (size == 0) {
          LocalDate.parse(input);
          inputBuffer.add(input);
          return this;
        } else if (size == 1) {
          try {
            portfolioModel.checkTransaction(LocalDate.parse(inputBuffer.get(0)), input);
            inputBuffer.add(input);
          } catch (Exception e) {
            errorMessage = e.getMessage();
            inputBuffer.clear();
          }
          return this;
        } else if (size == 2) {
          TransactionType.parse(input);
          inputBuffer.add(input);
          return this;
        } else if (size == 3) {
          try {
            if (Integer.parseInt(input) <= 0) {
              errorMessage = "The shares cannot be negative.";
              return this;
            }
            inputBuffer.add(input);
            return this;
          } catch (Exception e) {
            errorMessage = "The share is not a number or an integer.";
            return this;
          }
        } else if (size == 4) {
          try {
            if (Double.parseDouble(input) < 0) {
              errorMessage = "Commission cannot be negative.";
              return this;
            }
            inputBuffer.add(input);
          } catch (Exception e) {
            errorMessage = "Commission fee input is not a number.";
            return this;
          }
          transactions.add(
              new Transaction(
                  TransactionType.parse(inputBuffer.get(2)),
                  inputBuffer.get(1),
                  Integer.parseInt(inputBuffer.get(3)),
                  LocalDate.parse(inputBuffer.get(0)),
                  Double.parseDouble(inputBuffer.get(4))
              )
          );
          return this;
        } else if (size == 5) {
          if (input.equals("yes")) {
            inputBuffer.clear();
            return this;
          }
        }
      } catch (Exception e) {
        errorMessage = e.getMessage();
        return this;
      }
    }
    if (inputBuffer.size() == 5 && !isEnd) {
      try {
        // Check amount valid
        portfolioModel.checkTransactions(transactions);
        portfolioModel.create(null, PortfolioFormat.FLEXIBLE, transactions);
        isEnd = true;
        portfolioModel.init();
      } catch (Exception e) {
        errorMessage = e.getMessage() + " Please enter transaction list again.";
        inputBuffer.clear();
        transactions.clear();
      }
    } else if (inputBuffer.size() == 5) {
      String name = portfolioTmp != null && isNamed ? portfolioTmp.getName() : input;
      try {
        if (modifyMode) {
          portfolioModel.addTransactions(transactions);
        } else {
          portfolioModel.create(name, PortfolioFormat.FLEXIBLE, transactions);
        }
        ioService.saveTo(portfolioModel.getString(), name + ".txt", modifyMode);
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
    return this;*/
  }

}
