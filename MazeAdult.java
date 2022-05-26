import java.sql.SQLException;

public class MazeAdult extends Maze{

    private String startImagePath;
    private String endImagePath;

    public MazeAdult(String name, int type, int xDimension, int yDimension) throws SQLException {
        super(name, type, xDimension, yDimension);
    }

    public MazeAdult() throws SQLException {
        super();

    }
}
