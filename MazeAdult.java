import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class MazeAdult extends Maze{

    // Constructor
    public MazeAdult() throws SQLException {
        super();
    }

    public MazeAdult(String name,String author, int xDimension, int yDimension) throws SQLException, IOException {
        super(name, author, xDimension, yDimension);
    }

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

    private void InitialiseStartEndCells() throws IOException {

        ReplaceCell(new MazeCell(2,0,0,0,0),0);
        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-1);

    }

    @Override
    public void GenerateDisplayData() throws IOException {

        //
        this.displayData = new UIPanelDisplayCell[xDimension*yDimension];

        //
        for (int i = 0; i < (xDimension*yDimension); i++){
            if (mazeData[i].getValue() < 6){
                UIPanelDisplayCell newPanel = new UIPanelDisplayCell(this.mazeData[i],i);
                if (solutionDirections.size() > 1){
                    if (solutionDirections.contains(i)){
                        newPanel.setBackground(Color.GREEN);
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
