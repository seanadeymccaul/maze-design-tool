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
        MazeDatabase_new.getInstance().CreateTable(this,"Adult", lastEditTime);

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
        MazeDatabase_new.getInstance().CreateTable(this, "Adult", lastEditTime);

    }

    private void InitialiseStartEndCells() throws IOException {

        ReplaceCell(new MazeCell(3,0,0,0,0),0);
        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-1);

    }

    @Override
    public void GenerateDisplayData() throws IOException {

        //
        this.displayData = new UIPanelDisplayCell[this.cellCount];

        //
        for (int i = 0; i < this.cellCount; i++){
            UIPanelDisplayCell newPanel = new UIPanelDisplayCell(this.mazeData[i],i);
            this.displayData[i] = newPanel;
        }

        //
        if (startImagePath != null){
            super.InsertImage(startImagePath,0,1,1);
        }
        if (endImagePath != null){
            super.InsertImage(endImagePath,cellCount-1,1,1);
        }
        if (logoImagePath != null){
            super.InsertImage(logoImagePath,logoIndex,logoHeight,logoWidth);
        }

    }

}
