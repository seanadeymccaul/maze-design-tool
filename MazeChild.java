import java.sql.SQLException;

public class MazeChild extends Maze{

    private byte[] startImage;
    private byte[] endImage;

    public MazeChild(String name, int type, int xDimension, int yDimension) throws SQLException {
        super(name, type, xDimension, yDimension);
    }

    public MazeChild() throws SQLException {
        super();

    }
}
