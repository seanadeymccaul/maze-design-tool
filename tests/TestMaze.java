package tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;
import maze.*;


/**
 * Tests the Maze abstract class implementations
 */
public class TestMaze {

    public TestMaze(){}

    public Maze maze;

    @BeforeEach
    public void initDatabase() throws SQLException, IOException {
        // Remove the maze
        MazeDatabase.getInstance().DropTestTable();
        // Create the maze
        this.maze = new MazeAdult("MazeName", "Maze Author", 10, 10);
    }

    @Test
    // Test generating a blank maze by checking the values of each row (VALID CASE)
    public void TestBlankGenerator() throws SQLException, IOException {
        // Call the method
        this.maze.GenerateBlankMaze();
        // Check the first, middle, and last values of mazeData
        for (int i = this.maze.GetXDimension(); i < this.maze.GetXDimension()*(this.maze.GetYDimension()-1);i++){
            if (i%this.maze.GetXDimension() != 0 && i%this.maze.GetXDimension() != this.maze.GetXDimension()-1){
                assertEquals(0,this.maze.GetMazeData()[i].getWallAbove());
                assertEquals(0,this.maze.GetMazeData()[i].getWallBelow());
                assertEquals(0,this.maze.GetMazeData()[i].getWallLeft());
                assertEquals(0,this.maze.GetMazeData()[i].getWallRight());
            }
        }
    }

    @Test
    // Test generating an automatic maze by checking each cell is populated with a valid MazeDataCell (VALID CASE)
    public void TestAutoGenerator() throws SQLException, IOException {
        // Call the method
        this.maze.GenerateAutoMaze();
        // Check all cells for valid MazeDataCell
        boolean checker = true;
        for (MazeDataCell mazeDataCell : this.maze.GetMazeData()){
            if (Objects.equals(mazeDataCell, new MazeDataCell(1, 0, 0, 0, 0))){
                checker = false;
            } else if (mazeDataCell.getValue() == 0){
                checker = false;
            }
        }
        assertTrue(checker);
    }

    @Test
    // Test replacing a mazeData cell with a bordered cell and check neighbours (VALID CASE)
    public void TestReplaceBorderedCell() throws SQLException, IOException {
        // Use a method to generate the mazeData
        this.maze.GenerateAutoMaze();
        // Create a cell to replace with
        MazeDataCell mazeDataCell = new MazeDataCell(1,1,1,1,1);
        // Replace the middle cell
        int middleIndex = maze.GetXDimension()*maze.GetYDimension()/2;
        this.maze.ReplaceCell(mazeDataCell, middleIndex);
        // Check the borders of surrounding cells to make sure wall
        assertEquals(1,this.maze.GetMazeData()[middleIndex-this.maze.GetXDimension()].getWallBelow());
        assertEquals(1,this.maze.GetMazeData()[middleIndex-1].getWallRight());
        assertEquals(1,this.maze.GetMazeData()[middleIndex+1].getWallLeft());
        assertEquals(1,this.maze.GetMazeData()[middleIndex+this.maze.GetXDimension()].getWallAbove());
    }

    @Test
    // Test replacing a mazeData cell with an empty cell and check neighbours (VALID CASE)
    public void TestReplaceEmptyCell() throws SQLException, IOException {
        // Use a method to generate the maze
        this.maze.GenerateAutoMaze();
        // Create a cell to replace with
        MazeDataCell mazeDataCell = new MazeDataCell(1,0,0,0,0);
        // Replace the middle cell
        int middleIndex = (maze.GetXDimension()/2)*(maze.GetYDimension()/2);
        this.maze.ReplaceCell(mazeDataCell, middleIndex);
        // Check the borders of surrounding cells to make sure empty
        assertEquals(0,this.maze.GetMazeData()[middleIndex-this.maze.GetXDimension()].getWallBelow());
        assertEquals(0,this.maze.GetMazeData()[middleIndex-1].getWallRight());
        assertEquals(0,this.maze.GetMazeData()[middleIndex+1].getWallLeft());
        assertEquals(0,this.maze.GetMazeData()[middleIndex+this.maze.GetXDimension()].getWallAbove());
    }

