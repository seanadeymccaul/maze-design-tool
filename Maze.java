import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * An abstract class that implements some methods of AdultMaze and ChildMaze
 */
abstract class Maze {

    // Fields
    private String name;
    public String GetName(){return name;}

    private String author;
    public String GetAuthor(){return author;}
    public void SetAuthor(String a){author = a;}

    protected MazeDataCell[] mazeData;
    public MazeDataCell[] GetMazeData(){ return mazeData;}
    public void SetMazeData(MazeDataCell[] m){mazeData = m;}

    protected MazeDisplayCell[] displayData;
    public MazeDisplayCell[] GetDisplayData(){return displayData;}

    protected int xDimension;
    public int GetXDimension(){return xDimension;}

    protected int yDimension;
    public int GetYDimension(){return yDimension;}

    protected ArrayList<MazeImage> imageList;
    public ArrayList<MazeImage> GetImageList(){return imageList;}
    public void SetImageList(ArrayList<MazeImage> l){imageList = l;}

    protected String creationTime;
    public String GetCreationTime(){return creationTime;}
    public void SetCreationTime(String c){creationTime = c;}

    protected String lastEditTime;
    public String GetLastEditTime(){return lastEditTime;}
    public void SetLastEditTime(String l){lastEditTime = l;}

    public ArrayList<Integer> solutionDirections = new ArrayList<>();
    public boolean paintSolution = false;
    public int cellsReached;
    public int deadEnds;

    /**
     * Default constructor
     * @throws SQLException
     */
    public Maze() { }

    /**
     * Constructor for creating a new maze instance in the database
     * @param name the name to save the maze as
     * @param author the name of the author
     * @param xDimension the cell count of the maze width
     * @param yDimension the cell count of the maze height
     * @throws SQLException creates a new table and populates with data in mariadb
     */
    public Maze(String name,String author, int xDimension, int yDimension) throws SQLException {
        this.name = name;
        this.author = author;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.mazeData = new MazeDataCell[xDimension*yDimension];
        this.lastEditTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.imageList = new ArrayList<MazeImage>();
    }

    /**
     * Populates the maze data with the content of a blank array
     * @throws IOException inserts the start and end images from file
     * @throws SQLException saves the maze information in database
     */
    public void GenerateBlankMaze() throws IOException, SQLException {

        // Populate each cell with empty MazeDataCell with respect to maze border conditions
        for (int i = 0; i < xDimension*yDimension; i++){
            int id = 1, wallAbove = 0, wallBelow = 0, wallLeft = 0, wallRight = 0;
            if (CheckTopBorder(i)){
                wallAbove = 1;
            }
            if (CheckBottomBorder(i)){
                wallBelow = 1;
            }
            if (CheckLeftBorder(i)){
                wallLeft = 1;
            }
            if (CheckRightBorder(i)){
                wallRight = 1;
            }
            this.mazeData[i] = new MazeDataCell(id,wallLeft,wallRight,wallAbove,wallBelow);
        }
    }

