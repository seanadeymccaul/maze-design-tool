public class MazeCell {

    private int value;
    public int getValue(){return value;}
    public void setValue(int v){this.value = v;}
    private int wallAbove;
    public int getWallAbove(){return wallAbove;}
    public void setWallAbove(int w){this.wallAbove = w;}
    private int wallBelow;
    public int getWallBelow(){return wallBelow;}
    public void setWallBelow(int w){this.wallBelow = w;}
    private int wallLeft;
    public int getWallLeft(){return wallLeft;}
    public void setWallLeft(int w){this.wallLeft = w;}
    private int wallRight;
    public int getWallRight(){return wallRight;}
    public void setWallRight(int w){this.wallRight = w;}

    public MazeCell(int value, int wallLeft, int wallRight, int wallAbove, int wallBelow){
        this.value = value;
        this.wallLeft = wallLeft;
        this.wallRight = wallRight;
        this.wallAbove = wallAbove;
        this.wallBelow = wallBelow;
    }


}
