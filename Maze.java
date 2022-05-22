import java.sql.ResultSet;

abstract class Maze {

    // Fields
    private int xDimension;
    private int yDimension;
    public int cells;
    private int stepSize;
    private int borderDesign;
    public int[] mazeData;
    private Solution solution;
    private Algorithm algorithm;

    // construct empty maze
    public Maze(int xDimension, int yDimension){
        this.cells = xDimension * yDimension;
        this.mazeData = new int[cells];
    }

    // construct maze with existing data
    public Maze(int[] mazeData, int numColumns, int numRows){
        this.mazeData = mazeData;
        this.xDimension = numColumns;
        this.yDimension = numRows;
        this.cells = xDimension * yDimension;
    }

    public int[] GetCell(int cellIndex){
        int[] cellArray = new int[4];
        cellArray[0] = GetCellLeft(cellIndex);
        cellArray[1] = GetCellRight(cellIndex);
        cellArray[2] = GetCellAbove(cellIndex);
        cellArray[3] = GetCellBelow(cellIndex);
        return cellArray;
    }

    // check cell above
    private int GetCellAbove(int cellIndex){
        if (cellIndex > this.xDimension){
            return this.mazeData[cellIndex - this.xDimension];
        }
        else{
            return 1;       // given that 1 is a block
        }
    }

    private int GetCellBelow(int cellIndex){
        if (cellIndex < this.xDimension * (this.yDimension-1)){     // if it's not in the last row
            return this.mazeData[cellIndex + this.xDimension];
        }
        else{
            return 1;
        }
    }

    private int GetCellLeft(int cellIndex){
        if (cellIndex%this.xDimension == 0){
            return 1;
        }
        else{
            return this.mazeData[ - 1];
        }
    }

    private int GetCellRight(int cellIndex){
        if (cellIndex%(this.xDimension) == 4){            // every 5th one is gonna be 1
            return 1;
        }
        else if (cellIndex + 1 == xDimension * yDimension){
            return 1;
        }
        else{
            return this.mazeData[cellIndex + 1];
        }
    }

    /**
     * Populates the maze data according to maze generation algorithm
     */
    public void GenerateAuto() {
        this.algorithm = new Algorithm(xDimension,yDimension);
        algorithm.generateResults();
        this.mazeData = algorithm.getResults();
    }

    /**
     * 
     * @return
     */
    public int[] GenerateBlank(){
        for (int i = 0; i < cells; i++){
            mazeData[i] = i%2;
        }
        return new int[1];
    }

    public void Import() {

    }

    public void Export() {

    }

    public void Save() {

    }

    public void RevertSave() {

    }

    public void InsertImage() {

    }

    public boolean CheckSolution() {
        return true;
    }

    public abstract void Revert();
}