    /**
     * Populates the maze data with the content of an automatic generated array
     * @throws IOException inserts the start and end images from file
     * @throws SQLException saves the maze information in database
     */
    public void GenerateAutoMaze() throws IOException, SQLException {

        // Index for populating
        int i = 0;

        // Holds the last move (horizontal or vertical), used to determine use of corner piece
        String lastMove = "";

        // Iterate over the maze
        while (i < (xDimension * yDimension)){

            // Check if reached the end
            if (i + 1 == (xDimension * yDimension) || i + xDimension == (xDimension * yDimension)){
                i = 0;

                while (i < xDimension * yDimension){

                    // If it's a valid cell (not start, end, image, or solution value)
                    if (mazeData[i].getValue() == 1){

                        // Assign a cell depending on random integer between 5
                        int random = GetRandomInt(5);
                        if (random == 0){
                            ReplaceCell(new MazeDataCell(1,1,1,0,0),i);
                        } else if (random == 1){
                            ReplaceCell(new MazeDataCell(1,0,0,1,1),i);
                        } else if (random == 2){
                            ReplaceCell(new MazeDataCell(1,1,0,1,0),i);
                        } else if (random == 3){
                            ReplaceCell(new MazeDataCell(1,0,1,1,0),i);
                        } else if (random == 4) {
                            ReplaceCell(new MazeDataCell(1,1,0,0,1),i);
                        } else if (random == 5) {
                            ReplaceCell(new MazeDataCell(1, 0, 1, 0, 1), i);
                        }

                    }
                    i++;
                }
                return;
            }

            // Get random int between 2 and use it to decide on a vertical or horizontal move for the solution
            int rand = GetRandomInt(2);
            if (rand == 0) {
                if (!CheckRightBorder(i)) {
                    if (lastMove.equals("vertical")){
                        // get the cell and change bottom to border, and right side to no border
                        ReplaceCell(new MazeDataCell(4,1,0,0,1),i);
                    }
                    if (mazeData[i + 1].getValue() != 3){
                        ReplaceCell(new MazeDataCell(4, 0, 0, 1, 1), i + 1);  // cell with value 4;
                    }
                    i = i + 1;
                    lastMove = "horizontal";
                }
            } else {
                if (!CheckBottomBorder(i)) {
                    //
                    if (lastMove.equals("horizontal")){
                        // get the cell and change bottom to border, and right side to no border
                        ReplaceCell(new MazeDataCell(4,0,1,1,0),i);
                    }
                    if (mazeData[i + xDimension].getValue() != 3){
                        ReplaceCell(new MazeDataCell(4, 1, 1, 0, 0), i + xDimension);  // cell with value 4
                    }
                    i = i + xDimension;
                    lastMove = "vertical";
                }
            }
        }
    }

    /**
     * Populates the display data array with UIPanelDisplayCells depending on mazeData and imageList
     * @throws IOException
     */
    public void GenerateDisplayData() throws IOException { }

    /**
     * Replace a mazeCell item in the maze data array with a new mazeCell, and updates the borders of surrounding cells
     * @param newCell new mazeCell to insert
     * @param cellIndex the index of maze data array where the cell is to be replaced
     */
    public void ReplaceCell(MazeDataCell newCell, int cellIndex){

        MazeDataCell replacement = new MazeDataCell(newCell.getValue(),newCell.getWallLeft(),newCell.getWallRight(),
                newCell.getWallAbove(),newCell.getWallBelow());

        // Check the border conditions of the maze, and adjust the surrounding cell data respectively
        if (CheckTopBorder(cellIndex)){
            replacement.setWallAbove(1);
        } else {
            this.mazeData[cellIndex - xDimension].setWallBelow(replacement.getWallAbove());
        }
        if (CheckBottomBorder(cellIndex)){
            replacement.setWallBelow(1);
        } else {
            this.mazeData[cellIndex + xDimension].setWallAbove(replacement.getWallBelow());
        }
        if (CheckLeftBorder(cellIndex)){
            replacement.setWallLeft(1);
        } else {
            this.mazeData[cellIndex - 1].setWallRight(replacement.getWallLeft());
        }
        if (CheckRightBorder(cellIndex)){
            replacement.setWallRight(1);
        } else {
            this.mazeData[cellIndex + 1].setWallLeft(replacement.getWallRight());
        }
        this.mazeData[cellIndex] = replacement;
    }

