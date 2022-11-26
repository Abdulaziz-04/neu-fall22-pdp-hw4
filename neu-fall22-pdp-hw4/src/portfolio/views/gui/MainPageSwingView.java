package portfolio.views.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import portfolio.controllers.InputHandler;
import portfolio.views.View;

public class MainPageSwingView implements View {

  private final JFrame frame;
  private final InputHandler inputHandler;

  private final String errorMessage;
  private final boolean isInitFail;

  public MainPageSwingView(JFrame frame, InputHandler inputHandler, String errorMessage, boolean isInitFailed){
    this.frame = frame;
    this.inputHandler = inputHandler;
    this.errorMessage = errorMessage;
    this.isInitFail = isInitFailed;
  }

  @Override
  public void render() {
    JPanel panel = new JPanel();

    JButton button1 = new JButton("Create a flexible portfolio");
    button1.addActionListener(e -> inputHandler.handleInput("1"));

    JButton button2 = new JButton("Load portfolio");
    button2.addActionListener(e -> inputHandler.handleInput("2"));

    panel.setSize(500,500);
    panel.add(button1);
    panel.add(button2);

    frame.setContentPane(panel);
    frame.repaint();
    frame.revalidate();
  }
}
