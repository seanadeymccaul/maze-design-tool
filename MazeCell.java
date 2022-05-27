public class MazeCell {

    private int id;
    private int index;
    private int wallLeft;
    private int wallRight;
    private int wallAbove;
    private int wallBelow;

    public MazeCell(int id, int wallLeft, int wallRight, int wallAbove, int wallBelow, int index){
        this.id = id;
        this.index = index;
        this.wallLeft = wallLeft;
        this.wallRight = wallRight;
        this.wallAbove = wallAbove;
        this.wallBelow = wallBelow;
    }

    public boolean CheckIfTop(int xDimension, int yDimension){
        if (this.index < xDimension){
            return true;
        }
        return false;
    }

    public boolean CheckIfBottom(int xDimension, int yDimension){
        if (this.index >= xDimension * (yDimension - 1)){
            return true;
        }
        return false;
    }

    public boolean CheckIfLeft(int xDimension, int yDimension){
        if (this.index%xDimension == 0){
            return true;
        }
        return false;
    }

    public boolean CheckIfRight(int xDimension, int yDimension){
        if (this.index%xDimension == xDimension - 1){
            return true;
        } else if (this.index + 1 == xDimension * yDimension){
            return true;
        }
        return false;
    }

}