    @Test
    // Test the border condition functions by inserting an empty cell into the top left of the maze (VALID CASE)
    public void TestTopLeftBorder() throws SQLException, IOException {
        // Use a method to generate the maze
        this.maze.GenerateAutoMaze();
        // Create a cell to replace with
        MazeDataCell mazeDataCell = new MazeDataCell(1,0,0,0,0);
        // Replace the top left cell
        int index = 0;
        this.maze.ReplaceCell(mazeDataCell, index);
        // Check the borders
        assertEquals(1,this.maze.GetMazeData()[index].getWallAbove());
        assertEquals(1,this.maze.GetMazeData()[index].getWallLeft());
    }

    @Test
    // Test the border condition functions by inserting an empty cell into the top right of the maze (VALID CASE)
    public void TestTopRightBorder() throws SQLException, IOException {
        // Use a method to generate the maze
        this.maze.GenerateAutoMaze();
        // Create a cell to replace with
        MazeDataCell mazeDataCell = new MazeDataCell(1,0,0,0,0);
        // Replace the top right cell
        int index = this.maze.GetXDimension()-1;
        this.maze.ReplaceCell(mazeDataCell, index);
        // Check the borders
        assertEquals(1,this.maze.GetMazeData()[index].getWallAbove());
        assertEquals(1,this.maze.GetMazeData()[index].getWallRight());
    }

    @Test
    // Test the border condition functions by inserting an empty cell into the bottom left of the maze (VALID CASE)
    public void TestBottomLeftBorder() throws SQLException, IOException {
        // Use a method to generate the maze
        this.maze.GenerateAutoMaze();
        // Create a cell to replace with
        MazeDataCell mazeDataCell = new MazeDataCell(1,0,0,0,0);
        // Replace the bottom left cell
        int index = this.maze.xDimension * (this.maze.GetYDimension()-1);
        this.maze.ReplaceCell(mazeDataCell, index);
        // Check the borders
        assertEquals(1,this.maze.GetMazeData()[index].getWallLeft());
        assertEquals(1,this.maze.GetMazeData()[index].getWallBelow());
    }

    @Test
    // Test the border condition functions by inserting an empty cell into the bottom right of the maze (VALID CASE)
    public void TestBottomRightBorder() throws SQLException, IOException {
        // Use a method to generate the maze
        this.maze.GenerateAutoMaze();
        // Create a cell to replace with
        MazeDataCell mazeDataCell = new MazeDataCell(1,0,0,0,0);
        // Replace the middle cell
        int index = (maze.GetXDimension()*maze.GetYDimension())-1;
        this.maze.ReplaceCell(mazeDataCell, index);
        // Check the borders
        assertEquals(1,this.maze.GetMazeData()[index].getWallBelow());
        assertEquals(1,this.maze.GetMazeData()[index].getWallRight());
    }

    @Test
    // Insert valid image changes the maze data id to the image (id = 5) (VALID CASE)
    public void InsertValidImage() throws IOException, SQLException {
        this.maze.GenerateBlankMaze();
        this.maze.imageList.add(new MazeImage("arrow.png",1,1,1));
        this.maze.GenerateDisplayData();
        assertEquals(5,this.maze.GetMazeData()[1].getValue());
    }

    @Test
    // Insert an invalid image changes will return an IO exception (EXCEPTION CASE)
    public void InsertInvalidImage(){
        assertThrows(IOException.class, () -> {
            this.maze.GenerateBlankMaze();
            this.maze.imageList.add(new MazeImage("arrowInvalid.png",1,1,1));
            this.maze.GenerateDisplayData();
        });
    }

}
