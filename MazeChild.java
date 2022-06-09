import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
        MazeDatabase_new.getInstance().CreateTable(this,"Child");

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
        MazeDatabase_new.getInstance().CreateTable(this, "Child");

    }

    /**
     * A private helper method for generating the maze to initialise the child start and end cells
     */
    private void InitialiseStartEndCells() {


        ReplaceCell(new MazeCell(2,0,0,0,0),0);
        ReplaceCell(new MazeCell(2,0,0,0,0),1);
        ReplaceCell(new MazeCell(2,0,0,0,0),xDimension);
        ReplaceCell(new MazeCell(2,0,0,0,0),xDimension+1);

        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-1);
        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-2);
        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-2-xDimension);
        ReplaceCell(new MazeCell(3,0,0,0,0),this.cellCount-1-xDimension);

        /**
        int[] cells = new int[]{0,1,xDimension,xDimension+1};
        for (int cell : cells) {
            this.ReplaceCell(new MazeCell(2, 0, 0, 0, 0), cell);
        }
        cells = new int[]{cellCount-1,cellCount-2,cellCount-xDimension-1,cellCount-xDimension-2};
        for (int cell : cells) {
            this.ReplaceCell(new MazeCell(3, 0, 0, 0, 0), cell);
        }*/
    }


    //
    @Override
    public void GenerateDisplayData() throws IOException {
        this.displayData = new UIPanelDisplayCell[xDimension*yDimension];
        for (int i = 0; i < (xDimension*yDimension); i++){
            if (mazeData[i].getValue() < 6){
                UIPanelDisplayCell newPanel = new UIPanelDisplayCell(this.mazeData[i],i);
                newPanel.setBackground(Color.WHITE);
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

        /**
        this.displayData = new UIPanelDisplayCell[this.cellCount];
        for (int i = 0; i < this.cellCount; i++){
            if (mazeData[i].getValue() < 6){
                UIPanelDisplayCell newPanel = new UIPanelDisplayCell(this.mazeData[i],i);
                newPanel.setBackground(Color.WHITE);
                if (this.paintSolution) {
                    if (solutionDirections.size() > 1) {
                        if (solutionDirections.contains(i)) {
                            System.out.println("SETTING GREEN!");
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
        // inserts the start and end images above
        // now cut an opening for it*/
    }
    
}
