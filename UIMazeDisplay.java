import javax.swing.*;
import java.awt.*;

public class UIMazeDisplay extends JPanel {

    public UIMazeDisplay(){

        setPreferredSize(new Dimension(400,400));

        Icon horizontalIcon = new ImageIcon("horizontal_maze.png");
        Icon verticalIcon = new ImageIcon("vertical_maze.png");

        Maze currentMaze = new MazeAdult(10,10);
        setLayout(new GridLayout(10,10));
        for (int i = 0; i < currentMaze.cells; i++){
            if (currentMaze.mazeData[i] == 0){
                add(new JLabel(horizontalIcon));
            }
            else{
                add(new JLabel(verticalIcon));
            }

        }

    }

}
