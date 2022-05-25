import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

abstract class Maze {

    // Fields
    public Cell[] mazeData;
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
        this.mazeData = new Cell[this.xDimension * yDimension];
        for (int i = 0; i < xDimension * yDimension; i++) {
            this.mazeData[i] = new Cell(1, 0, 0, 1, 1);
        }
        db.CreateTable(name, CellToString(this.mazeData),type,xDimension,yDimension);
    }

    // INCOMPLETE - need to incorporate algorithm just random at the moment
    public void GenerateAuto() throws SQLException {
        this.mazeData = new Cell[this.xDimension * yDimension];
        // initialise a random array between 0 and 1 for 4 values
        int[] cell = new int[4];
        // insert it into the maze data
        for (int i = 0; i < xDimension * yDimension; i++) {
            for (int j = 0; j < cell.length; j++){
                cell[j] = GetRandomInt();
            }
            this.mazeData[i] = new Cell(1, cell[0], cell[1], cell[2], cell[3]);
        }
        // add the border conditions:
        for (int i = 0; i < this.cells; i++){           // if its in row 1
            // check above
            if (i < this.xDimension){
                this.mazeData[i].valueAbove = 1;
            }
            if (i > this.xDimension * (this.yDimension - 1)){            // if it's in last row
                this.mazeData[i].valueBelow = 1;
            }
            if (i%this.xDimension == 0){
                this.mazeData[i].valueLeft = 1;
            }
            if (i%this.xDimension == xDimension - 1){
                this.mazeData[i].valueRight = 1;
            } else if (i + 1 == this.cells){
                this.mazeData[i].valueRight = 1;
            }
        }
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

    //
    public void EditCell(Cell newCell, int cellIndex){
        this.mazeData[cellIndex] = newCell;
        // if this
    }

    // COMPLETED and TESTED
    private String[] CellToString(Cell[] cellData){
        String[] mazeDataString = new String[mazeData.length];
        for (int i = 0; i < cellData.length; i++){
            String value = Integer.toString(cellData[i].value) + Integer.toString(cellData[i].valueLeft) + Integer.toString(cellData[i].valueRight)
                    + Integer.toString(cellData[i].valueAbove) + Integer.toString(cellData[i].valueBelow);
            mazeDataString[i] = value;
        }
        return mazeDataString;
    }

    // COMPLETED and TESTED
    private Cell[] StringToCell(String[] stringData){
        Cell[] cellData = new Cell[stringData.length];
        for (int i = 0; i < stringData.length; i++){
            int value = Character.getNumericValue(stringData[i].charAt(0));
            int upValue = Character.getNumericValue(stringData[i].charAt(1));
            int downValue = Character.getNumericValue(stringData[i].charAt(2));
            int leftValue = Character.getNumericValue(stringData[i].charAt(3));
            int rightValue = Character.getNumericValue(stringData[i].charAt(4));
            cellData[i] = new Cell(value,leftValue,rightValue,upValue,downValue);
        }
        return cellData;
    }

    public static int GetRandomInt(){
        Random r = new Random();
        return r.nextInt((1 - 0) + 1) + 0;
    }


}
