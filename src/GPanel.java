import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;
import javax.swing.JPanel;

public class GPanel extends JPanel implements ActionListener {

    public static final int D = 100;                                 //delay
    public static final int X = 600;                                 //szerokość
    public static final int Y = 600;                                 //długość
    public static final int size = 20;                               //wielkosc kwadracika XxY
    public static final int M = (X * Y) / size;                      //ile zmieścimy obiektow wielkosci XxY
    final int x[] = new int[M];
    final int y[] = new int[M];
    char direction = 'D';
    int highScore=0;
    int parts = 4;                                                   //poczatkowa liczba czesci ciała weza
    int ax,ay;
    int score;
    boolean running;
    boolean gameOver;
    Point apple;
    Point snake;
    Point head;
    Timer timer;
    Random random;
    JButton myButton=new JButton("PLAY AGAIN");
    public static final String SCORE_FILE = "highscore.txt";


    public GPanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(X,Y));
        this.addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
        loadHighScore();
        Game();
        myButton.setBounds(200,480,200,40);
        myButton.setFocusable(false);
        myButton.addActionListener(this);
    }public void Game() {
        appleA();
        running=true;
        timer=new Timer(D,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        Plansza.draw(g);
        drawSnake(g);
        drawApple(g);
        drawGameOver(g);
        drawScore(g);
    }public void appleA() {
        ax=random.nextInt((int)(X/size))*size;
        ay=random.nextInt((int)(X/size))*size;
        apple=new Point(ax,ay);
    }public void drawApple(Graphics g){
        if(running){
            Graphics2D a = (Graphics2D) g;
            a.setColor(new Color(103, 6, 18));
            a.fillOval(ax,ay,size,size);
        }else gameOver= true;
    }public void drawSnake(Graphics g) {
        if(running){
            for (int i = 0; i < parts; i++) {
                snake=new Point(x[i],y[i]);
                Graphics2D a = (Graphics2D) g;
                if (i == 0) {
                    a.setColor(new Color(95, 10, 145));
                    a.fillRect(snake.x, snake.y, size, size);
                    head=new Point(x[i],y[i]);
                } else {
                    a.setColor(new Color(128, 13, 196));
                    a.fillRect(snake.x, snake.y, size, size);
                }
            }
        }else gameOver=true;
    }public void drawScore(Graphics g){
        if(running){
            Graphics2D a = (Graphics2D) g;
            a.setColor(new Color(9, 3, 4));
            a.drawString("Score: "+score,X/2-70,15);
            a.drawString("High Score: " + highScore, X/2+20, 15);
            a.setFont(new Font("ALGERIAN",Font.ITALIC,30));

        }else gameOver=true;
    }
    public void drawGameOver(Graphics g){
        if(gameOver){
            if (score > highScore) {
                highScore = score;  // Ustawiamy nowy rekord
                saveHighScore();  // Zapisujemy do pliku
            }
            g.setColor(Color.BLACK);
            g.fillRect(0,0,X,Y);
            g.setColor(Color.PINK);
            g.setFont(new Font("TimesNewRoman",Font.BOLD,90));
            g.drawString("GAME OVER",20,Y/2);
            g.setFont(new Font("Ink Free",Font.ITALIC,30));
            g.drawString("Score:"+score,250,400);
            g.drawString("High Score: " + highScore, 210, 450);
            this.add(myButton);
        }
    }public void move() {
        for (int i = parts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }if(direction=='U')y[0]=y[0]-size;
        else if(direction=='D')y[0]=y[0]+size;
        else if(direction=='R')x[0]=x[0]+size;
        else x[0]=x[0]-size;

    }public void isAppleEaten() {
        if ((head.x == apple.x) && (head.y == apple.y)) {
            parts++;
            appleA();
            score++;

        }
    }public void isCollision() {
        for (int i = parts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) running = false;
        }
        if (x[0] < 0) running = false;
        if (y[0] > Y) running = false;
        if (x[0] > X) running = false;
        if (y[0] < 0) running = false;
        if (!running) timer.stop();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myButton) new Plansza();
        if (running) {
            move();
            isAppleEaten();
            isCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch ((e.getKeyCode())) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction != 'D') direction = 'U';
                    else gameOver=true;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction != 'U') direction = 'D';
                    else gameOver=true;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction != 'R') direction = 'L';
                    else gameOver=true;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction != 'L') direction = 'R';
                    else gameOver=true;
                    break;
            }
        }
    }
    private void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            highScore = 0;  // Jeśli plik nie istnieje lub błąd odczytu, ustaw 0
        }
    }
}