import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UIPanelDisplay extends JPanel {

    public Maze currentMaze;
    public MazeCell currentSelection;

    public UIPanelDisplay() throws SQLException {
        // Set up
        setPreferredSize(new Dimension(2000, 2000));
        setBackground(Color.GRAY);
    }

    public void UpdateDisplay() {
        // Clear the display
        removeAll();
        // Get information from currentMaze
        int xDimension = currentMaze.xDimension;
        int yDimension = currentMaze.yDimension;
        int cells = currentMaze.cells;
        MazeCell[] mazeData = currentMaze.mazeData;
        // Set the layout
        setLayout(new GridLayout(yDimension, xDimension));
        // Populate the maze
        for (int i = 0; i < cells; i++) {
            MazeCell currentCell = currentMaze.mazeData[i];
            UIPanelMazeCell cell2 = new UIPanelMazeCell(currentCell,i);
            add(cell2);
            if (currentCell.value == 4){
                cell2.setBackground(Color.GREEN);
            }

            // when creating a line of them and then close them, they all block off
        }
        UI_new.getInstance().pack();
    }
}
