import java.sql.SQLException;

public class MazeAdult extends Maze{

    private byte[] logoImage;

    public MazeAdult(String name, int type, int xDimension, int yDimension) throws SQLException {
        super(name, type, xDimension, yDimension);
    }

    public MazeAdult() throws SQLException {
        super();

    }
}
