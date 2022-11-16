package portfolio.controllers.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import portfolio.controllers.PageController;
import portfolio.controllers.datastore.FileIOService;
import portfolio.controllers.datastore.IOService;
import portfolio.models.entities.PortfolioFormat;
import portfolio.models.entities.Transaction;
import portfolio.models.entities.TransactionType;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.models.portfolio.PortfolioParser;
import portfolio.models.portfolio.impl.PortfolioTextParser;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a page controller for the flexible create page, which is implement the page controller.
 * CreatePageController handles input from user and is responsible for checking valid stock input,
 * creating portfolio, saving portfolio and generate View. The controller can hold states while user
 * creating their portfolio. The states are stock selection, naming portfolio and portfolio
 * confirmation.
 */
public class FlexibleCreatePageController implements PageController {

  private final PortfolioModel portfolioModel;
  private final IOService ioService = new FileIOService();
  private final PortfolioParser portfolioParser = new PortfolioTextParser();
  private final ViewFactory viewFactory;
  private String errorMessage;
  private boolean isEnd = false;
  private boolean isNamed = false;
  private final boolean modifyMode;
  private List<Transaction> transactions;
  private final List<String> inputBuffer = new ArrayList<>();

  /**
   * This is a constructor to construct a FlexibleCreatePageController.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory ViewFactor for creating a view
   */
  public FlexibleCreatePageController(
      PortfolioModel portfolioModel,
      ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
    if (portfolioModel.getPortfolio() != null) {
      this.transactions = new ArrayList<>(portfolioModel.getPortfolio().getTransactions());
      isNamed = true;
      modifyMode = true;
    } else {
      this.transactions = new ArrayList<>();
      modifyMode = false;
    }
  }

  @Override
  public View getView() {
    return viewFactory.newFlexibleCreatePageView(isEnd, isNamed, inputBuffer.size(), transactions,
        errorMessage);
  }

  /**
   * Handle user input for creating portfolio. User can select stock symbol and number of shares,
   * after type 'end', user can input the desire portfolio name. Portfolio name cannot be the same
   * as an existing file in the folder and some keywords such as 'end', 'yes' and 'no'. The method
   * return the next page controller that user should be navigated to.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  @Override
  public PageController handleInput(String input) {
    input = input.trim();
    errorMessage = null;

    if (input.equals("back")) {
      return new MainPageController(portfolioModel, viewFactory);
    }
    if (!isEnd && !input.equals("end")) {
      try {
        int size = inputBuffer.size();
        if (size == 0) {
          LocalDate.parse(input);
          inputBuffer.add(input);
          return this;
        } else if (size == 1) {
          TransactionType.parse(input);
          inputBuffer.add(input);
          return this;
        } else if (size == 2) {
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

            }
            inputBuffer.add(input);
          } catch (Exception e) {
            errorMessage = "Commission fee input is not a number.";
            return this;
          }
          transactions.add(
              new Transaction(
                  TransactionType.parse(inputBuffer.get(1)),
                  inputBuffer.get(2),
                  Integer.parseInt(inputBuffer.get(3)),
                  LocalDate.parse(inputBuffer.get(0)),
                  Double.parseDouble(inputBuffer.get(4))
              )
          );
          return this;
        } else if (size == 5) {
          if (input.equals("Y")) {
            inputBuffer.clear();
            return this;
          }
        }
      } catch (Exception e) {
        errorMessage = e.getMessage();
      }
    }
    if (inputBuffer.size() == 5 && !isEnd) {
      try {
        portfolioModel.create(null, PortfolioFormat.FLEXIBLE, transactions);
        isEnd = true;
      } catch (Exception e) {
        errorMessage = e.getMessage() + " Please enter transaction list again.";
        inputBuffer.clear();
        if (portfolioModel.getPortfolio() != null) {
          transactions = portfolioModel.getPortfolio().getTransactions();
        }
      }
    } else if (inputBuffer.size() == 5) {
      String name = isNamed ? portfolioModel.getPortfolio().getName() : input;
      try {
        portfolioModel.set(name, PortfolioFormat.FLEXIBLE, transactions);
        ioService.saveTo(portfolioParser.toString(portfolioModel.getPortfolio()), name + ".txt", modifyMode);
        return new LoadPageController(portfolioModel, viewFactory);
      } catch (Exception e) {
        errorMessage = e.getMessage();
      }
    }
    return this;
  }

}
