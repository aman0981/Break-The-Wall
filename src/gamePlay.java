import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;    //ActionListener is used for moving the ball
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;      //keyListener is used for detecting Arrows key

public class gamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play= false;
    private int score =0;

    private int totalBricks = 21;

    private Timer timer;          //Timer class for setting the time of the ball
    private int delay= 1;          //delay for the ball

    private int playerX = 310;       //starting position of slider
                                     // X axis and Y axis of the ball are
    private int ballposX= 120;       // starting position of the ball
    private int ballposY= 350;
    private int ballXdir = -1;       //direction of the ball on x axis
    private int ballYdir= -2;

    private mapGenerator map;

    public gamePlay(){
        map= new mapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D) g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(683, 0, 3, 592);


        //scores
        g.setColor(Color.CYAN);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 520, 40);


        //the paddle

        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <=0){
            play= false;
            ballXdir =0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won,\n Your Final Score: "+score, 190,300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart the Game", 290, 350);
        }

        if(ballposY >570){
            play= false;
            ballXdir =0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over,\n Your Final Score: "+score, 190,300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart the Game", 300, 400);
        }

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {      //This method is automatically called
         timer.start();
         if(play){     //if play is true   it will be true when right or left arrow is pressed

             //For detecting the intersection of the ball with the paddle  by creating rectangle shape around ball and player
             if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX, 550,100,8))){
                 ballYdir = -ballYdir;
             }

            A: for(int i=0; i<map.map.length; i++){
                 for(int j=0; j<map.map[0].length; j++){
                     if(map.map[i][j] > 0){
                         int brickX = j* map.brickWidth + 80;
                         int brickY = i* map.brickHeight + 50;
                         int brickWidth = map.brickWidth;
                         int brickHeight = map.brickHeight;

                         //Rectangle is been created around the brick and ball to detect the collision between them

                         Rectangle rect =new Rectangle(brickX, brickY, brickWidth, brickHeight);
                         Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                         Rectangle brickRect = rect;

                         if(ballRect.intersects(brickRect)){
                             map.setBrickValue(0, i, j);
                             totalBricks--;
                             score += 5;

                             if(ballposX +19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width){
                                 ballXdir = -ballXdir;
                             } else {
                                 ballYdir = -ballYdir;
                             }

                             break A;   //using only break will bring to inner for loop that's why assigning outer loop
                                       // as A then using break A will move to outer for loop
                         }

                     }
                 }
             }

             //For detecting the intersection of ball with the Borders
             ballposX += ballXdir;
             ballposY += ballYdir;
             if(ballposX < 0){                //For left border
                 ballXdir = -ballXdir;
             }
             if(ballposY < 0){                  // For top border
                 ballYdir = -ballYdir;
             }
             if(ballposX > 670){                 // For right border
                 ballXdir = -ballXdir;
             }
         }

         //The need to call this method is
        // when the value of playerX is incremented or decremented then no change is shown in paddle because this
        //method is reDrawing the paddle again and again
        // similarly other code in paint method is also executed again and again

         repaint();                           //this will call paint method again and again
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {               //detects Arrow key
        if(e.getKeyCode()== KeyEvent.VK_RIGHT) {          //for right arrow key
            if(playerX >= 600){
                playerX = 600;            //Ensuring that the slider doesn't go outside the panel
            }else {
                moveRight();               //when right arrow key will be entered playerX will be incremented
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){           //for left arrow key
            if(playerX < 10){                 //when left arrow is pressed repeatedly then playerX value will be decremented
                playerX = 10;                //and when the value will be less than 10 then again slider will get position at playerX= 10
            } else {
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir= -2;
                playerX= 310;
                score = 0;
                totalBricks = 21;
                map = new mapGenerator(3,7);

                repaint();
            }
        }
    }
    public void moveRight(){
        play= true;
        playerX+=20;
    }
    public void moveLeft(){
        play= true;
        playerX-=20;
    }


}
