import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class UIPanelDisplay extends JPanel {

    public Maze currentMaze;

    public UIPanelDisplay() throws SQLException {

        // Set up
        setPreferredSize(new Dimension(2000, 2000));
        setBackground(Color.GRAY);
    }

    public void UpdateDisplay() throws IOException {

        // Clear the display
        removeAll();

        MazeCell[] mazeData = currentMaze.GetMazeData();
        int xDimension = currentMaze.GetXDimension();
        int yDimension = currentMaze.GetYDimension();
        int cells = currentMaze.GetCellCount();
        currentMaze.GenerateDisplayData();
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
