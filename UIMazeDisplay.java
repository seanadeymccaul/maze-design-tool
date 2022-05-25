import jdk.dynalink.beans.StaticClass;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UIMazeDisplay extends JPanel {

    public Maze currentMaze;

        public UIMazeDisplay() throws SQLException {

        setPreferredSize(new Dimension(2000,2000));
        setBackground(Color.GRAY);
        }

        public void UpdateDisplay(){
            removeAll();
            int xDimension = currentMaze.xDimension;
            int yDimension = currentMaze.yDimension;
            int cells = currentMaze.cells;
            // Set the layout
            setLayout(new GridLayout(yDimension,xDimension));

            // Populate the maze
            for (int i = 0; i < cells; i++){

                // for each cell we will populate with border layout
                Cell currentCell = currentMaze.mazeData[i];
                // create the panel
                JPanel currentPanel = new JPanel();
                currentPanel.setBorder(BorderFactory.createMatteBorder(currentCell.valueAbove*10,currentCell.valueLeft*10,
                        currentCell.valueBelow*10,currentCell.valueRight*10, Color.BLACK));
                add(currentPanel);
            }
            UI_new.getInstance().pack();
        }

}
