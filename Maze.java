import java.sql.ResultSet;

abstract class Maze implements MazeInterface{

    // Fields
    private int xDimension;
    private int yDimension;
    public int cells;
    private int stepSize;
    private int borderDesign;
    public int[] mazeData;

    public Maze(int xDimension, int yDimension){
        this.cells = xDimension * yDimension;
        this.mazeData = new int[cells];
        GenerateAuto();
    }

    // Methods
    public int[] GenerateBlank() {
        return new int[1];
    }

    public int[] GenerateAuto(){
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

}
