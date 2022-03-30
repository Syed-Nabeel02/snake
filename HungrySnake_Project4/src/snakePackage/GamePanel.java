package snakePackage;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class GamePanel extends JPanel implements ActionListener {

    Timer timer;                    //Creating timer object from Timer Class
    Random random;
    int foodEaten;
    int foodX;                     // X coordinate of Food
    int foodY;                     // Y coordinate of Food/
    final int WidthOfScreen = 700;
    final int HeightOfScreen = 500;
    final int gridSize = 20;
    final int totalGrids = (WidthOfScreen * HeightOfScreen)/ gridSize;   
    int DELAY = 45;                                   //time delay when timer started
    final int x[] = new int[totalGrids];              //All x coordinates of Snake position
    final int y[] = new int[totalGrids];              //All y coordinates of Snake position
    int bodyParts = 6;
    char direction = 'D';
    boolean running = false;




    public GamePanel(){                                                      //creating the panel screen

        random = new Random();
        this.setPreferredSize(new Dimension(WidthOfScreen, HeightOfScreen));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyDirections());                         //Will allow us to use keyboard
        startGame();

    }

    public void newFood(){                     //Setting random positions for x and y coordinates of the apple
        foodX = random.nextInt((int)(WidthOfScreen / gridSize))* gridSize;
        foodY = random.nextInt((int)(HeightOfScreen / gridSize))* gridSize;
      

    }

    public void paintComponent(Graphics g){
            super.paintComponent(g);              //paint all the properties of the panel
            draw(g);
    }

    public void draw(Graphics g){
//        for(int i = 0; i< HeightOfScreen / gridSize; i++){
//            g.drawLine(i* gridSize,0,i* gridSize, HeightOfScreen);     //for making grids
//            g.drawLine(0,i* gridSize, WidthOfScreen,i* gridSize);
//        }

        g.setColor(Color.red);
        g.fillOval(foodX, foodY, gridSize, gridSize);         //food colored grid

        for(int i = 0; i< bodyParts;i++) {                    // Coloring body of the snake

            g.setColor(Color.green);
            g.fillOval(x[i], y[i], gridSize, gridSize);
        }

    }



    public void move(){
        for(int i = bodyParts;i>0;i--) {//Traversing through the body of the snake
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {          //Setting direction
            case 'U':
                y[0] = y[0] - gridSize;
                break;
            case 'D':
                y[0] = y[0] + gridSize;
                break;
            case 'L':
                x[0] = x[0] - gridSize;
                break;
            case 'R':
                x[0] = x[0] + gridSize;
                break;
        }

    }

    public void checkFoodEaten(){
        if((x[0] == foodX) && (y[0] == foodY)) {
            bodyParts+=3;
            foodEaten++;
            DELAY-=1;
            newFood();
        }

    }

    public void checkCollisions(){
        //checks if head collides with body
        for(int i = bodyParts;i>0;i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

            //check if head touches left border
            if (x[0] < 0) {
                running = false;
            }
            //check if head touches right border
            if (x[0] > WidthOfScreen) {
                running = false;
            }
            //check if head touches top border
            if (y[0] < 0) {
                running = false;
            }
            //check if head touches bottom border
            if (y[0] > HeightOfScreen) {
                running = false;
            }

            if (!running) {
                timer.stop();
            }

    }
    public void gameOver(){
        if(!running) {
            //Shows Message after Game is over
            JOptionPane.showMessageDialog(null," You had "+foodEaten+" food bytes \n"+"Your score is: "+ foodEaten * 3,"Game Over" ,JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void startGame(){
        newFood();
        running =true;
        timer = new Timer(DELAY,this);//object from the Timer class will start Action Lister
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkFoodEaten();
            checkCollisions();
        }
        repaint();                       //Calls the paintcomponet function to draw again on screen
        gameOver();
    }

    public class KeyDirections extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case 37:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case 39:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case 38:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case 40:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}
