import javax.swing.*;
import java.awt.*;

public class UIController extends JPanel {

    // Instances

    public UIController(){
        setLayout(new GridLayout(3,3));
        Icon upIcon = new ImageIcon("up_arrow.png"); JButton up = new JButton(upIcon);
        Icon downIcon = new ImageIcon("down_arrow.png"); JButton down = new JButton(downIcon);
        Icon leftIcon = new ImageIcon("left_arrow.png"); JButton left = new JButton(leftIcon);
        Icon rightIcon = new ImageIcon("right_arrow.png"); JButton right = new JButton(rightIcon);
        add(new JPanel()); add(up); add(new JPanel()); add(left); add(new JPanel()); add(right); add(new JPanel());
        add(down); add(new JPanel());

    }
}
