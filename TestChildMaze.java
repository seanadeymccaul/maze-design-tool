import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

public class TestChildMaze {

    public Maze maze;

    @BeforeEach
    public void initDatabase() throws SQLException, IOException {
        // Remove the maze
        MazeDatabase.getInstance().DropTestTable();
        // Create the maze
        this.maze = new MazeChild("MazeName", "Maze Author", 10, 10);
    }

    @Test
    // Test the initialisation of the child maze data (VALID CASE)
    public void TestChildInitialisation() throws SQLException, IOException {
        this.maze.GenerateAutoMaze();
        assertEquals(2,this.maze.GetMazeData()[0].getValue());
        assertEquals(2,this.maze.GetMazeData()[1].getValue());
        assertEquals(2,this.maze.GetMazeData()[maze.GetXDimension()].getValue());
        assertEquals(2,this.maze.GetMazeData()[maze.GetXDimension()+1].getValue());
        assertEquals(3,this.maze.GetMazeData()[(maze.GetXDimension()*maze.GetYDimension())-1].getValue());
        assertEquals(3,this.maze.GetMazeData()[(maze.GetXDimension()*maze.GetYDimension())-2].getValue());
        assertEquals(3,this.maze.GetMazeData()[(maze.GetXDimension()*maze.GetYDimension())- maze.GetXDimension()-1].getValue());
        assertEquals(3,this.maze.GetMazeData()[(maze.GetXDimension()*maze.GetYDimension())- maze.GetXDimension()-2].getValue());
    }

}
