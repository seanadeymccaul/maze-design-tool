import java.util.ArrayList;
import java.util.Random;

public class Algorithm {

    public MazeCell[] mazeData;
    public ArrayList<Integer> solution;
    private int xDimension;
    private int yDimension;

    public Algorithm(int xDimension, int yDimension){
        this.mazeData = new MazeCell[xDimension * yDimension];
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.solution = new ArrayList();
    }

    public void PopulateMaze(){
        int i = 0;
        while (i < xDimension * yDimension){
            if (mazeData[i].value == 1){
                int random = GetRandomInt6();
                if (random == 0){
                    EditCell(new MazeCell(1,1,1,0,0),i);
                } else if (random == 1){
                    EditCell(new MazeCell(1,0,0,1,1),i);
                } else if (random == 2){
                    EditCell(new MazeCell(1,1,0,1,0),i);
                } else if (random == 3){
                    EditCell(new MazeCell(1,0,1,1,0),i);
                } else if (random == 4) {
                    EditCell(new MazeCell(1,1,0,0,1),i);
                } else if (random == 5) {
                    EditCell(new MazeCell(1, 0, 1, 0, 1), i);
                }

            }
            i++;
        }


    }

    public void GenerateSolution(){
        int i = 0;
        String lastMove = "";
        while (i < (xDimension * yDimension)){
            if (i + 1 == (xDimension * yDimension) || i + xDimension == (xDimension * yDimension)){
                return;
            }
            if (GetRandomInt() == 0) {
                if (!CheckRightBorder(i)) {
                    if (lastMove == "vertical"){
                        // get the cell and change bottom to border, and right side to no border
                        EditCell(new MazeCell(4,1,0,0,1),i);
                    }
                    if (mazeData[i + 1].value != 3){
                        EditCell(new MazeCell(4, 0, 0, 1, 1), i + 1);  // cell with value 4
                        this.solution.add(i + 1);             // add index to the solution
                    }
                    i = i + 1;
                    lastMove = "horizontal";
                }
            } else {
                if (!CheckBottomBorder(i)) {
                    //
                    if (lastMove == "horizontal"){
                        // get the cell and change bottom to border, and right side to no border
                        EditCell(new MazeCell(4,0,1,1,0),i);
                    }
                    if (mazeData[i + xDimension].value != 3){
                        EditCell(new MazeCell(4, 1, 1, 0, 0), i + xDimension);  // cell with value 4
                        this.solution.add(i + xDimension);
                    }
                    i = i + xDimension;
                    lastMove = "vertical";
                }
            }

        }

    }

    // Edit Cells
    public void EditCell(MazeCell newCell, int cellIndex){

        if (CheckTopBorder(cellIndex)){
            newCell.valueAbove = 1;
        } else {
            this.mazeData[cellIndex - xDimension].valueBelow = newCell.valueAbove;
        }

        if (CheckBottomBorder(cellIndex)){
            newCell.valueBelow = 1;
        } else {
            this.mazeData[cellIndex + xDimension].valueAbove = newCell.valueBelow;
        }

        if (CheckLeftBorder(cellIndex)){
            newCell.valueLeft = 1;
        } else {
            this.mazeData[cellIndex - 1].valueRight = newCell.valueLeft;
        }

        if (CheckRightBorder(cellIndex)){
            newCell.valueRight = 1;
        } else {
            this.mazeData[cellIndex + 1].valueLeft = newCell.valueRight;
        }

        this.mazeData[cellIndex] = newCell;

    }

    public static int GetRandomInt(){
        Random r = new Random();
        return r.nextInt((1 - 0) + 1) + 0;
    }

    public static int GetRandomInt6(){
        return (int)(Math.random()*6);
    }

    public void GenerateBlank(){

        MazeCell currentCell;
        for (int i = 0; i < (xDimension * yDimension); i++){
            int id = 1, topValue = 0, bottomValue = 0, leftValue = 0, rightValue = 0;

            if (i == 0){                                            // check if start, set id = 2
                id = 2;
            } else if (i == (xDimension * yDimension)) {        // check if last, set id = 3
                id = 3;
            }

            if (id != 2 && CheckTopBorder(i)){
                topValue = 1;
            }
            if (id != 3 && CheckBottomBorder(i)){
                bottomValue = 1;
            }
            if (CheckLeftBorder(i)){
                leftValue = 1;
            }
            if (CheckRightBorder(i)){
                rightValue = 1;
            }

            mazeData[i] = new MazeCell(id,leftValue,rightValue,topValue,bottomValue);
        }
    }

    // check if start and end
    // check if border


    public boolean CheckTopBorder(int cellIndex){
        if (cellIndex < this.xDimension){
            return true;
        }
        return false;
    }

    public boolean CheckBottomBorder(int cellIndex){
        if (cellIndex >= this.xDimension * (this.yDimension - 1)){
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
        } else if (cellIndex + 1 == xDimension * yDimension){
            return true;
        }
        return false;
    }

    private int[] results;

    public int[] generateResults(){
        return new int[0];
    }

    public int[] getResults(){
        return new int[1];
    }

}
