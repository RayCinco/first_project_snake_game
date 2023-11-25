
import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardheight = 600;
        int boardwidth = 600;
        
        
        
        JFrame frame = new JFrame("SnakeApple");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardwidth,boardheight);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        SnakePanel snakePanel = new SnakePanel(boardwidth,boardheight);
        frame.add(snakePanel);
        frame.pack();
        snakePanel.requestFocus();
    }
}
