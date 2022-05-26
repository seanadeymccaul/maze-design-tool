public class MazeCell {



    public int value;
    public int valueAbove;
    public int valueBelow;
    public int valueLeft;
    public int valueRight;

    public MazeCell(int value, int valueLeft, int valueRight, int valueAbove, int valueBelow){
        this.value = value;
        this.valueLeft = valueLeft;
        this.valueRight = valueRight;
        this.valueAbove = valueAbove;
        this.valueBelow = valueBelow;
    }

}
