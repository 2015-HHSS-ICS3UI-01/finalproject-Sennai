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

import javax.sound.sampled.*;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import sun.audio.AudioPlayer;

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
    public AudioPlayer bgMusic;
    ArrayList<Rectangle> blocks = new ArrayList<>();
    ArrayList<Rectangle> enemies = new ArrayList<>();
    //another player
    Rectangle player = new Rectangle(100, 50000, 100, 100);

    int moveX = 0;
    int moveY = 0;
    boolean inAir = false;
    int gravity = 1;
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
    boolean enemy = false;
    boolean greenHill;
    boolean gameOver;
    boolean restart = false;
    private Timer timer;
    BufferedImage knux = loadImage("knux.png");
    BufferedImage background = loadImage("greenhill.png");
    BufferedImage hit = loadImage("knucklesgettinghit1.png");
    BufferedImage BItitleScreen = loadImage("TitleScreen.png");
    BufferedImage platform = loadImage("greenhillobj.png");
    BufferedImage knuxStandR = loadImage("knuckles_standingright.png");
    BufferedImage knuxStand = loadImage("knuckles_standing.png");
    BufferedImage knuxRun1 = loadImage("knuckles_running1.png");
    BufferedImage knuxRun2 = loadImage("knuckles_running2.png");
    BufferedImage knuxRun3 = loadImage("knuckles_running3.png");
    BufferedImage knuxRun4 = loadImage("knuckles_running4.png");
    BufferedImage knuxRun5 = loadImage("knuckles_running5.png");
    BufferedImage knuxRun6 = loadImage("knuckles_running6.png");
    BufferedImage knuxRun7 = loadImage("knuckles_running7.png");
    BufferedImage knuxRun8 = loadImage("knuckles_running8.png");
    BufferedImage knuxRun1R = loadImage("knuckles_running1right.png");
    BufferedImage knuxRun2R = loadImage("knuckles_running2right.png");
    BufferedImage knuxRun3R = loadImage("knuckles_running3right.png");
    BufferedImage knuxRun4R = loadImage("knuckles_running4right.png");
    BufferedImage knuxRun5R = loadImage("knuckles_running5right.png");
    BufferedImage knuxRun6R = loadImage("knuckles_running6right.png");
    BufferedImage knuxRun7R = loadImage("knuckles_running7right.png");
    BufferedImage knuxRun8R = loadImage("knuckles_running8right.png");
    BufferedImage knuxJump5 = loadImage("knuckles_jumping5.png");
    BufferedImage knuxJump4 = loadImage("knuckles_jumping4.png");
    BufferedImage knuxJump3 = loadImage("knuckles_jumping3.png");
    BufferedImage knuxJump1 = loadImage("knuckles_jumping1.png");
    BufferedImage knuxJump2 = loadImage("knuckles_jumping2.png");
    BufferedImage ground = loadImage("ground.png");
    BufferedImage motorbug = loadImage("motorbug.png");
    BufferedImage levelName = loadImage("levelName.png");
    BufferedImage knuxJump1R = loadImage("knuckles_jumping1right.png");
    BufferedImage knuxJump2R = loadImage("knuckles_jumping2right.png");
    BufferedImage knuxJump3R = loadImage("knuckles_jumping3right.png");
    BufferedImage knuxJump4R = loadImage("knuckles_jumping4right.png");
    BufferedImage bridge = loadImage("bridge.png");
    BufferedImage tree = loadImage("tree.png");
    //number of frames all sprites can run at
    int frameRun = 0;
    //array of Knuckles sprites that are part of his running animation
    BufferedImage[] running = {knuxRun1, knuxRun2, knuxRun3, knuxRun4, knuxRun5, knuxRun6, knuxRun7, knuxRun8};
    BufferedImage[] runningRight = {knuxRun1R, knuxRun2R, knuxRun3R, knuxRun4R};
    BufferedImage[] jumping = {knuxJump1, knuxJump2, knuxJump3, knuxJump4, knuxJump5};
    BufferedImage[] jumpingRight = {knuxJump1R, knuxJump2R, knuxJump3R, knuxJump4, knuxJump5};

    //sets up a buffered image for all sprites
    BufferedImage playerImg;
    BufferedImage enemyImg;

    int camx = 0;

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
        
        if (greenHill = true) {
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
            for (Rectangle block : blocks) {
            }
            //platforms
            g.drawImage(tree, 2460 - camx, 180, 400, 400, null);
            g.drawImage(tree, 1060 - camx, 180, 400, 400, null);
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
            g.drawImage(ground, 4000 - camx, 550, 300, 100, null);
            g.drawImage(ground, 4200 - camx, 550, 300, 100, null);
            g.drawImage(ground, 4400 - camx, 550, 300, 100, null);
            g.drawImage(platform, 300 - camx, 100, 400, 400, null);
            g.drawImage(platform, 1400 - camx, 100, 400, 400, null);
            g.drawImage(platform, 1500 - camx, 100, 400, 400, null);
            g.drawImage(platform, 1500 - camx, 100, 300, 100, null);
            g.drawImage(platform, 2360 - camx, 210, 400, 400, null);
            g.drawImage(platform, 2460 - camx, 210, 400, 400, null);
            g.drawImage(platform, 2560 - camx, 210, 400, 400, null);
            g.drawImage(platform, 2700 - camx, 50, 400, 400, null);
            g.drawImage(platform, 2800 - camx, 50, 400, 400, null);
            g.drawImage(platform, 3000 - camx, 50, 400, 400, null);
            g.drawImage(platform, 3100 - camx, 50, 400, 400, null);
            g.drawImage(platform, 3500 - camx, 50, 400, 400, null);
            g.drawImage(platform, 3600 - camx, 50, 400, 400, null);
            g.drawImage(platform, 3800 - camx, -150, 400, 400, null);
            g.drawImage(platform, 3900 - camx, -150, 400, 400, null);

        }
        //sprite actions for knuckles
        if (!inAir && !left && !right && !jump) {
            playerImg = knuxStandR;
        }
