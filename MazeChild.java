import java.io.IOException;
import java.sql.SQLException;

public class MazeChild extends Maze{

    private byte[] startImage;
    private byte[] endImage;


    public MazeChild() throws SQLException {
        super();
    }

    public MazeChild(String name,String author, int xDimension, int yDimension) throws SQLException, IOException {
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
        MazeDatabase_new.getInstance().CreateTable(this,"Child", lastEditTime);

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
        MazeDatabase_new.getInstance().CreateTable(this, "Child", lastEditTime);

    }

    private void InitialiseStartEndCells() throws IOException {

        int[] cells = new int[]{0,1,xDimension,xDimension+1,cellCount-1,cellCount-2,cellCount-xDimension-1,cellCount-xDimension-2};
        for (int cell : cells) {
            this.ReplaceCell(new MazeCell(3, 0, 0, 0, 0), cell);
        }

    }

    //
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
            super.InsertImage(startImagePath,0,2,2);
        }
        if (endImagePath != null){
            super.InsertImage(endImagePath,cellCount-xDimension-2,2,2);
        }
        if (logoImagePath != null){
            super.InsertImage(logoImagePath,logoIndex,1,1);
        }

    }
    
}
