package gameexample;

/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import sun.audio.*;
import javax.sound.sampled.*;
import java.net.URL;

/**
 *
 * @author ${user}
 */
public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    int x = 100;
    int y = 500;
    //mouse variables
    int mouseX = 0;
    int mouseY = 0;
    boolean buttonPressed = false;
    ArrayList<Rectangle> blocks = new ArrayList<>();

    //another player
    Rectangle player = new Rectangle(100, 50000, 100, 100);
    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    int gravity = 0;
    int frameCount = 0;
    //keyboard variables
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    boolean attack = false;
    boolean titleScreen = false;
    boolean levelname = false;
    boolean enemy;
    private Timer timer;
    
    BufferedImage knux = loadImage("knux.png");
    BufferedImage background = loadImage("greenhill.png");
    BufferedImage hit = loadImage("knucklesgettinghit1.png");
    BufferedImage BItitleScreen = loadImage("TitleScreen.png");
    BufferedImage platform = loadImage("greenhillobj.png");
    BufferedImage knuxRun1 = loadImage("knuckles_running1.png");
    BufferedImage knuxRun2 = loadImage("knuckles_running2.png");
    BufferedImage knuxRun3 = loadImage("knuckles_running3.png");
    BufferedImage knuxRun4 = loadImage("knuckles_running4.png");
    BufferedImage knuxRun5 = loadImage("knuckles_running5.png");
    BufferedImage knuxRun6 = loadImage("knuckles_running6.png");
    BufferedImage knuxRun7 = loadImage("knuckles_running7.png");
    BufferedImage knuxRun8 = loadImage("knuckles_running8.png");
    BufferedImage knuxJump5 = loadImage("knuckles_jumping5.png");
    BufferedImage knuxJump2 = loadImage("knuckles_jumping2.png");
    BufferedImage ground = loadImage("ground.png");
    BufferedImage motorbug = loadImage("motorbug.png");
    BufferedImage levelName = loadImage("levelName.png");
    
    BufferedImage[]running1 = new BufferedImage[1];
    BufferedImage[]running2 = new BufferedImage[2];
    BufferedImage[]running3 = new BufferedImage[3];
    BufferedImage[]running4 = new BufferedImage[4];
    BufferedImage[]running5 = new BufferedImage[5];
    BufferedImage[]running6 = new BufferedImage[6];
    BufferedImage[]running7 = new BufferedImage[7];
    BufferedImage[]running8 = new BufferedImage[8];
    
    
    
    
    
    
    int camx = 0;
    int camy = 0;

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading " + filename);
        }
        return img;
    }
    
    
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        g.clearRect(mouseX - camx, 0, 10, 50);

        if (buttonPressed) {
            buttonPressed = false;
        }

        

        for (Rectangle block : blocks) {
           
        }
        //platforms
        g.drawImage(platform, 100 - camx, 100, 400, 400, null);
        g.drawImage(platform, 200 - camx, 100, 400, 400, null);
        g.drawImage(platform, 400 - camx, 100, 400, 400, null);
        g.drawImage(platform, 500 - camx, 100, 400, 400, null);
        g.drawImage(platform, 600 - camx, 100, 400, 400, null);
        g.drawImage(platform, 700 - camx, 100, 400, 400, null);
        g.drawImage(ground, 1000 - camx, 550, 300, 100, null);
        g.drawImage(ground, 50 - camx, 550, 300, 100, null);
        g.drawImage(ground, 1200 - camx, 550, 300, 100, null);
        g.drawImage(ground, 1400 - camx, 550, 300, 100, null);
        g.drawImage(ground, 1600 - camx, 550, 300, 100, null);
        g.drawImage(ground, 1800 - camx, 550, 300, 100, null);
        g.drawImage(ground, 2000 - camx, 550, 300, 100, null);
        g.drawImage(ground, 2200 - camx, 550, 300, 100, null);
        g.drawImage(platform, 300 - camx, 100, 400, 400, null);
        g.drawImage(platform, 1400 - camx, 100, 400, 400, null);
        g.drawImage(platform, 1500 - camx, 100, 400, 400, null);
        
        
        
        

        g.drawImage(hit, player.x - camx, player.y, player.width, player.height, null);

        //titleScreen
        if (titleScreen == false) {
            g.drawImage(BItitleScreen, 0, 0, WIDTH, HEIGHT, null);
        
        }
        if (titleScreen == true){
            g.drawImage(levelName, 120 - camx, 90, 300, 50, null);
            
        }
        
        if (inAir == true) {

            g.drawImage(knuxJump5, player.x - camx, player.y, 100, 100, null);

        }
        

        // GAME DRAWING ENDS HERE
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {   //add blocks

        //END BLOCKS
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        blocks.add(new Rectangle(100, 560, 1000, 55));
        blocks.add(new Rectangle(1100, 560, 2000, 55));
        blocks.add(new Rectangle(260, 445, 100, 55));
        blocks.add(new Rectangle(360, 445, 100, 55));
        blocks.add(new Rectangle(460, 445, 100, 55));
        blocks.add(new Rectangle(560, 445, 100, 55));
        blocks.add(new Rectangle(660, 445, 200, 55));
        blocks.add(new Rectangle(760, 445, 200, 55));
        blocks.add(new Rectangle(1600, 445, 120, 55));
        

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            if (titleScreen == true) {

                x = mouseX;
                y = mouseY;
                if (left) {
                    moveX = -5;
                } else if (right) {
                    moveX = 5;

                } else {
                    moveX = 0;
                }

                //gravity pulling player down
                moveY = moveY + gravity;

                //jumping
                if (jump && prevJump == false && !inAir) {
                    //make a big change in Y direction
                    moveY = -20;
                    inAir = true;
                }
                prevJump = jump;

                //move player
                player.x = player.x + moveX;
                player.y = player.y + moveY;
                
                //if the player moves far behind the screen
               
                //if feet of player become lower than screen
                if (player.y + player.height > HEIGHT) {
                    player.y = HEIGHT - player.height;
                    moveY = 0;
                    inAir = false;
                }
                //go through all blocks
                for (Rectangle block : blocks) {
                    if (player.intersects(block)) {
                        Rectangle intersection = player.intersection(block);
                        if (intersection.width < intersection.height) {
                            //if player on left
                            if (player.x < block.x) {
                                player.x = player.x - intersection.width;
                            } else {
                                player.x = player.x + intersection.width;
                            }
                        } else if (player.y > block.y) {
                            player.y = player.y + intersection.height;
                            moveY = 0;
                        } else {
                            player.y = player.y - intersection.height;
                            moveY = 0;
                            inAir = false;
                        }
                    }
                }

                if (player.x > WIDTH / 2) {
                    camx = player.x - WIDTH / 2;
                }

               
                
                
                
                
            }
            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
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
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (key == KeyEvent.VK_UP) {
            jump = true;
        } else if (key == KeyEvent.VK_SPACE) {
            attack = true;

        } else if (key == KeyEvent.VK_SHIFT) {
            titleScreen = true;
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_UP) {
            jump = false;
        } else if (key == KeyEvent.VK_SPACE) {
            attack = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
}
