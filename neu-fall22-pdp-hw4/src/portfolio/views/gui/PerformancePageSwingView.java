package portfolio.views.gui;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import portfolio.controllers.InputHandler;
import portfolio.views.View;

/**
 * This is a GUI view for performance page, which implement View interface.
 */
public class PerformancePageSwingView implements View {

  private final String errorMessage;

  private final String portfolioName;

  private final LocalDate startDate;
  private final LocalDate endDate;

  private final Map<String, Integer> performance;
  private final String scale;

  private boolean isFinish;

  private JFrame frame;
  private InputHandler inputHandler;


  /**
   * This is a constructor to construct a GUI performance page view.
   *
   * @param frame         the GUI frame
   * @param inputHandler  the controller for handling input
   * @param portfolioName the name of portfolio
   * @param startDate     the start date to performance
   * @param endDate       the end date to performance
   * @param performance   a map of performance
   * @param scale         the scale of performance
   * @param isFinish      finish current performance is true. Otherwise, false.
   * @param errorMessage  the error message will show to the user
   */
  public PerformancePageSwingView(JFrame frame, InputHandler inputHandler, String portfolioName,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  Map<String, Integer> performance,
                                  String scale,
                                  boolean isFinish,
                                  String errorMessage) {
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.portfolioName = portfolioName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.performance = performance;
    this.scale = scale;
    this.isFinish = isFinish;
    this.errorMessage = errorMessage;
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

    JPanel panelStartDate = new JPanel();
    panelStartDate.setLayout(new FlowLayout());
    panelStartDate.setSize(500, 20);
    JLabel startLabel = new JLabel("Start Date:");
    panelStartDate.add(startLabel);
    JTextField startTextArea = new JTextField(10);
    panelStartDate.add(startTextArea);

    JPanel panelEndDate = new JPanel();
    panelEndDate.setLayout(new FlowLayout());
    panelEndDate.setSize(500, 20);
    JLabel endLabel = new JLabel("End Date:");
    panelEndDate.add(endLabel);
    JTextField endTextArea = new JTextField(10);
    panelEndDate.add(endTextArea);

    JPanel panelShowButton = new JPanel();
    panelShowButton.setLayout(new FlowLayout());
    JButton showButton = new JButton("show performance");
    showButton.addActionListener(e -> inputHandler.handleInput(startTextArea.getText() +
            "," + endTextArea.getText()));
    panelShowButton.add(showButton);

    JPanel panelShow = new JPanel();
    panelShowButton.setLayout(new FlowLayout());
    if (performance != null && isFinish) {
      JLabel performaceLabel = new JLabel("Performance of portfolio " + portfolioName
              + " from " + startDate + " to " + endDate);
      JTextArea showArea = new JTextArea();
      for (var entry : performance.entrySet()) {
        showArea.append(entry.getKey() + "*".repeat(entry.getValue()) + "\n");
      }
      showArea.append("scale: \n" + scale);
      panelShow.add(performaceLabel);
      panelShow.add(showArea);
    }

    Box vBox = Box.createVerticalBox();
    vBox.setPreferredSize(new Dimension(500, 500));
    vBox.add(panelBack);
    vBox.add(error);
    vBox.add(panelStartDate);
    vBox.add(panelEndDate);
    vBox.add(panelShowButton);
    vBox.add(panelShow);

    frame.setContentPane(vBox);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
