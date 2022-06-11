import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

public class TestMazeDatabase {

    public TestMazeDatabase() throws SQLException {

    }

    public Maze maze;

    @BeforeEach
    public void initDatabase() throws SQLException, IOException {
        // Remove the maze
        MazeDatabase.getInstance().DropTestTable();
        // Create the maze
        this.maze = new MazeAdult("MazeName", "Maze Author", 10, 10);
        this.maze.GenerateAutoMaze();
        MazeDatabase.getInstance().CreateTable(maze, "Adult");
    }

    @Test
    // Create a table with a valid name
    public void TestCreateValidTable() throws SQLException {
        // Test that the maze table exists
        String[] mazeNames = MazeDatabase.getInstance().GetTableNames();
        boolean contains = false;
        for (String mazeName : mazeNames) {
            if ("MazeName".equals(mazeName)) {
                contains = true;
                break;
            }
        }
        assertTrue(contains);
    }

    @Test
    // Create a table with an invalid name
    public void TestCreateInvalidTable() throws SQLException {
        // Attempt to add the second maze again
        assertThrows(NullPointerException.class, () -> {
            MazeDatabase.getInstance().CreateTable(maze, "Adult");
        });
    }

    @Test
    // Create a table, alter the data, and save it, load it again
    public void TestSaveAndLoadTable() throws SQLException, IOException {
        //
        MazeImage mazeImage = new MazeImage("examplePath",1,1);
        // Load the instance and add the new author
        maze = MazeDatabase.getInstance().LoadTable("MazeName");
        maze.imageList.add(mazeImage);
        // Save it back to the database
        MazeDatabase.getInstance().SaveTable(maze);
        // Load it back to the maze variable
        maze = MazeDatabase.getInstance().LoadTable("MazeName");
        boolean populated = false;
        for (MazeImage m : maze.imageList){
            if (Objects.equals(m.GetPath(), "examplePath")){
                populated = true;
            }
        }
        assertTrue(populated);
    }

    @Test
    // Test inserting and retrieving a single value from a database table
    public void TestSetAndGetValue() throws SQLException {
        // Create and value to set and get
        int value = 53;
        // Insert value into the maze
        System.out.println("Testing the setting int");
        MazeDatabase.getInstance().SetInt("MazeName","xDimension",value,1);
        // Get the value as a new variable
        int sameValue = MazeDatabase.getInstance().GetInt("MazeName", "xDimension");
        assertEquals(value,sameValue);
    }

    @Test
    // Test inserting and retrieving an array value from a database table
    public void TestSetAndGetArrayValue() throws SQLException {
        // Create the array of values to set and get
        int[] values = new int[]{1, 2, 3, 4, 5};
        // Insert the array of values
        MazeDatabase.getInstance().SetIntColumn("MazeName","yDimension",values);
        // Get the array of values as a new variable
        Integer[] sameValues = MazeDatabase.getInstance().GetIntegerColumn("MazeName","yDimension");
        boolean equals = true;
        for (int i = 0; i < values.length; i++){
            if (values[i] != sameValues[i]) {
                equals = false;
                break;
            }
        }
        assertTrue(equals);
    }

    @Test
    // Test the helper method that converts between MazeCell and string data arrays
    public void TestCellAndStringArrayConversion(){
        // Create the strings for the maze cells
        String[] stringCells = new String[]{"10101","11111","00000"};
        // Convert them to MazeCell objects
        MazeCell[] mazeCells = MazeDatabase.getInstance().StringArrayToCellArray(stringCells);
        // Convert back to a new string arrays
        String[] newStringCells = MazeDatabase.getInstance().CellArrayToStringArray(mazeCells);
        assertArrayEquals(stringCells,newStringCells);
    }

}
