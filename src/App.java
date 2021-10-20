import javax.swing.UIManager;

public class App {
  public static void main(String[] args) {
    // windows风格
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
    gui.startgui();
  }
}