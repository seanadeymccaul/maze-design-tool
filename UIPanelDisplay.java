import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A JPanel class to contain the maze to display
 */
public class UIPanelDisplay extends JPanel {

    private Maze displayedMaze;
    public Maze GetDisplayedMaze(){return displayedMaze;}
    public void SetDisplayedMaze(Maze m) throws IOException {displayedMaze = m; UpdateDisplay();}

    public UIPanelDisplay() throws SQLException {

        // Set up
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(2000,2000));
    }

    public void UpdateDisplay() throws IOException {
        // Clear the display
        removeAll();
        int xDimension = displayedMaze.GetXDimension();
        int yDimension = displayedMaze.GetYDimension();
        int cellWidth = UI.getInstance().GetDisplayDimension(xDimension);
        int cellHeight = UI.getInstance().GetDisplayDimension(yDimension);
        setPreferredSize(new Dimension(2000,2000));
        // Get updated display parameters
        setLayout(new GridLayout(yDimension,xDimension));
        // Update the maze display cells
        displayedMaze.GenerateDisplayData();
        // Populate the display with the updated display cells
        for (int i = 0; i < xDimension*yDimension; i++){
            MazeDisplayCell currentCell = displayedMaze.GetDisplayData()[i];
            add(currentCell);
        }
        // Pack the UI
        UI.getInstance().pack();
    }
}
