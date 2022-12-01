package portfolio.views.gui;

import java.awt.*;
import java.util.List;
import java.util.Vector;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import portfolio.controllers.InputHandler;
import portfolio.models.entities.Transaction;
import portfolio.views.View;

/**
 * This is a view that show the GUI create page, which implement the View function.
 */
public class FlexibleCreatePageSwingView implements View {

  private JFrame frame;

  private InputHandler inputHandler;
  private List<Transaction> transactions;
  private String errorMessage;
  private Boolean isEnd;
  private Boolean isNamed;
  private int state;
  private List<String> inputBuffer;

  /**
   * This is a constructor that construct a create page view. The error messages will contain "Error
   * Format!", "The share is not a number.", "The shares cannot be negative and 0.", "Symbol not
   * found.", "error!", "The name cannot be end, back, no and yes.", "External API is not ready.
   * Please try again in the next few minutes.", "No stock entered. Please input stock.".
   *
   * @param frame        the frame for GUI
   * @param inputHandler the controller for handling input
   * @param isEnd        if user finish input the portfolio, it will be true. Otherwise, false.
   * @param isNamed      if user finish input name, it will be true. Otherwise, false.
   * @param state        the inputBuffer size in controller
   * @param transactions the map that store the symbol and shares for portfolio.
   * @param errorMessage the error message we want to show to the user.
   */
  public FlexibleCreatePageSwingView(JFrame frame, InputHandler inputHandler, Boolean isEnd,
                                     Boolean isNamed, int state, List<String> inputBuffer,
                                     List<Transaction> transactions,
                                     String errorMessage) {
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.isEnd = isEnd;
    this.isNamed = isNamed;
    this.state = state;
    this.inputBuffer = inputBuffer;
    this.transactions = transactions;
    this.errorMessage = errorMessage;
  }

  /**
   * The helper function to show the transaction table in a ScrollPane.
   *
   * @return ScrollPanel that contain the table of transaction
   */
  private JScrollPane showTransaction() {
    Vector vData = new Vector();
    Vector vName = new Vector();
    vName.add(" Date");
    vName.add("Type");
    vName.add("Stock");
    vName.add("No. of shares");
    vName.add("Commission fee");
    for (var entry : transactions) {
      Vector row = new Vector();
      row.add(String.valueOf(entry.getDate()));
      row.add(String.valueOf(entry.getType()));
      row.add(String.valueOf(entry.getSymbol()));
      row.add(String.valueOf(entry.getAmount()));
      row.add(String.valueOf(entry.getCommissionFee()));
      vData.add(row);
    }

    DefaultTableModel model = new DefaultTableModel(vData, vName) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    //DefaultTableModel model = new DefaultTableModel();
    JTable portfolioTable = new JTable(model);
    portfolioTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
    JScrollPane jsp = new JScrollPane(portfolioTable);
    return jsp;
  }

