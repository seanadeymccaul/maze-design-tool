import javax.swing.*;
import java.awt.*;

public class UIMazeEditor extends JPanel {
    public UIMazeEditor(){
        setLayout(new GridLayout(3,2,10,0));
        Icon horizontalIcon = new ImageIcon("horizontal_maze.png"); JButton horizontal = new JButton(horizontalIcon);
        Icon verticalIcon = new ImageIcon("vertical_maze.png"); JButton vertical = new JButton(verticalIcon);
        add(new JPanel()); add(new JPanel()); add(horizontal);
        add(vertical); add(new JPanel()); add(new JPanel());
    }
}
