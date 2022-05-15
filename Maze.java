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

    /**
     * @param xDimension width of the maze
     * @param yDimension height of the maze
     */
    public Maze(int xDimension, int yDimension){
        this.cells = xDimension * yDimension;
        this.mazeData = new int[cells];
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

}
