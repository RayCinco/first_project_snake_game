import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;


public class SnakePanel extends JPanel implements KeyListener,ActionListener{
    private class Tile{
        int x;
        int y;

         Tile(int x, int y){
            this.x = x;
            this.y = y;

        }

    }   Tile snakeHead;
        

        Tile food;

        int boardwidth;
        int boardheight;
        int tileSize = 25;
        Random random;
        //Gamelogic
        Timer gameLoop;
        int xVelocity = 1;
        int yVelocity = 0;
        ArrayList<Tile> snakeBody;
        boolean gameOver = false;

        
    SnakePanel(int boardwidth,int boardheight){
        this.boardwidth = boardwidth;
        this.boardheight = boardheight;
        addKeyListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.black);

        snakeHead = new Tile(5,5);
        food = new Tile(10,10);
        random =  new Random();
        placeFood();
        gameLoop = new Timer(200,this);
        gameLoop.start();
        //Every 100milisecond the timer would perform action listener this
        snakeBody = new ArrayList<Tile>();
    }

    public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);

    }
    public void draw(Graphics g){
        //grid
        for (int i = 0; i < boardwidth/tileSize; i++ ){
            
            g.drawLine(i *tileSize,0, i *tileSize,boardheight);
            g.drawLine(0, i *tileSize, boardwidth, i*tileSize);
        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);

        //snake head
        g.setColor(Color.green);
        g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        
    
        //snake body
        for (int i = 0; i < snakeBody.size(); i++){ //Let us know how many snake part need to be printed
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize,tileSize);

        }
    
    
    
    
    }
    public void placeFood(){
        food.x = random.nextInt(boardwidth/tileSize);
        food.y = random.nextInt(boardheight/tileSize);



    }
    public void actionPerformed(ActionEvent e){
            move();
            repaint();
            }
            
    public void collision(){
        /*public void collision(Tile tile1, Tile tile2){
         * return tile.x == tile2.x && tile.y==
         * }
         * then on the public actionPerformed(ActionEvent e){
         * if (collision(snakeHead,food))
         *      snakeBody.add(new Tile(food.x,food.y))
         *      placefood();
         * }
         * 
         * 
         */
        
       
        int snakeX = snakeHead.x*tileSize;
        int snakeY = snakeHead.y*tileSize;
        int foodX = food.x*tileSize;
        int foodY = food.y*tileSize;

        if(snakeX == foodX && snakeY == foodY){
            
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();

        }






    }
    public void move(){
             snakeHead.x = snakeHead.x + xVelocity;
            snakeHead.y = snakeHead.y+ yVelocity;
                 
                 collision();
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
            

             }
            




    
    @Override
    public void keyTyped(KeyEvent e){
           


    }
    @Override
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        //Adds tile after click

        if (keyCode == KeyEvent.VK_RIGHT) {
           xVelocity = 1;
           yVelocity = 0;
        } else if (keyCode == KeyEvent.VK_LEFT) {
              xVelocity = -1;
             yVelocity = 0;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            xVelocity = 0;
            yVelocity = 1;
        } else if (keyCode == KeyEvent.VK_UP) {
            xVelocity = 0;
            yVelocity = -1;
        }
        collision();
        repaint();

    }
            

        
    
    @Override
    public void keyReleased(KeyEvent e){



    }




    





}