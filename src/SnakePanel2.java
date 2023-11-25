import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;


public class SnakePanel2 extends JPanel implements KeyListener,ActionListener,MouseListener{
    private class Tile{
        int x;
        int y;
        Image image;

         Tile(int x, int y){
            this.x = x;
            this.y = y;

        }
        
        public Image getImage(){
            return image;
        }
        public void setImage(Image image){
            this.image = image;
        }


    }   
    Tile snakeHead;
        Tile food;

        Image background;
        

        int boardwidth;
        int boardheight;
        int tileSize = 25;
        Random random;
        Image foodImage;
        //Gamelogic
        Timer gameLoop;
        int xVelocity = 1;
        int yVelocity = 0;
        ArrayList<Tile> snakeBody;
        boolean gameOver = false;
        JRadioButton retry;
        JRadioButton pause;
        JRadioButton exit;
        ImageIcon retryImage;
        ImageIcon pauseImage;
        ImageIcon exitImage;
        
        
        
       

        
    SnakePanel2(int boardwidth,int boardheight){
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        
        addKeyListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.black);
        

        snakeHead = new Tile(5,5);
        snakeHead.setImage(new ImageIcon("Snake.png").getImage());

        food = new Tile(10, 10);
        food.setImage(new ImageIcon("APPLE.png").getImage());

        random =  new Random();
        //Background
        background = new ImageIcon("BLACKBG.png").getImage();
        placeFood();
        gameLoop = new Timer(100,this);
        gameLoop.start();
        //Every 100milisecond the timer would perform action listener this
        snakeBody = new ArrayList<Tile>();
        //Button for retty
        retry = new JRadioButton("");
        retry.setBounds(100,0,40,40);
        retry.addActionListener(this);
        retry.addMouseListener(this);
        retry.setVisible(true);
        retryImage = new ImageIcon("RETRY.png");
        retry.setIcon(retryImage);
        add(retry);
       
        pause = new JRadioButton("");
        pause.setBounds(160,0,40,40);
        pause.addActionListener(this);
        pause.addMouseListener(this);
        pause.setVisible(true);
        pauseImage = new ImageIcon("PAUSE.png");
        pause.setIcon(pauseImage);
        add(pause);

        exit= new JRadioButton("");
        exit.setBounds(220,0,40,40);
        exit.addActionListener(this);
        exit.addMouseListener(this);
        exit.setVisible(true);
        exitImage = new ImageIcon("EXIT.png");
        exit.setIcon(exitImage);
        add(exit);
        

    }

    public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);

    }
    public void draw(Graphics g){
        //grid
      
        /*  for (int i = 0; i < boardwidth/tileSize; i++ ){
            
            g.drawLine(i *tileSize,0, i *tileSize,boardheight);
            g.drawLine(0, i *tileSize, boardwidth, i*tileSize);
        }
        */
        //food
        Graphics2D g2d = (Graphics2D) g;
        //background
        g2d.drawImage(background,0,0,null);
        
        g2d.drawImage(food.getImage(),food.x*tileSize,food.y*tileSize,null);
        //snake head
        g.setColor(new Color(63,155,68));
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        
    
        //snake body
        for (int i = 0; i < snakeBody.size(); i++){ //Let us know how many snake part need to be printed
            Tile snakePart = snakeBody.get(i);
            g.setColor(new Color(112,196,116));
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize,tileSize);
            
        }

        if(gameOver){
            g.setColor(Color.red);
            g.setFont(new Font("MV BOLI",Font.PLAIN, 50));
            g.drawString("GAME OVER",300,300);
           
        }
        g.setColor(Color.white);
        g.setFont(new Font("MV BOLI",Font.BOLD,20));
        g.drawString("Score: " + String.valueOf(snakeBody.size()),tileSize, tileSize);

    
    
    
    
    }
    public void placeFood(){
        food.x = random.nextInt(boardwidth/tileSize);
        food.y = random.nextInt(boardheight/tileSize);



    }
    public void actionPerformed(ActionEvent e){
       
            move();
            repaint();
            
     if(gameOver){
    gameLoop.stop();
    }
    
        
    }

    
          
            
    public boolean collision(Tile tile1, Tile tile2){
        /*public void collision(Tile tile1, Tile tile2){
         * return tile1.x == tile2.x && tile1.y==tile2.y
         * }
         * then on the public actionPerformed(ActionEvent e){
         * if (collision(snakeHead,food))
         *      snakeBody.add(new Tile(food.x,food.y))
         *      placefood();
         * }
         * 
         * 
         */
        return tile1.x == tile2.x && tile1.y == tile2.y;
       

    }
    
    public void move(){
            //eat food
            if (collision(snakeHead, food)){
                snakeBody.add(new Tile(food.x,food.y));
                placeFood();

            }
                  
        //snakeBody
            for(int i = snakeBody.size()-1; i >= 0; i-- ){
                Tile snakePart = snakeBody.get(i);
                if(i ==0){
                    //first member of the snake body will move to the position of snakeHead
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;

                }
                else{
                    Tile prevSnakePart = snakeBody.get(i-1);
                    snakePart.x = prevSnakePart.x;
                    snakePart.y = prevSnakePart.y;

                }
            }
            //snakeHead
            snakeHead.x += xVelocity;
            snakeHead.y +=  yVelocity;
            for (int i = 0; i < snakeBody.size(); i++){
                Tile snakePart = snakeBody.get(i);
                
                if(collision(snakePart,snakeHead)){
                    gameOver = true;
                    
                    
                }
                
            }
           
            if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardwidth){
                gameOver = true;

            }
            else if(snakeHead.y*tileSize <0 || snakeHead.y*tileSize > boardheight){
                gameOver = true;
            }

    }
    @Override
    public void keyTyped(KeyEvent e){
           


    }
    //The keys not working after retry;
    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        //Adds tile after click

        if (keyCode == KeyEvent.VK_D) {
           xVelocity = 1;
           yVelocity = 0;
        } else if (keyCode == KeyEvent.VK_A) {
              xVelocity = -1;
             yVelocity = 0;
        } else if (keyCode == KeyEvent.VK_S) {
            xVelocity = 0;
            yVelocity = 1;
        } else if (keyCode == KeyEvent.VK_W) {
            xVelocity = 0;
            yVelocity = -1;
        }
        

    }
            

        
    
    @Override
    public void keyReleased(KeyEvent e){



    }
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == retry) {
            resetGame(); 
        }
        else if(e.getSource() == pause){
            gameLoop.stop();
            requestFocusInWindow();//Allows us to focus once again the window
        }
         else if(e.getSource() == exit){
            gameLoop.start();
            requestFocusInWindow();//Allows us to focus once again the window
        }
            
        
    }

    @Override
    public void mousePressed(MouseEvent e) { 


    }

    @Override
    public void mouseReleased(MouseEvent e) { 

     }

    @Override
    public void mouseEntered(MouseEvent e) { 

     }

    @Override
    public void mouseExited(MouseEvent e) { 

     }
     
        public void resetGame() {
            
            snakeHead = new Tile(5, 5);
            snakeBody.clear();
            placeFood();
            xVelocity = 1;
            yVelocity = 0;
            gameOver = false;
            gameLoop.start();
            requestFocusInWindow();//Allows us to focus once again the window
            repaint();
        }
        
        
    
   
}






    





