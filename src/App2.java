
import javax.swing.*;
public class App2 {
    public static void main(String[] args) throws Exception {
        int boardheight = 600;
        int boardwidth = 900;
        ImageIcon image = new ImageIcon("Charmander.png");
        
        
        JFrame frame = new JFrame("SnakeGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardwidth,boardheight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        SnakePanel2 snakePanel = new SnakePanel2(boardwidth,boardheight);
        frame.add(snakePanel);
        frame.pack();
        frame.setIconImage(image.getImage());
        snakePanel.requestFocus();
        frame.setVisible(true);
        
    }
    
        
        
    
}
