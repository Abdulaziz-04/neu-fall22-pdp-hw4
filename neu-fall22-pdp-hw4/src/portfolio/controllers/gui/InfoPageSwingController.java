package portfolio.controllers.gui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import portfolio.models.entities.PortfolioWithValue;
import portfolio.models.portfolio.PortfolioModel;
import portfolio.views.View;
import portfolio.views.ViewFactory;

/**
 * This is a controller for the determining portfolio value. It implements PageController.
 * InfoPageController handles input from user and is responsible for getting portfolio value of the
 * input date.
 */
public class InfoPageSwingController implements SwingPageController {

  private final PortfolioModel portfolioModel;
  private final ViewFactory viewFactory;
  private String errorMessage;
  private PortfolioWithValue portfolioWithValue;
  private Double costOfBasis;

  /**
   * This is a constructor that construct a InfoPageController, which is for determining a portfolio
   * on a certain date.
   *
   * @param portfolioModel the model of portfolio
   * @param viewFactory    ViewFactor for creating a view
   */
  public InfoPageSwingController(PortfolioModel portfolioModel,
                                 ViewFactory viewFactory) {
    this.portfolioModel = portfolioModel;
    this.viewFactory = viewFactory;
  }

  @Override
  public View getView() {
    return viewFactory.newInfoPageView(portfolioWithValue, costOfBasis, errorMessage);
  }

  /**
   * Handle user input for determining portfolio value. User can input a date as many times as they
   * want. If the input is 'back', the method will return LoadPageController, otherwise return
   * current object.
   *
   * @param input user input as a string
   * @return PageController as a next page to be redirected
   */
  @Override
  public SwingPageController handleInput(String input) {
    errorMessage = null;
    if (input.equals("back")) {
      return new LoadPageSwingController(portfolioModel, viewFactory);
    }

    LocalDate date;

    try {
      date = LocalDate.parse(input);

    } catch (DateTimeParseException e) {
      errorMessage = "Error! Please input the correct date.";
      portfolioWithValue = null;
      return this;
    }

    try {
      costOfBasis = portfolioModel.getCostBasis(date);
    } catch (Exception e) {
      costOfBasis = null;
    }

    try {
      portfolioWithValue = portfolioModel.getValue(date);
    } catch (Exception e) {
      errorMessage = e.getMessage();
      portfolioWithValue = null;
    }
    return this;
  }
}