//        below are commands for how the sprite should be visually displayed
//        depending on what the player has done.(turned left, right)i.e.
        if (left) {
            playerImg = running[frameRun / 2];

        }
        if (right && !left && !jump) {
            playerImg = runningRight[frameRun / 2];
        }

        if (inAir) {
            playerImg = jumping[frameRun / 2];
        }

        if (inAir && right && !left) {
            playerImg = jumpingRight[frameRun / 2];
        }
        if (player.x < 50) {
            left = false;
            right = false;
        }

        //titleScreen
        if (titleScreen == false) {
            g.drawImage(BItitleScreen, 0, 0, WIDTH, HEIGHT, null);

        }//title of level
        if (titleScreen == true) {
            g.drawImage(levelName, 120 - camx, 90, 300, 50, null);

        }
        //when game is beaten player wins
        if (player.x == 4170) {
        g.drawString("You Win! Thanks for Playing!", 400, 400); 
        }
        //same message as mentioned above
        if (player.x == 4170) {
            System.out.println("You Win! Thanks for playing!"); 
        }

        g.drawImage(playerImg, player.x - camx, player.y, player.width, player.height, null);
        g.drawImage(enemyImg, 2000 - camx, 300, player.width, player.height, null);

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
        //all blocks used for collision detection
        if (greenHill = true) {
            blocks.add(new Rectangle(100, 560, 1000, 55));
            blocks.add(new Rectangle(1100, 560, 1700, 55));
            blocks.add(new Rectangle(260, 445, 100, 55));
            blocks.add(new Rectangle(360, 445, 100, 55));
            blocks.add(new Rectangle(460, 445, 100, 55));
            blocks.add(new Rectangle(560, 445, 100, 55));
            blocks.add(new Rectangle(660, 445, 200, 55));
            blocks.add(new Rectangle(760, 445, 200, 55));
            blocks.add(new Rectangle(1600, 445, 120, 55));
            blocks.add(new Rectangle(2900, 400, 120, 55));
            blocks.add(new Rectangle(3200, 400, 120, 55));
            blocks.add(new Rectangle(3700, 400, 120, 55));
            blocks.add(new Rectangle(4000, 200, 120, 55));
            blocks.add(new Rectangle(4050, 560, 500, 200));
            blocks.add(new Rectangle(4100, 560, 200, 500));

        }
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            //titlescreen == true means when titlescreen is not displayed
            if (titleScreen == true) {

                x = mouseX;
                y = mouseY;

                //show array of running sprites when player turns left
                if (left) {
                    moveX = -8;
                    frameRun++;
                    if (frameRun >= running.length) {
                        frameRun = 0;
                    }
                    //show array of running sprites when player turns right    
                } else if (right) {
                    moveX = 8;
                    frameRun++;
                    if (frameRun >= running.length) {
                        frameRun = 0;
                    }

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

                //if the player touches the water
                if (player.y + player.height == HEIGHT) {
                    player.x = 75;
                    camx = 0;

                }

                //if feet of player become lower than screen
                if (player.y + player.height > HEIGHT) {
                    player.y = HEIGHT - player.height;
//                    moveY = 0;
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
                                left = false;
                                right = false;
                            }
                        } else if (player.y > block.y) {
                            player.y = player.y + intersection.height;
                            moveY = 0;
                        } else {
                            player.y = player.y - intersection.height;
                            moveY = 0;
                            inAir = false;
                            jump = false;
                        }
                    }
                }
                //allows the camera to follow player 
                if (player.x > WIDTH / 2) {
                    camx = player.x - WIDTH / 2;
                }//enemy collision
                for (Rectangle enemy : enemies) {
                    //if player comes in contact with enemy, player gets hit 
                    if (player.intersects(enemy)) {
                        Rectangle intersection = player.intersection(enemy);
                        if (intersection.width < intersection.height) {
                            if (player.x < enemy.x) {
                                player.x = player.x - intersection.width;
                            } else {
                                player.x = player.x + intersection.width;
                                left = false;
                                right = false;
                                playerImg = hit;
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
    //assigns keys to game actions 
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
        } else if (key == KeyEvent.VK_UP) {
            jump = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
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
