package portfolio.views.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import portfolio.controllers.InputHandler;
import portfolio.models.entities.Transaction;
import portfolio.views.View;

/**
 * This is a view that show the GUI create page, which implement the View function.
 */
public class OneTimeStrategyPageSwingView implements View {

  private final JFrame frame;

  private final InputHandler inputHandler;
  private final Map<String, Double> stockList;
  private final String errorMessage;
  private final Boolean isEnd;
  private final List<String> inputBuffer;

  /**
   * This is a constructor that construct a create page view. The error messages will contain "Error
   * Format!", "The share is not a number.", "The shares cannot be negative and 0.", "Symbol not
   * found.", "error!", "The name cannot be end, back, no and yes.", "External API is not ready.
   * Please try again in the next few minutes.", "No stock entered. Please input stock.".
   *
   * @param frame        the frame for GUI
   * @param inputHandler the controller for handling input
   * @param isEnd        if user finish input the portfolio, it will be true. Otherwise, false.
   * @param errorMessage the error message we want to show to the user.
   */
  public OneTimeStrategyPageSwingView(JFrame frame, InputHandler inputHandler,
      Map<String, Double> stockList, boolean isEnd, List<String> inputBuffer, String errorMessage) {
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.isEnd = isEnd;
    this.inputBuffer = inputBuffer;
    this.stockList = stockList;
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
    vName.add("Stock symbol");
    vName.add("Weight");
    for (var entry : stockList.entrySet()) {
      Vector row = new Vector();
      row.add(String.valueOf(entry.getKey()));
      row.add(String.valueOf(entry.getValue()));
      vData.add(row);
    }

    DefaultTableModel model = new DefaultTableModel(vData, vName) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return true;
      }

      @Override
      public void setValueAt(Object value, int row, int col) {
        String firstKey = (String) stockList.keySet().toArray()[row];
        inputHandler.handleInput(firstKey + "," + value);
      }
    };
    JTable portfolioTable = new JTable(model);
    portfolioTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
    JScrollPane jsp = new JScrollPane(portfolioTable);
    return jsp;
  }

  @Override
  public void render() {
    frame.setTitle("Specific Date Strategy");
    frame.setSize(600, 700);
    JPanel panelBack = new JPanel();
    panelBack.setLayout(null);
    JButton backButton = new JButton("back");
    backButton.addActionListener(e -> inputHandler.handleInput("back"));
    backButton.setBounds(0, 0, 70, 30);
    panelBack.add(backButton);

    JLabel error = new JLabel(errorMessage);
    error.setForeground(Color.red);

    JPanel panelSymbol = new JPanel();
    panelSymbol.setLayout(new FlowLayout());
    JPanel panelShares = new JPanel();
    panelShares.setLayout(new FlowLayout());
    JPanel panelShow = new JPanel();
    panelShow.setLayout(new FlowLayout());
    JPanel panelButton = new JPanel();
    panelButton.setLayout(new FlowLayout());

    if (isEnd == false) {
      JLabel symbolLabel = new JLabel("Stock Symbol (ex. AAPL)");
      panelSymbol.add(symbolLabel);
      JTextField symbolTextArea = new JTextField(10);
      panelSymbol.add(symbolTextArea);

      JLabel sharesLabel = new JLabel("Weight: ");
      panelShares.add(sharesLabel);
      JTextField sharesTextArea = new JTextField(10);
      panelShares.add(sharesTextArea);

      if (errorMessage != null && inputBuffer.size() > 0) {
        symbolTextArea.setText(inputBuffer.get(0));
        sharesTextArea.setText(inputBuffer.get(1));
      }

      if (stockList != null) {
        JScrollPane jsp = showTransaction();
        panelShow.add(jsp);
      }

      JButton buttonBuy = new JButton("Add");
      buttonBuy.addActionListener(
          e -> inputHandler.handleInput(symbolTextArea.getText() + "," + sharesTextArea.getText()));
      panelButton.add(buttonBuy);

      JButton buttonFinish = new JButton("Finish");
      buttonFinish.addActionListener(e -> inputHandler.handleInput("yes"));
      panelButton.add(buttonFinish);

    }


    JPanel panelAmount = new JPanel();
    panelAmount.setLayout(new FlowLayout());
    JPanel panelDate = new JPanel();
    panelDate.setLayout(new FlowLayout());
    JPanel panelCommission = new JPanel();
    panelCommission.setLayout(new FlowLayout());
    JPanel panelCreate = new JPanel();
    panelCreate.setLayout(new FlowLayout());

    if (isEnd) {
      if (stockList != null) {
        JScrollPane jsp = showTransaction();
        panelShow.add(jsp);
      }



      JLabel amountLabel = new JLabel("Amount (USD):");
      panelAmount.add(amountLabel);
      JTextField amountTextArea = new JTextField(10);
      panelAmount.add(amountTextArea);

      JLabel TransactionDateLabel = new JLabel("Transaction date (format: 2022-10-10):");
      panelDate.add(TransactionDateLabel);
      JTextField TransactionDate = new JTextField(10);
      panelDate.add(TransactionDate);

      JLabel commissionFee = new JLabel("Commission fee:");
      panelCommission.add(commissionFee);
      JTextField commissionFeeTextArea = new JTextField(10);
      panelCommission.add(commissionFeeTextArea);

      JButton buttonCreate = new JButton("Create & Save to File");
      buttonCreate.addActionListener(e -> inputHandler.handleInput(
          amountTextArea.getText() + "," + TransactionDate.getText() + ","
              + commissionFeeTextArea.getText()));
      panelCreate.add(buttonCreate);
    }

    Box vBox = Box.createVerticalBox();
    vBox.setPreferredSize(new Dimension(500, 500));
    vBox.add(panelBack);
    vBox.add(error);
    vBox.add(panelSymbol);
    vBox.add(panelShares);
    vBox.add(panelShow);
    vBox.add(panelButton);
    vBox.add(panelAmount);
    vBox.add(panelDate);
    vBox.add(panelCommission);
    vBox.add(panelCreate);

    frame.setContentPane(vBox);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
