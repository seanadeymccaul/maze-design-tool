import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class UIPanelDisplay extends JPanel {

    public Maze currentMaze;
    public MazeCell currentSelection;

    public UIPanelDisplay() throws SQLException {
        // Set up
        setPreferredSize(new Dimension(2000, 2000));
        setBackground(Color.GRAY);
    }

    public void UpdateDisplay() throws IOException {
        // Clear the display
        removeAll();

        MazeCell[] mazeData = currentMaze.getMazeData();
        int xDimension = currentMaze.getXDimension();
        int yDimension = currentMaze.getYDimension();
        int cells = currentMaze.getCellCount();
        // Set the layout
        setLayout(new GridLayout(yDimension, xDimension));
        // Populate the display
        UIPanelDisplayCell[] cellToDisplay = this.currentMaze.GetDisplayData();
        for (int i = 0; i < cellToDisplay.length; i++) {
            cellToDisplay[i].CreateBorder();
            add(cellToDisplay[i]);
        }
        UI_new.getInstance().pack();
    }
}
