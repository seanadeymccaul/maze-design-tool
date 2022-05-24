import java.sql.ResultSet;
import java.sql.SQLException;

abstract class Maze {

    // Fields
    public int[] mazeData;
    private int rows;
    private int columns;
    public int cells;

    // construct empty maze
    public Maze(){

    }

    // Generate a blank maze COMPLETED
    public void GenerateBlank(int rows, int columns){
        this.mazeData = new int[rows*columns];
        for (int i = 0; i < mazeData.length; i++){
            this.mazeData[i] = 0;
        }
    }

    // Generate random maze from algorithm NOT DONE
    public void GenerateMaze() {
        // load maze from algorithm
    }

    // Load mazeData from database COMPLETED
    public void LoadMaze(String tableName) throws SQLException {
        // load maze from database
        MazeDatabase db = new MazeDatabase();
        int[] hello = db.RetrieveTableSize("names");
    }

    public void SaveMaze(String tableName){

    }

    // get an array for the cell
    public int[] GetCell(int cellIndex){
        int[] cellArray = new int[4];
        cellArray[0] = GetCellLeft(cellIndex);
        cellArray[1] = GetCellRight(cellIndex);
        cellArray[2] = GetCellAbove(cellIndex);
        cellArray[3] = GetCellBelow(cellIndex);
        return cellArray;
    }

    private int GetCellAbove(int cellIndex){
        if (cellIndex > this.rows){
            return this.mazeData[cellIndex - this.rows];
        }
        else{
            return 1;       // given that 1 is a block
        }
    }

    private int GetCellBelow(int cellIndex){
        if (cellIndex < this.rows * (this.columns-1)){     // if it's not in the last row
            return this.mazeData[cellIndex + rows];
        }
        else{
            return 1;
        }
    }

    private int GetCellLeft(int cellIndex){
        if (cellIndex%this.rows == 0){
            return 1;
        }
        else{
            return this.mazeData[ - 1];
        }
    }

    private int GetCellRight(int cellIndex){
        if (cellIndex%(this.rows) == 4){
            return 1;
        }
        else if (cellIndex + 1 == rows * columns){
            return 1;
        }
        else{
            return this.mazeData[cellIndex + 1];
        }
    }

}
