package maze;
/**
 * An object for storing image related data in a list
 */
public class MazeImage {

    // Fields

    private String path;
    public String GetPath(){return path;}

    private int index;
    public int GetIndex(){return index;}
    public void SetIndex(int i){index = i;}

    private int width;
    public int GetWidth(){return width;}

    private int height;
    public int GetHeight(){return height;}

    public MazeImage(String path, int width, int height){
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public MazeImage(String path, int width, int height, int index){
        this.path = path;
        this.width = width;
        this.height = height;
        this.index = index;
    }

}
