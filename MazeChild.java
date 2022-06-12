import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Extends abstract maze class and implements design specific to child mazes
 */
public class MazeChild extends Maze{

    /**
     * Default constructor
     */
    public MazeChild(){
        super();
    }

    /**
     * Constructor for creating a new maze
     * @param name the name to save the maze as
     * @param author the name of the author
     * @param xDimension the cell count of the maze width
     * @param yDimension the cell count of the maze height
     * @throws SQLException creates a new table and populates with data in mariadb
     */
    public MazeChild(String name,String author, int xDimension, int yDimension) throws SQLException {
        super(name, author, xDimension, yDimension);
    }

    /**
     * Populates the maze data with the content of a blank array
     * @throws IOException inserts the start and end images from file
     * @throws SQLException saves the maze information in database
     */
    @Override
    public void GenerateBlankMaze() throws IOException, SQLException {

        // Generate blank maze
        super.GenerateBlankMaze();

        // Initialise the start and end cells
        this.InitialiseStartEndCells();

        // Generate the display data
        this.GenerateDisplayData();

        // Create the table in the database
        MazeDatabase.getInstance().CreateTable(this,"Child");

    }

    /**
     * Populates the maze data with the content of an automatic generated array
     * @throws IOException inserts the start and end images from file
     * @throws SQLException saves the maze information in database
     */
    @Override
    public void GenerateAutoMaze() throws IOException, SQLException {

        // Generate blank maze
        super.GenerateBlankMaze();

        // Generate automatic maze
        super.GenerateAutoMaze();

        // Initialise the start and end cells
        this.InitialiseStartEndCells();

        // Generate display data
        this.GenerateDisplayData();

        // Create table in the database
        MazeDatabase.getInstance().CreateTable(this, "Child");

    }

    /**
     * A private helper method for generating the maze to initialise the child start and end cells
     */
    private void InitialiseStartEndCells() {

        ReplaceCell(new MazeDataCell(2,0,0,0,0),0);
        ReplaceCell(new MazeDataCell(2,0,0,0,0),1);
        ReplaceCell(new MazeDataCell(2,0,0,0,0),xDimension);
        ReplaceCell(new MazeDataCell(2,0,0,0,0),xDimension+1);

        ReplaceCell(new MazeDataCell(3,0,0,0,0),(xDimension*yDimension)-1);
        ReplaceCell(new MazeDataCell(3,0,0,0,0),(xDimension*yDimension)-2);
        ReplaceCell(new MazeDataCell(3,0,0,0,0),(xDimension*yDimension)-2-xDimension);
        ReplaceCell(new MazeDataCell(3,0,0,0,0),(xDimension*yDimension)-1-xDimension);

    }


    //
    @Override
    public void GenerateDisplayData() throws IOException {

        // Wipe the display data
        this.displayData = new MazeDisplayCell[xDimension*yDimension];

        for (int i = 0; i < (xDimension*yDimension); i++){

            // Create a new MazeDisplayCell for each valid mazeData cell
            if (mazeData[i].getValue() < 6){
                MazeDisplayCell newPanel = new MazeDisplayCell(this.mazeData[i],i);
                newPanel.setBackground(Color.WHITE);

                // Check if solution should be painted
                if (this.paintSolution) {
                    if (solutionDirections.size() > 1) {
                        if (solutionDirections.contains(i)) {
                            newPanel.setBackground(Color.GREEN);
                        }
                    }
                }
                this.displayData[i] = newPanel;
            }
        }

        // Add images from image list
        if (imageList.size() > 0){
            for (MazeImage i : imageList){
                super.InsertImage(i);
            }
        }

    }
    
}
