
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.CENTER;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MovingPong implements Runnable {

    private BufferedImage ballImage;
    private BufferedImage powerup;
    JPanel panel;
    JFrame frame;
    boolean saveHighScore;
    Highscore hm;
    int x;
    int y;
    Random rand = new Random();

    boolean movePowerUp;

    int PowerX;
    //450 is the maximum and the 1 is our minimum 

    int PowerY;
    //50 is the maximum and the 50 is our minimum  

    boolean freePowerUP;    

    int greenx;
    int greeny;

    int ball1X;
    int ball1Y;
    int ball1XSpeed;
    int ball1YSpeed;
    int ball2X;
    int ball2Y;
    int ball2XSpeed;
    int ball2YSpeed;
    int ball3XSpeed;
    int ball3YSpeed;
    int seconds;
    Canvas canvas;
    BufferStrategy bufferStrategy;
    boolean running;
    int border;
    boolean powerUpImage;
    boolean powerUp;
    boolean ball1GoingDown;
    boolean ball1GoingRight;
    boolean ball2GoingDown;
    boolean ball2GoingRight;
    boolean ball3GoingDown;
    boolean ball3GoingRight;
    boolean freeballs;
    boolean powerUpColission;
    int direction;
    int notStarted;
    int up;
    int right;
    int left;
    int down;
    int GameOver;
    int PowerUpTime;
    int freeballtime;
    int powerDownTime;
    int flashCounter;
    boolean flash;
    Color brightRed;
    JButton btnCredits;
    boolean showBtnCredits;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width;
    int height;
    
    public MovingPong() {
        initVariables();
        frame = new JFrame("Soccer Evolution");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("ball.png")));
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(width-10, height-10));
        panel.setLayout(null);
        
        btnCredits = new JButton ("Credits");
        canvas = new Canvas();
        canvas.setBounds(0, 0, width, height);
        canvas.setIgnoreRepaint(true);
        panel.add(btnCredits);
        panel.add(canvas);
        btnCredits.setBounds(width/2-75/2, height-25*2, 75, 25);
        btnCredits.addActionListener(new ActionListener() { 
        public void actionPerformed(ActionEvent e) { 
            CreditsBtnPressed();
        } 
      } );
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                moveIt(evt);
            }

        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        makeRandomSpeeds2();
        makeRandomSpeeds1();
       
        

        try {
            //Get the "PowerUp" picture
            powerup = ImageIO.read(getClass().getResourceAsStream("/powerup.png"));

            //Get the "ball" picture
            ballImage = ImageIO.read(getClass().getResourceAsStream("/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveHighScore = false;
        hm = new Highscore();
    }

    
    
    public void CreditsBtnPressed (){
    Credits CreditsPanel = new Credits();
    CreditsPanel.pack();
    CreditsPanel.setVisible(true);
    }
    
    public void run() {
        int counter = 0;
        while (running = true) {
        btnCredits.setVisible(showBtnCredits);

            if (direction == GameOver) {
                if (saveHighScore == false) {
                    saveHighScore = true;
                    JPanel namepanel;
                    JFrame nameframe;

                    if (hm.getMinHighscore() < seconds) {

                        String response = JOptionPane.showInputDialog(null,
                                "What is your name?",
                                "Enter your name",
                                JOptionPane.QUESTION_MESSAGE);

                        if (response != null && !response.isEmpty()) {
                            hm.addScore(response, seconds);
                        }
                    }
                }
            }

            flashCounter++;
            
            if (flashCounter == 10){
                flashCounter  = 0;
                flash =! flash;
            }
            
            
            
            if (direction != GameOver) {
                counter++;
            }
            if (counter >= 40) {
                counter = 0;

                if (direction != notStarted) {
                    seconds++;
                }
            }

            if (direction == right) {
                x += 6;
            }
            if (direction == left) {
                x -= 6;
            }
            if (direction == down) {
                y += 6;
            }
            if (direction == up) {
                y -= 6;
            }

            if (x < 0) {
                if (powerUpColission == true) {
                direction = GameOver;
                }
                
            }
            if (x + 50 > panel.getWidth()) {
                if (powerUpColission == true) 
                direction = GameOver;
                
            }
            if (y < 0) {
                if (powerUpColission == true) 
                direction = GameOver;
            }

            if (y + 50 > panel.getHeight()) {
                if (powerUpColission == true) 
                direction = GameOver;
            }

            if (direction != notStarted && direction != GameOver) {

                
                if (ball1GoingRight == true) {
                    ball1X = ball1X + ball1XSpeed;
                }

                if (ball2GoingRight == true) {
                    ball2X = ball2X + 5;
                }

                if (ball1GoingRight == false) {
                    ball1X = ball1X - ball1XSpeed;
                }

                if (ball2GoingRight == false) {
                    ball2X = ball2X - 5;
                }

                if (ball1GoingDown == true) {
                    ball1Y = ball1Y + ball1YSpeed;
                }

                if (ball2GoingDown == true) {
                    ball2Y = ball2Y + 5;
                }

                if (ball1GoingDown == false) {
                    ball1Y = ball1Y - ball1YSpeed;
                }

                if (ball2GoingDown == false) {
                    ball2Y = ball2Y - 5;
                }

                if (freeballs == true) {

                    if (ball3GoingDown == false) {
                        greeny -= 5;
                    }

                    if (ball3GoingDown == true) {
                        greeny += 5;
                    }

                    if (ball3GoingRight == false) {
                        greenx -= 5;
                    }

                    if (ball3GoingRight == true) {
                        greenx += 5;
                    }
                }
            }

            if (ball1Y > panel.getHeight() - border) {
                ball1GoingDown = false;
                makeRandomSpeeds1();
            }

            if (ball2Y > panel.getHeight() - border) {
                ball2GoingDown = false;
                makeRandomSpeeds2();
            }

            if (ball1Y < 0) {
                ball1GoingDown = true;
                makeRandomSpeeds1();
            }

            if (ball2Y < 0) {
                ball2GoingDown = true;
                makeRandomSpeeds2();
            }

            if (ball1X > panel.getWidth() - border) {
                ball1GoingRight = false;
                makeRandomSpeeds1();
            }

            if (ball2X > panel.getWidth() - border) {
                ball2GoingRight = false;
                makeRandomSpeeds2();
            }

            if (ball1X < 0) {
                ball1GoingRight = true;
                makeRandomSpeeds1();
            }

            if (ball2X < 0) {
                ball2GoingRight = true;
                makeRandomSpeeds2();
            }

            if (greenx < 0) {
                ball3GoingRight = true;
                makeRandomSpeeds3();
            }

            if (greenx > panel.getWidth() - border) {
                ball3GoingRight = false;
                makeRandomSpeeds3();
            }

            if (greeny < 0) {
                ball3GoingDown = true;
                makeRandomSpeeds3();
            }

            if (greeny > panel.getHeight() - border) {
                ball3GoingDown = false;
                makeRandomSpeeds3();
            }

         

            if (random() > 0.99) {
                ball3GoingDown = true;
            } else if (random() > 0.99) {
                ball3GoingDown = false;
            } else if (random() > 0.99) {
                ball3GoingRight = true;
            } else if (random() > 0.99) {
                ball3GoingRight = false;
            }

            if (random() > 0.99) {
                ball1GoingDown = true;
            } else if (random() > 0.99) {
                ball1GoingDown = false;
            } else if (random() > 0.99) {
                ball1GoingRight = true;
            } else if (random() > 0.99) {
                ball1GoingRight = false;
            }

            if (random() > 0.99) {
                ball2GoingDown = true;
            } else if (random() > 0.99) {
                ball2GoingDown = false;
            } else if (random() > 0.99) {
                ball2GoingRight = true;
            } else if (random() > 0.99) {
                ball2GoingRight = false;
            }
            Paint();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        MovingPong ex = new MovingPong();
        new Thread(ex).start();
    }

    public void Paint() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, width+10, height+10);
        Paint(g);
        bufferStrategy.show();
    }

    private void printSimpleString(String s, int YPos) {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        Font tr = new Font(null, Font.PLAIN, 30);
        g.setFont(tr);
        int stringLen = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        int start = panel.getWidth() / 2 - stringLen / 2;
        g.drawString(s, start, YPos);
    }

    void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            if (!line.isEmpty()) {
                String[] part = line.split("\t");
                g.drawString(part[0], 190, y);
                g.drawString(part[1], 240, y);
                g.drawString(part[2], 350, y);
                y += g.getFontMetrics().getHeight();
            }
        }
    }

    protected void Paint(Graphics2D g) {
       
        
        String time = Integer.toString(seconds);

        if (seconds == freeballtime)
            freeballs = true;

        
        if (seconds == PowerUpTime){ 
            powerUpColission = true;
            powerUp = true;
        }     

        if (seconds == powerDownTime){
            powerUpColission = true;
        }

        if (powerUp == true) {
            g.drawImage(powerup, PowerX, PowerY, null);
            if (direction != GameOver) {
                PowerY += 3;
            }
        }

       
    if(powerUpColission == false) 
            g.setColor(flash ? Color.red : Color.yellow);
        else
            g.setColor(Color.red);
        g.fillOval(ball1X, ball1Y, 50, 50);

        if (direction == notStarted) {
            showBtnCredits = true;
            printSimpleString("Press the arrow keys to start", 180);
            freeballs = false;
            Font tr = new Font(null, Font.PLAIN, 18);
            g.setFont(tr);
            g.setColor(Color.black);
            drawString(g, hm.getHighscoreString(), 10, 220);

        }

         if(powerUpColission == false) 
            g.setColor(flash ? Color.blue : Color.yellow);
        else
            g.setColor(Color.blue);
        g.fillOval(ball2X, ball2Y, 50, 50);

        if (direction == GameOver) {
            printSimpleString("Game over", 165);
            printSimpleString("Press \"Space\" to start a new game", 240);
            
            
        }
        if (freeballs == true) {

            if(powerUpColission == false) 
            g.setColor(flash ? Color.green : Color.yellow);
        else
            g.setColor(Color.green);
            g.fillOval(greenx, greeny, 50, 50);

        }

        g.drawImage(ballImage, x, y, null);

        int Ydiff1 = y - ball1Y;
        int Xdiff1 = x - ball1X;

        int Xdiff2 = x - ball2X;
        int Ydiff2 = y - ball2Y;

        int Xdiffgreen = x - greenx;
        int Ydiffgreen = y - greeny;

        int powerXdiff = x - PowerX;
        int powerYdiff = y - PowerY;

        if (powerUp == true) {

            if (powerYdiff < 0) {
                powerYdiff = -powerYdiff;
            }

            if (powerXdiff < 0) {
                powerXdiff = -powerXdiff;
            }

            if (sqrt(powerXdiff * powerXdiff + powerYdiff * powerYdiff) < 50) {
                powerUpColission = false;
                powerUp = false;
                powerDownTime = seconds + 10;
            }
        }

        if (freeballs == true) {
            if (Ydiffgreen < 0) {
                Ydiffgreen = -Ydiffgreen;
            }
        }

        if (freeballs == true) {
            if (Xdiffgreen < 0) {
                Xdiffgreen = -Xdiffgreen;
            }
        }

        if (freeballs == true) {
            if (powerUpColission == true) {
                if (sqrt(Xdiffgreen * Xdiffgreen + Ydiffgreen * Ydiffgreen) < 50) {
                    direction = GameOver;
                }
            }
        }

        if (Ydiff2 < 0) {
            Ydiff2 = -Ydiff2;
        }

        if (Xdiff2 < 0) {
            Xdiff2 = -Xdiff2;
        }

        if (Ydiff1 < 0) {
            Ydiff1 = -Ydiff1;
        }

        if (Xdiff1 < 0) {
            Xdiff1 = -Xdiff1;
        }

        if (powerUpColission == true) {

            if (sqrt(Xdiff1 * Xdiff1 + Ydiff1 * Ydiff1) < 50) {
                direction = GameOver;
            }

            if (sqrt(Xdiff2 * Xdiff2 + Ydiff2 * Ydiff2) < 50) {
                direction = GameOver;
            }
        }

        if (direction != notStarted) {
            showBtnCredits = false;

            printSimpleString("Seconds: " + time, 25);
        }
    }

   
    
    void initVariables() {
        direction = 0;
        seconds = 0;

        x = 250;
        y = 330;

        height = (int) screenSize.getHeight();
        width = (int) screenSize.getWidth();

        PowerX = rand.nextInt(450) + 1;
        //450 is the maximum and the 1 is our minimum 

        PowerY = rand.nextInt(50) + 50;
        //50 is the maximum and the 50 is our minimum 

        powerUp = false;

        ball1X = 440;
        ball1Y = 50;

        ball2X = 50;
        ball2Y = 50;

        saveHighScore = false;

        makeRandomSpeeds2();
        makeRandomSpeeds1();

        movePowerUp = true;
        freePowerUP = true;

        freeballtime = rand.nextInt(50) + 20;
        greenx = rand.nextInt(450) + 0;
        greeny = rand.nextInt(350) + 50;

        running = true;
        border = 50;
        powerUpImage = true;
        powerUp = false;
        ball1GoingDown = true;
        ball1GoingRight = true;
        ball2GoingDown = true;
        ball2GoingRight = true;
        ball3GoingDown = true;
        ball3GoingRight = true;
        freeballs = false;
        powerUpColission = true;
        notStarted = 0;
        up = 1;
        right = 2;
        left = 3;
        down = 4;
        GameOver = 5;
        PowerUpTime = rand.nextInt(50) + 3;
        powerDownTime = -1;
        flashCounter = 5; 
        flash = false;
        brightRed = red.brighter();
        showBtnCredits = false;
    }

    public void moveIt(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                if (direction != GameOver) {
                    direction = down;
                }
                break;

            case KeyEvent.VK_UP:
                if (direction != GameOver) {
                    direction = up;
                }

                break;
            case KeyEvent.VK_LEFT:
                if (direction != GameOver) {
                    direction = left;
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (direction != GameOver) {
                    direction = right;
                }
                break;

            case KeyEvent.VK_S:
                initVariables();
                break;

            case KeyEvent.VK_SPACE:
                initVariables();
                break;

            case KeyEvent.VK_ENTER:
                initVariables();
                break;

        }

    }

    void makeRandomSpeeds1() {
        ball1XSpeed = 2 + (int) (random() * 6);
        ball1YSpeed = 2 + (int) (random() * 6);
    }

    void makeRandomSpeeds2() {
        ball2XSpeed = 2 + (int) (random() * 6);
        ball2YSpeed = 2 + (int) (random() * 6);
    }

    void makeRandomSpeeds3() {
        ball3XSpeed = 2 + (int) (random() * 6);
        ball3YSpeed = 2 + (int) (random() * 6);
    }

    // Av Jonathan Persson 2015
}
