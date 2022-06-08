import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class UIPanelDisplay extends JPanel {

    private Maze displayedMaze;
    public Maze GetDisplayedMaze(){return displayedMaze;}
    public void SetDisplayedMaze(Maze m) throws IOException {displayedMaze = m; UpdateDisplay();}

    public UIPanelDisplay() throws SQLException {

        // Set up
        setPreferredSize(new Dimension(2000, 2000));
        setBackground(Color.GRAY);

    }

    public void UpdateDisplay() throws IOException {

        // Clear the display
        removeAll();

        // Get updated display parameters
        int xDimension = displayedMaze.GetXDimension();
        int yDimension = displayedMaze.GetYDimension();
        setLayout(new GridLayout(yDimension,xDimension));

        // Update the maze display cells
        displayedMaze.GenerateDisplayData();

        // Populate the display with the updated display cells
        for (int i = 0; i < xDimension*yDimension; i++){
            UIPanelDisplayCell currentCell = displayedMaze.GetDisplayData()[i];
            add(currentCell);
        }

        // Pack the UI
        UI.getInstance().pack();

    }
}
