package portfolio.views.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import portfolio.controllers.InputHandler;
import portfolio.models.portfolio.BuySchedule;
import portfolio.views.View;

/**
 * This is a view that show the GUI determine page, which implement the View function.
 */
public class ScheduleInfoPageSwingView implements View {

  private final JFrame frame;
  private final InputHandler inputHandler;
  private final String errorMessage;
  private final BuySchedule schedule;

  /**
   * This is a constructor that construct a GUI determine page view.
   *
   * @param frame              the frame for GUI
   * @param inputHandler       the controller for handling input
   * @param errorMessage       the error message we want to show to the user
   */
  public ScheduleInfoPageSwingView(JFrame frame, InputHandler inputHandler,
                            BuySchedule schedule,
                            String errorMessage) {
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.errorMessage = errorMessage;
    this.schedule = schedule;
  }

  private JScrollPane showStockList() {
    Vector vData = new Vector();
    Vector vName = new Vector();
    vName.add("Stock");
    vName.add("Weight percentage");
    for (var entry : schedule.getBuyingList()) {
      Vector row = new Vector();
      row.add(String.valueOf(entry.getSymbol()));
      row.add(String.valueOf(entry.getAmount()));
      vData.add(row);
    }

    DefaultTableModel model = new DefaultTableModel(vData, vName) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    JTable portfolioTable = new JTable(model);
    portfolioTable.setPreferredScrollableViewportSize(new Dimension(500, 200));
    JScrollPane jsp = new JScrollPane(portfolioTable);
    return jsp;
  }

  @Override
  public void render() {
    frame.setTitle("Information of strategy");
    frame.setSize(600, 600);
    JPanel panelBack = new JPanel();
    panelBack.setLayout(null);
    JButton backButton = new JButton("back");
    backButton.addActionListener(e -> inputHandler.handleInput("back"));
    backButton.setBounds(0, 0, 70, 30);
    panelBack.add(backButton);

    JLabel error = new JLabel(errorMessage);
    error.setForeground(Color.red);

    JPanel panelDate = new JPanel();
    panelDate.setLayout(new FlowLayout());
    panelDate.setSize(500, 20);

    JPanel panelShow = new JPanel();
    panelShow.add(new JLabel("Name: " + schedule.getName()));
    panelShow.add(new JLabel("Type: " + schedule.getType()));
    panelShow.add(new JLabel("Amount: $" + schedule.getAmount()));
    panelShow.add(new JLabel("Start date: " + schedule.getStartDate()));
    panelShow.add(new JLabel("End date: " + schedule.getEndDate()));
    panelShow.add(new JLabel("Commission fee: $" + schedule.getTransactionFee()));

    if (schedule.getBuyingList() != null) {
      JScrollPane jsp = showStockList();
      JLabel showLabel = new JLabel("Schedule: ");
      panelShow.add(showLabel);
      panelShow.add(jsp);
    }

    Box vBox = Box.createVerticalBox();
    vBox.setPreferredSize(new Dimension(500, 500));
    vBox.add(panelBack);
    vBox.add(error);
    vBox.add(panelDate);
    vBox.add(panelShow);

    frame.setContentPane(vBox);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
