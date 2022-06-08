import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class MazeAdult extends Maze{

    /**
     * Default constructor
     * @throws SQLException
     */
    public MazeAdult() {
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
    public MazeAdult(String name,String author, int xDimension, int yDimension) throws SQLException {
        super(name, author, xDimension, yDimension);
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
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
        MazeDatabase_new.getInstance().CreateTable(this,"Adult");

        }

    /**
     *
     * @throws IOException
     * @throws SQLException
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
        MazeDatabase_new.getInstance().CreateTable(this, "Adult");

    }

    /**
     *
     * @throws IOException
     */
    private void InitialiseStartEndCells() throws IOException {

        ReplaceCell(new MazeCell(2,0,0,0,0),0);
        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-1);

    }

    /**
     * 
     * @throws IOException
     */
    @Override
    public void GenerateDisplayData() throws IOException {

        //
        this.displayData = new UIPanelDisplayCell[xDimension*yDimension];

        //
        for (int i = 0; i < (xDimension*yDimension); i++){
            if (mazeData[i].getValue() < 6){
                UIPanelDisplayCell newPanel = new UIPanelDisplayCell(this.mazeData[i],i);
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

        // for the images
        if (imageList.size() > 0){
            for (MazeImage i : imageList){
                super.InsertImage(i);
                System.out.println("Calling insert for " + i.GetPath());
            }
        }

    }

}