    /**
     * Takes an image and transforms it before adding it to the display data array
     * @param mazeImage mazeImage object to add to the array
     * @throws IOException attempts to access file from mazeImage path
     */
    public void InsertImage(MazeImage mazeImage) throws IOException {

        // Check if image will fit on the maze from its specified index value
        if ((mazeImage.GetIndex() + mazeImage.GetWidth() + ((mazeImage.GetHeight()-1) * xDimension) <= xDimension*yDimension)){

            // Scale the image to a new size respective of specified height and width dimensions
            int scaledWidth = mazeImage.GetWidth() * 2000 / xDimension;
            int scaledHeight = (mazeImage.GetHeight() * 2000 / yDimension);

            // Read input image
            File inputFile = new File(mazeImage.GetPath());
            BufferedImage inputImage = ImageIO.read(inputFile);

            // Create output image
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

            // Transform image to scaled version
            Graphics g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // Divide the image into sub images for each cell
            int x = 0, y = 0;
            for (int i = 0; i < mazeImage.GetHeight(); i++) {
                for (int j = 0; j < mazeImage.GetWidth(); j++) {
                    // if i = 0 replace it with id of 5 and also top
                    int topValue = 0, bottomValue = 0, leftValue = 0, rightValue = 0;
                    if (i == 0){
                        topValue = 1;
                    }
                    if (j == 0){
                        leftValue = 1;
                    }
                    if (j == mazeImage.GetWidth()-1){
                        rightValue = 1;
                    }
                    if (i == mazeImage.GetHeight()-1){
                        bottomValue = 1;
                    }

                    // If the image is not replacing a reserved cell, then specify a new image id (4), otherwise keep the previous id
                    if(mazeData[mazeImage.GetIndex() + j + (xDimension * i)].getValue() == 2){
                        ReplaceCell(new MazeDataCell(2,1,0,0,0),
                                mazeImage.GetIndex() + j + (xDimension * i));
                    } else if (mazeData[mazeImage.GetIndex() + j + (xDimension * i)].getValue() == 3){
                        ReplaceCell(new MazeDataCell(3,0,0,0,0),
                                mazeImage.GetIndex() + j + (xDimension * i));
                    } else {
                        ReplaceCell(new MazeDataCell(5,leftValue,rightValue,topValue,bottomValue),
                                mazeImage.GetIndex() + j + (xDimension * i));
                    }

                    BufferedImage croppedImage = outputImage.getSubimage(x, y, 2000 / xDimension, 2000 / yDimension);
                    JLabel picLabel = new JLabel(new ImageIcon(croppedImage));

                    // Assign the image to display data
                    this.displayData[mazeImage.GetIndex() + j + (xDimension * i)].add(picLabel);
                    x = x + 2000 / xDimension;
                }
                x = 0;
                y = y + 2000 / yDimension;
            }
        }
        UI.getInstance().editor.current = true;
    }

    /**
     * Attempts to find an optimal solution for the current maze data, if possible it sets solutionDirections, deadEnds, and cellsReached
     * @return true if a solution is found
     */
    public boolean Solve(){

        // Boolean list of explored cells
        Boolean[] exploredCells = new Boolean[mazeData.length];
        Arrays.fill(exploredCells, false);

        // Int list to keep track of parents
        int[] parentCells = new int[mazeData.length];

        // An arraylist of the directions
        ArrayList<Integer> directions = new ArrayList<>();

        // Linked list of next cells to visit
        LinkedList<Integer> nextToVisit = new LinkedList<>();
        int start = 0;
        nextToVisit.add(start);

        // Remove a cell from the list and analyse it
        while (!nextToVisit.isEmpty()){

            int cursor = nextToVisit.remove();
            System.out.println("cursor is "+cursor);

            // If it is found then return
            if (mazeData[cursor].getValue() == 3){

                // Calculate and assign solution path
                int parent = parentCells[cursor];
                while (parent != 0){
                    directions.add(parent);
                    parent = parentCells[parent];
                }
                this.solutionDirections = directions;
                double cellsReached2 = (double)solutionDirections.size()/(xDimension*yDimension);
                this.cellsReached = (int)(cellsReached2 * 100);

                // Calculate and assign dead ends
                int deadEnds = 0;
                for (int i = 0; i < mazeData.length; i++){
                    int total = mazeData[i].getWallAbove() + mazeData[i].getWallRight() + mazeData[i].getWallLeft() +
                            mazeData[i].getWallBelow();
                    if (total > 2){
                        deadEnds++;
                    }
                }
                double deadEnds2 = (double)deadEnds/(xDimension*yDimension);
                this.deadEnds = (int)(deadEnds2 * 100);

                return true;
            }

            // If not then mark it as visited
            else {

                // Mark it as explored
                exploredCells[cursor] = true;

                // Add all neighbours to the list if they are valid

                // Add the right neighbour
                if (mazeData[cursor].getWallRight() == 0){
                    if (!exploredCells[cursor + 1]){
                        nextToVisit.add(cursor + 1);
                        //
                        parentCells[cursor + 1] = cursor;
                    }
                }

                // Add the bottom neighbour
                if (mazeData[cursor].getWallBelow() == 0){
                    if (!exploredCells[cursor + xDimension]){
                        nextToVisit.add(cursor + xDimension);
                        parentCells[cursor + xDimension] = cursor;
                    }
                }

                // Add the top neighbour
                if (mazeData[cursor].getWallAbove() == 0 && cursor != 0){
                    System.out.println(xDimension);
                    if (!exploredCells[cursor - xDimension]){
                        nextToVisit.add(cursor - xDimension);
                        parentCells[cursor - xDimension] = cursor;
                    }
                }

                // Add the left neighbour
                if (mazeData[cursor].getWallLeft() == 0){
                    if (!exploredCells[cursor - 1]){
                        nextToVisit.add(cursor - 1);
                        parentCells[cursor - 1] = cursor;
                    }
                }
            }

        }
        return false;
    }

