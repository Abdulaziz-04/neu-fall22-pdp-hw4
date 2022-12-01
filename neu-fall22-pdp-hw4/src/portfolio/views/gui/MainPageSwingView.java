package portfolio.views.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import portfolio.controllers.InputHandler;
import portfolio.views.View;

/**
 * This is a class that represent a GUI main menu view page, which implement the View interface.
 */
public class MainPageSwingView implements View {

  private final JFrame frame;
  private final InputHandler inputHandler;

  private final String errorMessage;
  private final boolean isInitFail;

  /**
   * This is a constructor to construct a GUI main page view, which contains the frame,
   * inputHandler,error message and isInitFailed.
   *
   * @param frame        the frame for GUI
   * @param inputHandler the controller for handling input
   * @param errorMessage the error message will show to the user
   * @param isInitFailed initialize fail or not
   */
  public MainPageSwingView(JFrame frame, InputHandler inputHandler,
                           String errorMessage, boolean isInitFailed) {
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.errorMessage = errorMessage;
    this.isInitFail = isInitFailed;
  }

  @Override
  public void render() {
    JPanel panel = new JPanel();

    JButton button1 = new JButton("Create a flexible portfolio");
    button1.addActionListener(e -> inputHandler.handleInput("2"));

    JButton button2 = new JButton("Load portfolio");
    button2.addActionListener(e -> inputHandler.handleInput("3"));

    JButton button3 = new JButton("Create dollar cost average portfolio");
    button3.addActionListener(e -> inputHandler.handleInput("4"));

    panel.setSize(500, 500);
    panel.add(button1);
    panel.add(button2);
    panel.add(button3);

    frame.setSize(600, 600);
    frame.setContentPane(panel);
    frame.repaint();
    frame.revalidate();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
