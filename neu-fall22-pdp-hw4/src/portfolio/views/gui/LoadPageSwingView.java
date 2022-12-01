package portfolio.views.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;


import javax.swing.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import portfolio.controllers.InputHandler;
import portfolio.views.ButtonColumn;
import portfolio.views.View;

import portfolio.models.portfolio.Portfolio;

/**
 * This is a view that show the GUI load page, which implement the View function.
 */
public class LoadPageSwingView implements View {

  private final String errorMessage;
  private final Portfolio portfolio;
  boolean showModifyMenu;
  private final JFrame frame;
  private final InputHandler inputHandler;

  /**
   * This is a constructor that construct a GUI load page view.
   *
   * @param frame          the frame for GUI
   * @param inputHandler   the controller for handling input
   * @param portfolio      the portfolio that we want to load
   * @param showModifyMenu show modify menu or not
   * @param errorMessage   the error message we want to show to the user
   */
  public LoadPageSwingView(JFrame frame, InputHandler inputHandler,
      Portfolio portfolio,
      boolean showModifyMenu, String errorMessage) {
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.errorMessage = errorMessage;
    this.portfolio = portfolio;
    this.showModifyMenu = showModifyMenu;
  }

  private JScrollPane showPortfolioComposition() {
    Vector vData = new Vector();
    Vector vName = new Vector();
    vName.add("Date");
    vName.add("Type");
    vName.add("Stock");
    vName.add("No. of shares");
    vName.add("Commission fee");
    for (var entry : portfolio.getTransactions()) {
      Vector row = new Vector();
      row.add(String.valueOf(entry.getDate()));
      row.add(String.valueOf(entry.getType()));
      row.add(String.valueOf(entry.getSymbol()));
      row.add(String.valueOf(entry.getAmount()));
      row.add("$" + entry.getCommissionFee());
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

  private JScrollPane showSchedule() {
    Vector vData = new Vector();
    Vector vName = new Vector();
    vName.add("Name");
    vName.add("Type");
    vName.add("Amount");
    vName.add("Frequency");
    vName.add("StartDate");
    vName.add("EndDate");
    vName.add("");
    for (var entry : portfolio.getBuySchedules()) {
      Vector row = new Vector();
      row.add(String.valueOf(entry.getName()));
      row.add(String.valueOf(entry.getType()));
      row.add("$" + entry.getAmount());
      row.add(String.valueOf(entry.getFrequencyDays()));
      row.add(String.valueOf(entry.getStartDate()));
      row.add(String.valueOf(entry.getEndDate()));
      row.add("View");
      vData.add(row);
    }
    DefaultTableModel model = new DefaultTableModel(vData, vName) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    JTable table = new JTable(model);

    Action view = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        JTable table = (JTable) e.getSource();
        int modelRow = Integer.parseInt(e.getActionCommand());
        inputHandler.handleInput("view_schedule_name," + portfolio.getBuySchedules().get(modelRow));
      }
    };

    ButtonColumn buttonColumn = new ButtonColumn(table, view, 6);
    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());
        int col = table.columnAtPoint(evt.getPoint());
        if (row >= 0 && col == 6) {
          inputHandler.handleInput(
              "view_schedule_name," + portfolio.getBuySchedules().get(row).getName());
        }
      }
    });

    table.setPreferredScrollableViewportSize(new Dimension(500, 200));
    JScrollPane jsp = new JScrollPane(table);
    return jsp;
  }

  @Override
  public void render() {
    frame.setSize(600, 800);
    JPanel panelBack = new JPanel();
    panelBack.setLayout(null);
    JButton backButton = new JButton("back");
    backButton.addActionListener(e -> inputHandler.handleInput("back"));
    backButton.setBounds(0, 0, 70, 30);
    panelBack.add(backButton);

    JLabel error = new JLabel(errorMessage);
    error.setForeground(Color.red);

    JPanel panelNameAndButton = new JPanel();
    panelNameAndButton.setLayout(new FlowLayout());
    panelNameAndButton.setSize(500, 20);
    JLabel portfolioName = new JLabel("Name of Portfolio:");
    panelNameAndButton.add(portfolioName);

    JTextField nameTextArea = new JTextField(10);
    if (portfolio != null) {
      nameTextArea.setText(portfolio.getName());
    }
    panelNameAndButton.add(nameTextArea);

    if (portfolio == null) {
      JButton loadButton = new JButton("load");
      loadButton.addActionListener(e -> inputHandler.handleInput(nameTextArea.getText()));
      panelNameAndButton.add(loadButton);
    }

    JPanel panelShow = new JPanel();
    JPanel panelMenu = new JPanel();
    JPanel panelShowSchedule = new JPanel();
    panelMenu.setLayout(new GridLayout(5, 1));
    if (portfolio != null) {
      JScrollPane jsp = showPortfolioComposition();
      panelShow.add(jsp);

      if (portfolio.getBuySchedules() != null) {
        JScrollPane jsp1 = showSchedule();
        panelShowSchedule.add(jsp1);
      }

      JLabel menuLabel = new JLabel("Menu for current portfolio:");
      panelMenu.add(menuLabel);
      JButton informationsButton = new JButton("Show composition, value, cost of basis for " +
          "specific date");
      informationsButton.addActionListener(e -> inputHandler.handleInput("1"));
      panelMenu.add(informationsButton);
      JButton performanceButton = new JButton("Show performance of a portfolio");
      performanceButton.addActionListener(e -> inputHandler.handleInput("2"));
      panelMenu.add(performanceButton);
      if (showModifyMenu) {
        JButton modifyButton = new JButton("modify the portfolio");
        modifyButton.addActionListener(e -> inputHandler.handleInput("3"));
        panelMenu.add(modifyButton);
      }
      JButton addScheduleButton = new JButton("Add new strategy");
      addScheduleButton.addActionListener(e -> inputHandler.handleInput("4"));
      panelMenu.add(addScheduleButton);
    }

    Box vBox = Box.createVerticalBox();
    vBox.setPreferredSize(new Dimension(500, 500));
    vBox.add(panelBack);
    vBox.add(error);
    vBox.add(panelNameAndButton);
    vBox.add(panelShow);
    vBox.add(panelShowSchedule);
    vBox.add(panelMenu);

    frame.setContentPane(vBox);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
