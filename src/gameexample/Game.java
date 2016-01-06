package gameexample;

/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author ${user}
 */


public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener{

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    int x = 100;
    int y = 500;
    
    //mouse variables
    int mouseX = 0;
    int mouseY = 0;
    
    boolean buttonPressed = false;
    
    ArrayList<Rectangle> blocks = new ArrayList<>();
    
    //another player
    Rectangle player = new Rectangle(100, 500, 50, 50);
    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    
    int gravity = 1;
    
    //keyboard variables
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        
        g.setColor(Color.red);
        
        for(Rectangle block: blocks){
            g.fillRect(block.x, block.y, block.width, block.height);
        }
        
        g.fillRect(x,y,50,50);
        g.fillRect(player.x, player.y, player.width, player.height);
        if(buttonPressed){
            g.setColor(Color.green);
            g.fillRect(300, 500, 500,300);
        }
        
        
        
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {   //add blocks
        blocks.add(new Rectangle(400, 450, 100, 50));
        blocks.add(new Rectangle(500, 400, 50, 50));
        
        
        
        //END BLOCKS
        
        
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            x = mouseX;
            y = mouseY;
            if(left){
                moveX = -2;
            }else if(right){
                moveX = 2;
                    
            
}else{
                moveX = 0;
            }
            
            
            //gravity pulling player down
            moveY = moveY + gravity;
            
            //jumping
            if(jump && prevJump ==false && !inAir){
                //make a big change in Y direction
                moveY = -20;
                inAir = true;
            }
            prevJump = jump;
            
            
            
            
            //move player
            player.x = player.x + moveX;
            player.y = player.y + moveY;
            
            //if feet of player become lower than screen
            if(player.y + player.height > HEIGHT){
                player.y = HEIGHT - player.height;
                moveY = 0;
                inAir = false;
            }
            //go through all blcoks
            for(Rectangle block: blocks){
                if(player.intersects(block)){
                    Rectangle intersection = player.intersection(block);
                    if(intersection.width < intersection.height){
                        //if player on left
                        if(player.x < block.x){
                          player.x = player.x - intersection.width;
                      }else
                            player.x = player.x + intersection.width;
                    } else{
                        if(player.y > block.y){
                            player.y = player.y + intersection.height;
                            moveY = 0;
                        }else{
                            player.y = player.y - intersection.height;
                            moveY = 0;
                            inAir = false;
                        }
                    }
                }
            }
            
            
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        
        frame.addKeyListener(game); //keyboard
        game.addMouseListener(game);
        game.addMouseMotionListener(game); 
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            left = true;
        }else if(key == KeyEvent.VK_RIGHT){
            right = true;
        }else if(key == KeyEvent.VK_SPACE){
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
         int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            left = false;
    }else if(key == KeyEvent.VK_RIGHT){
            right = false;
        }else if(key == KeyEvent.VK_SPACE){
            jump = false;
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       mouseX = e.getX();
       mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
       if(e.getButton() == MouseEvent.BUTTON1){
           buttonPressed = true;
       }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            buttonPressed = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
