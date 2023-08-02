package tests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;
import maze.*;


/**
 * Tests the functions of MazeDatabases
 */
public class TestMazeDatabase {

    public TestMazeDatabase() throws SQLException {}

    public Maze maze;

    @BeforeEach
    public void InitMaze() throws SQLException, IOException {
        MazeDatabase.getInstance().DropTestTable();
        this.maze = new MazeAdult("MazeName", "Maze Author", 10, 10);
        this.maze.GenerateAutoMaze();
    }

    @Test
    // Create a table with a valid name (VALID CASE)
    public void TestCreateValidTable() throws SQLException, IOException {
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
    // Create a table with a duplicate name (EXCEPTION CASE)
    public void TestCreateDuplicateTable() throws SQLException {
        // Attempt to add the second maze again
        assertThrows(SQLException.class, () -> {
            MazeDatabase.getInstance().CreateTable(maze, "Adult");
        });
    }

    @Test
    // Create a table with an invalid syntax name (EXCEPTION CASE)
    public void TestCreateInvalidTable(){
        assertThrows(SQLException.class, () -> {
            MazeDatabase.getInstance().CreateTable(new MazeAdult(";;;;","Maze Author",10,
                    10),"Adult");
        });
    }

    @Test
    // Create a table, alter the data, and save it, load it again (VALID CASE)
    public void TestSaveAndLoadTable() throws SQLException, IOException {
        //
        MazeImage mazeImage = new MazeImage("examplePath",1,1);
        // Load the instance and add the new author
        Maze maze = MazeDatabase.getInstance().LoadTable("MazeName");
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
    // Test inserting and retrieving a single value from a database table (VALID CASE)
    public void TestSetAndGetInt() throws SQLException {
        // Create and value to set and get
        int value = 53;
        // Insert value into the maze
        MazeDatabase.getInstance().SetInt("MazeName","xDimension",value,1);
        // Get the value as a new variable
        int sameValue = MazeDatabase.getInstance().GetInt("MazeName", "xDimension");
        assertEquals(value,sameValue);
    }

    @Test
    // Test inserting and retrieving a single string from a database table (VALID CASE)
    public void TestSetAndGetString() throws SQLException {
        // Create and value to set and get
        String value = "fifty-three";
        // Insert value into the maze
        MazeDatabase.getInstance().SetString("MazeName","imagePath",value,1);
        // Get the value as a new variable
        String sameValue = MazeDatabase.getInstance().GetString("MazeName", "imagePath");
        assertEquals(value,sameValue);
    }

    @Test
    // Test inserting the highest value to a database table (BOUNDARY CASE)
    public void TestSetAndGetHighestInt() throws SQLException {
        // Create and value to set and get
        int value = 2147483647;
        // Insert value into the maze
        MazeDatabase.getInstance().SetInt("MazeName","xDimension",value,1);
        // Get the value as a new variable
        int sameValue = MazeDatabase.getInstance().GetInt("MazeName", "xDimension");
        assertEquals(value,sameValue);
    }

    @Test
    // Test inserting the lowest possible value to a database table (BOUNDARY CASE)
    public void TestSetAndGetLowestInt() throws SQLException {
        // Create and value to set and get
        int value = -2147483648;
        // Insert value into the maze
        MazeDatabase.getInstance().SetInt("MazeName","xDimension",value,1);
        // Get the value as a new variable
        int sameValue = MazeDatabase.getInstance().GetInt("MazeName", "xDimension");
        assertEquals(value,sameValue);
    }

    @Test
    // Test inserting and retrieving an array value from a database table
    public void TestSetAndGetArrayInt() throws SQLException {
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
    // Test inserting and retrieving a string array value from a database table (VALID CASE)
    public void TestSetAndGetArrayString() throws SQLException {
        // Create the array of values to set and get
        String[] values = new String[]{"one", "two", "three", "four", "five"};
        // Insert the array of values
        MazeDatabase.getInstance().SetStringColumn("MazeName","imagePath",values);
        // Get the array of values as a new variable
        String[] sameValues = MazeDatabase.getInstance().GetStringColumn("MazeName","imagePath");
        boolean equals = true;
        for (int i = 0; i < values.length; i++){
            if (!Objects.equals(values[i], sameValues[i])) {
                equals = false;
                break;
            }
        }
        assertTrue(equals);
    }

    @Test
    // Test inserting a string array into an integer column (EXCEPTION CASE)
    public void TestSetAndGetInvalidColumnType() throws SQLException {
        // Create the array of values to set and get
        String[] values = new String[]{"one", "two", "three", "four", "five"};
        // Insert the array of values
        assertThrows(SQLException.class, () -> {
            MazeDatabase.getInstance().SetStringColumn("MazeName","yDimension",values);
        });
    }

    @Test
    // Test the helper method that converts between MazeCell and string data arrays (VALID CASE)
    public void TestCellAndStringArrayConversion(){
        // Create the strings for the maze cells
        String[] stringCells = new String[]{"10101","11111","00000"};
        // Convert them to MazeCell objects
        MazeDataCell[] mazeDataCells = MazeDatabase.getInstance().StringArrayToCellArray(stringCells);
        // Convert back to a new string arrays
        String[] newStringCells = MazeDatabase.getInstance().CellArrayToStringArray(mazeDataCells);
        assertArrayEquals(stringCells,newStringCells);
    }

}
