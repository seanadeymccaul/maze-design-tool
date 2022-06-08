import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class MazeChild extends Maze{

    public MazeChild(){
        super();
    }

    public MazeChild(String name,String author, int xDimension, int yDimension) throws SQLException {
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
        MazeDatabase_new.getInstance().CreateTable(this,"Child");

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
        MazeDatabase_new.getInstance().CreateTable(this, "Child");

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


        //
        for (MazeImage i : imageList){
            InsertImage(i);
        }

    }
    
}
