import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;

public class Plansza extends JFrame{
    public static final int X=30;
    public static final int Y=30;
    public static final int size=20;
    JFrame frame=new JFrame();
    public Plansza(){
        frame.setTitle("SNAKE!");
        frame.add(new GPanel() );
        frame.setVisible(true);
        frame. setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        ImageIcon snakeFrog= new ImageIcon("src/zaba.jpg");
        frame.setIconImage(snakeFrog.getImage());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    } public static void draw(Graphics g) {
        g.setColor(new Color(88, 187, 166));
        g.fillRect(0,0,(X*size)/2,(Y*size)/2);
        g.setColor(new Color(38, 199, 185));
        g.fillRect(300,0,(X*size)/2,(Y*size)/2);
        g.setColor(new Color(55, 155, 61));
        g.fillRect(300,300,(X*size)/2,(Y*size)/2);
        g.setColor(new Color(5, 139, 124));
        g.fillRect(0,300,(X*size)/2,(Y*size)/2);
    }
}

