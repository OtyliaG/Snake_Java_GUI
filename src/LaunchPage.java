import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage implements ActionListener {
    JFrame frame=new JFrame();
    JLabel label=new JLabel();
    JLabel label2=new JLabel();
    JButton myButton=new JButton("START");
    LaunchPage(){
        label2.setBounds(65,10,470,30);
        label2.setText("Wanna play Sssssnake?");
        label2.setForeground(new Color(6, 49, 8));
        label2.setFont(new Font("ALGERIAN",Font.ITALIC,30));
        ImageIcon snake= new ImageIcon("src/tyci.jpg");
        label.setBounds(0,40,530,451);
        label.setIcon(snake);
        myButton.setBounds(160,440,200,40);
        myButton.setFocusable(false);
        myButton.addActionListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(530,530);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(myButton);
        frame.add(label);
        frame.add(label2);
        frame.setIconImage(snake.getImage());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myButton) {
            frame.dispose();
            new Plansza();
        }
    }
}