  @Override
  public void render() {
    frame.setSize(600, 700);
    JPanel panelBack = new JPanel();
    panelBack.setLayout(null);
    JButton backButton = new JButton("back");
    backButton.addActionListener(e -> inputHandler.handleInput("back"));
    backButton.setBounds(0, 0, 70, 30);
    panelBack.add(backButton);

    JLabel error = new JLabel(errorMessage);
    error.setForeground(Color.red);

    JPanel panelCommissionFee = new JPanel();
    panelCommissionFee.setLayout(new FlowLayout());
    //panelCommissionFee.setSize(500,20);
    JPanel panelDate = new JPanel();
    panelDate.setLayout(new FlowLayout());
    //panelDate.setSize(500,20);
    JPanel panelSymbol = new JPanel();
    panelSymbol.setLayout(new FlowLayout());
    //panelSymbol.setSize(500,20);
    JPanel panelShares = new JPanel();
    panelShares.setLayout(new FlowLayout());
    //panelShares.setSize(500,20);
    JPanel panelShow = new JPanel();
    panelShow.setLayout(new FlowLayout());
    JPanel panelButton = new JPanel();
    panelButton.setLayout(new FlowLayout());
    //panelButton.setSize(500,20);

    if (isEnd == false) {
      boolean check = true;
      if(state == 1) {
        check = false;
      }
      JCheckBox jCheckBox = new JCheckBox("Commission fee",check);
      jCheckBox.addActionListener(e -> inputHandler.handleInput("checkbox"));
      panelCommissionFee.add(jCheckBox);


      JLabel dateLabel = new JLabel("Date for transaction (format: 2022-10-10)");
      panelDate.add(dateLabel);
      JTextField dateTextArea = new JTextField(10);
      panelDate.add(dateTextArea);


      JLabel symbolLabel = new JLabel("Stock Symbol (ex. AAPL)");
      panelSymbol.add(symbolLabel);
      JTextField symbolTextArea = new JTextField(10);
      panelSymbol.add(symbolTextArea);


      JLabel sharesLabel = new JLabel("Shares of stocks");
      panelShares.add(sharesLabel);
      JTextField sharesTextArea = new JTextField(10);
      panelShares.add(sharesTextArea);

      if (errorMessage != null) {
        //commissionTextArea.setText(inputBuffer.get(4));
        dateTextArea.setText(inputBuffer.get(0));
        symbolTextArea.setText(inputBuffer.get(1));
        sharesTextArea.setText(inputBuffer.get(3));
      }

      if (transactions != null) {
        JScrollPane jsp = showTransaction();
        panelShow.add(jsp);
      }

      if(jCheckBox.isSelected()) {
        //JLabel commissionLabel = new JLabel("Commission fee:");
        //panelCommissionFee.add(commissionLabel);
        JTextField commissionTextArea = new JTextField(10);
        panelCommissionFee.add(commissionTextArea);
        if(errorMessage != null) {
          commissionTextArea.setText(inputBuffer.get(4));
        }
        JButton buttonBuy = new JButton("BUY");
        buttonBuy.addActionListener(e -> inputHandler.handleInput(dateTextArea.getText() + ","
                + symbolTextArea.getText() + "," + "BUY" + "," + sharesTextArea.getText() + ","
                + commissionTextArea.getText()));
        panelButton.add(buttonBuy);

        JButton buttonSell = new JButton("SELL");
        buttonSell.addActionListener(e -> inputHandler.handleInput(dateTextArea.getText() + ","
                + symbolTextArea.getText() + "," + "SELL" + "," + sharesTextArea.getText() + ","
                + commissionTextArea.getText()));
        panelButton.add(buttonSell);
      } else {
        JButton buttonBuy = new JButton("BUY");
        buttonBuy.addActionListener(e -> inputHandler.handleInput(dateTextArea.getText() + ","
                + symbolTextArea.getText() + "," + "BUY" + "," + sharesTextArea.getText() + ","
                + "0"));
        panelButton.add(buttonBuy);

        JButton buttonSell = new JButton("SELL");
        buttonSell.addActionListener(e -> inputHandler.handleInput(dateTextArea.getText() + ","
                + symbolTextArea.getText() + "," + "SELL" + "," + sharesTextArea.getText() + ","
                + "0"));
        panelButton.add(buttonSell);
      }

      /*JButton buttonBuy = new JButton("BUY");
      buttonBuy.addActionListener(e -> inputHandler.handleInput(dateTextArea.getText() + ","
              + symbolTextArea.getText() + "," + "BUY" + "," + sharesTextArea.getText() + ","
              + commissionTextArea.getText()));
      panelButton.add(buttonBuy);*/
      /*JButton buttonSell = new JButton("SELL");
      buttonSell.addActionListener(e -> inputHandler.handleInput(dateTextArea.getText() + ","
              + symbolTextArea.getText() + "," + "SELL" + "," + sharesTextArea.getText() + ","
              + commissionTextArea.getText()));
      panelButton.add(buttonSell);*/
      JButton buttonFinish = new JButton("finish");
      buttonFinish.addActionListener(e -> inputHandler.handleInput("yes"));
      panelButton.add(buttonFinish);

    }

    JPanel panelNamed = new JPanel();
    panelNamed.setLayout(new FlowLayout());
    panelNamed.setSize(500, 20);
    if (isEnd == true && isNamed == false) {
      if (transactions != null) {
        JScrollPane jsp = showTransaction();
        panelShow.add(jsp);
      }
      JLabel namedLabel = new JLabel("Name of portfolio:");
      panelNamed.add(namedLabel);
      JTextField namedTextArea = new JTextField(10);
      panelNamed.add(namedTextArea);
      JButton buttonNamed = new JButton("Create & Save to File");
      buttonNamed.addActionListener(e -> inputHandler.handleInput(namedTextArea.getText()));
      panelNamed.add(buttonNamed);
    }

    Box vBox = Box.createVerticalBox();
    vBox.setPreferredSize(new Dimension(500, 500));
    vBox.add(panelBack);
    vBox.add(error);
    vBox.add(panelCommissionFee);
    vBox.add(panelDate);
    vBox.add(panelSymbol);
    vBox.add(panelShares);
    vBox.add(panelShow);
    vBox.add(panelButton);
    vBox.add(panelNamed);

    frame.setContentPane(vBox);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
