import java.sql.SQLException;
import java.util.Random;

abstract class Maze {

    // Fields
    public MazeCell[] mazeData;
    public int xDimension;
    public int yDimension;
    public int cells;
    public String name;
    public int type;
    public MazeDatabase db;

    //
    public Maze() throws SQLException {
        this.db = new MazeDatabase();
    }

    public Maze(String name, int type, int xDimension, int yDimension) throws SQLException {
        this.name = name;
        this.type = type;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.cells = xDimension * yDimension;
        this.db = new MazeDatabase();
    }

    // COMPLETED and TESTED
    public void GenerateBlank() throws SQLException {
        Algorithm algorithm = new Algorithm(this.xDimension, this.yDimension);
        algorithm.GenerateBlank();
        this.mazeData = algorithm.mazeData;
        db.CreateTable(name, CellToString(this.mazeData),type,xDimension,yDimension);
    }

    // INCOMPLETE - need to incorporate algorithm just random at the moment
    public void GenerateAuto() throws SQLException {

        Algorithm algorithm = new Algorithm(this.xDimension,this.yDimension);
        algorithm.GenerateBlank();
        algorithm.GenerateSolution();
        algorithm.PopulateMaze();
        this.mazeData = algorithm.mazeData;
        db.CreateTable(name, CellToString(this.mazeData),type,xDimension,yDimension);
    }

    // COMPLETED and TESTED
    public void LoadMaze(String tableName) throws SQLException {
        this.name = tableName;
        this.mazeData = StringToCell(db.GetMazeData(tableName));
        this.type = db.GetMazeType(tableName);
        this.xDimension = db.GetMazeDimensions(tableName)[0];
        this.yDimension = db.GetMazeDimensions(tableName)[1];
        this.cells = xDimension * yDimension;
    }

    // COMPLETED and TESTED
    public void SaveMaze(String tableName) throws SQLException {
        db.SetMazeData(tableName, CellToString(this.mazeData));
    }

    // Edit Cells
    public void EditCell(MazeCell newCell, int cellIndex){
        this.mazeData[cellIndex] = newCell;

        if (!CheckTopBorder(cellIndex)){
            this.mazeData[cellIndex - xDimension].valueBelow = newCell.valueAbove;
        }
        if (!CheckBottomBorder(cellIndex)){
            this.mazeData[cellIndex + xDimension].valueAbove = newCell.valueBelow;
        }
        if (!CheckRightBorder(cellIndex)){
            this.mazeData[cellIndex + 1].valueLeft = this.mazeData[cellIndex].valueRight;
        }
        if (!CheckLeftBorder(cellIndex)){
            this.mazeData[cellIndex - 1].valueRight = this.mazeData[cellIndex].valueLeft;
        }
    }

    public boolean CheckTopBorder(int cellIndex){
        if (cellIndex < this.xDimension){
            return true;
        }
        return false;
    }

    public boolean CheckBottomBorder(int cellIndex){
        if (cellIndex > this.xDimension * (this.yDimension - 1)){
            return true;
        }
        return false;
    }

    public boolean CheckLeftBorder(int cellIndex){
        if (cellIndex%xDimension == 0){
            return true;
        }
        return false;
    }

    public boolean CheckRightBorder(int cellIndex){
        if (cellIndex%xDimension == xDimension - 1){
            return true;
        } else if (cellIndex + 1 == cells){
            return true;
        }
        return false;
    }

    // COMPLETED and TESTED
    private String[] CellToString(MazeCell[] cellData){
        String[] mazeDataString = new String[mazeData.length];
        for (int i = 0; i < cellData.length; i++){
            String value = Integer.toString(cellData[i].value) + Integer.toString(cellData[i].valueLeft) + Integer.toString(cellData[i].valueRight)
                    + Integer.toString(cellData[i].valueAbove) + Integer.toString(cellData[i].valueBelow);
            mazeDataString[i] = value;
        }
        return mazeDataString;
    }

    // COMPLETED and TESTED
    private MazeCell[] StringToCell(String[] stringData){
        MazeCell[] cellData = new MazeCell[stringData.length];
        for (int i = 0; i < stringData.length; i++){
            int value = Character.getNumericValue(stringData[i].charAt(0));
            int upValue = Character.getNumericValue(stringData[i].charAt(1));
            int downValue = Character.getNumericValue(stringData[i].charAt(2));
            int leftValue = Character.getNumericValue(stringData[i].charAt(3));
            int rightValue = Character.getNumericValue(stringData[i].charAt(4));
            cellData[i] = new MazeCell(value,leftValue,rightValue,upValue,downValue);
        }
        return cellData;
    }

    public static int GetRandomInt(){
        Random r = new Random();
        return r.nextInt((1 - 0) + 1) + 0;
    }


}