    /**
     * Saves the current maze to a jpeg image file
     * @throws IOException Creates a new file to save in the file system
     */
    public void PublishMaze() throws IOException {

        GenerateDisplayData();
        UI.getInstance().display.SetDisplayedMaze(this);

        // Set the width and height
        int width = 2000;
        int height = 2000;

        // Create a new JPanel and populate with displayData
        JPanel imageCanvas = new JPanel();
        imageCanvas.setLayout(new GridLayout(yDimension,xDimension));
        imageCanvas.setPreferredSize(new Dimension(width,height));
        imageCanvas.setBackground(Color.GRAY);
        imageCanvas.setBackground(Color.WHITE);
        for (int i = 0; i < this.xDimension*this.yDimension; i++){;
            imageCanvas.add(displayData[i]);
        }

        // Create a frame to check
        JFrame frame = new JFrame();
        frame.add(imageCanvas);
        frame.setVisible(false);
        frame.pack();

        // Save that JPanel
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        imageCanvas.paint(g2);
        try{
            ImageIO.write(image,"jpeg",new File(this.name+".jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private helper method for ReplaceCell to make sure a top border is not altered
     * @param cellIndex index of the maze
     * @return true if the cell index is a top border cell
     */
    private boolean CheckTopBorder(int cellIndex){
        if (cellIndex < this.xDimension){
            return true;
        }
        return false;
    }

    /**
     * Private helper method for ReplaceCell to make sure a bottom border is not altered
     * @param cellIndex index of the maze
     * @return true if the cell index is a bottom border cell
     */
    private boolean CheckBottomBorder(int cellIndex){
        if (cellIndex >= this.xDimension * (this.yDimension - 1)){
            return true;
        }
        return false;
    }

    /**
     * Private helper method for ReplaceCell to make sure a left border is not altered
     * @param cellIndex index of the maze
     * @return true if the cell index is a left border cell
     */
    private boolean CheckLeftBorder(int cellIndex){
        if (cellIndex%this.xDimension == 0){
            return true;
        }
        return false;
    }

    /**
     * Private helper method for ReplaceCell to make sure a right border is not altered
     * @param cellIndex index of the maze
     * @return true if the cell index is a right border cell
     */
    private boolean CheckRightBorder(int cellIndex){
        if (cellIndex%this.xDimension == this.xDimension - 1){
            return true;
        } else if (cellIndex + 1 == xDimension*yDimension){
            return true;
        }
        return false;
    }

    /**
     * Private helper method for GenerateAutoMaze to generate random int for direction
     * @param max the maximum int to be returned
     * @return random int between 0 and the max value
     */
    protected int GetRandomInt(int max){
        return (int)(Math.random()*max);
    }

}
