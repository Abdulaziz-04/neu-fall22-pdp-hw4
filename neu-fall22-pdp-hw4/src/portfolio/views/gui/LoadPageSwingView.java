package portfolio.views.gui;

import java.awt.*;
import java.util.Vector;


import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import portfolio.controllers.InputHandler;
import portfolio.views.View;

import portfolio.models.portfolio.Portfolio;

/**
 * This is a view that show the GUI load page, which implement the View function.
 */
public class LoadPageSwingView implements View {

  private String errorMessage;
  private Portfolio portfolio;
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

  private JPanel showMenu() {
    JPanel panelMenu = new JPanel();
    panelMenu.setLayout(new GridLayout(4, 1));
    JLabel menuLabel = new JLabel("Menu for current portfolio:");
    panelMenu.add(menuLabel);
    JButton informationsButton = new JButton("Show composition, value, cost of basis for " +
            "specific date");
    informationsButton.addActionListener(e -> inputHandler.handleInput("1"));
    panelMenu.add(informationsButton);
    JButton performanceButton = new JButton("Show performance of a portfolio");
    performanceButton.addActionListener(e -> inputHandler.handleInput("2"));
    panelMenu.add(performanceButton);
    if (showModifyMenu == true) {
      JButton modifyButton = new JButton("modify the portfolio");
      modifyButton.addActionListener(e -> inputHandler.handleInput("3"));
      panelMenu.add(modifyButton);
    }
    return panelMenu;
  }

  @Override
  public void render() {
    //JFrame frame = new JFrame();
    frame.setSize(600, 600);
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
    panelMenu.setLayout(new GridLayout(4, 1));
    if (portfolio != null) {
      JScrollPane jsp = showPortfolioComposition();
      panelShow.add(jsp);

      //panelMenu = showMenu();
      JLabel menuLabel = new JLabel("Menu for current portfolio:");
      panelMenu.add(menuLabel);
      JButton informationsButton = new JButton("Show composition, value, cost of basis for " +
              "specific date");
      informationsButton.addActionListener(e -> inputHandler.handleInput("1"));
      panelMenu.add(informationsButton);
      JButton performanceButton = new JButton("Show performance of a portfolio");
      performanceButton.addActionListener(e -> inputHandler.handleInput("2"));
      panelMenu.add(performanceButton);
      if (showModifyMenu == true) {
        JButton modifyButton = new JButton("modify the portfolio");
        modifyButton.addActionListener(e -> inputHandler.handleInput("3"));
        panelMenu.add(modifyButton);
      }
    }
    Box vBox = Box.createVerticalBox();
    vBox.setPreferredSize(new Dimension(500, 500));
    vBox.add(panelBack);
    vBox.add(error);
    vBox.add(panelNameAndButton);
    vBox.add(panelShow);
    vBox.add(panelMenu);

    frame.setContentPane(vBox);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
