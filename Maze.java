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

abstract class Maze {

    // Fields

    private String name;
    public String GetName(){return name;}
    public void SetName(String n){name = n;}

    private String author;
    public String GetAuthor(){return author;}
    public void SetAuthor(String n){author = n;}

    protected MazeCell[] mazeData;
    public MazeCell[] GetMazeData(){return mazeData;}
    public void SetMazeData(MazeCell[] d){mazeData = d;}

    protected UIPanelDisplayCell[] displayData;
    public UIPanelDisplayCell[] GetDisplayData(){return displayData;}

    protected int xDimension, yDimension, cellCount, logoIndex, logoWidth, logoHeight;
    public int GetXDimension(){return xDimension;}
    public int GetYDimension(){return yDimension;}
    public int GetCellCount(){return cellCount;}
    public void SetXDimension(int x){xDimension = x;}
    public void SetYDimension(int y){yDimension = y;}
    public void SetImageList(ArrayList<MazeImage> a){imageList = a;}
    public ArrayList<Integer> solutionDirections = new ArrayList<>();

    protected String lastEditTime;
    public String GetLastEditTime(){return lastEditTime;}
    public void SetLastEditTime(String s){lastEditTime = s;}
    protected String creationTime;
    public String GetCreationTime(){return creationTime;}
    public void SetCreationTime(String c){creationTime = c;}

    protected ArrayList<MazeImage> imageList;
    public ArrayList<MazeImage> GetImageList(){return imageList;}

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
        this.cellCount = xDimension * yDimension;
        this.mazeData = new MazeCell[this.cellCount];
        this.lastEditTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.imageList = new ArrayList<MazeImage>();
    }

    /**
     * Populates the maze data with the content of a blank array
     * @throws IOException inserts the start and end images from file
     * @throws SQLException saves the maze information in database
     */
    public void GenerateBlankMaze() throws IOException, SQLException {
        for (int i = 0; i < this.cellCount; i++){
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
            this.mazeData[i] = new MazeCell(id,wallLeft,wallRight,wallAbove,wallBelow);
        }
    }

    /**
     * Populates the maze data with the content of an automatic generated array
     * @throws IOException inserts the start and end images from file
     * @throws SQLException saves the maze information in database
     */
    public void GenerateAutoMaze() throws IOException, SQLException {
        int i = 0;
        String lastMove = "";
        while (i < (xDimension * yDimension)){
            if (i + 1 == (xDimension * yDimension) || i + xDimension == (xDimension * yDimension)){
                i = 0;
                while (i < xDimension * yDimension){
                    if (mazeData[i].getValue() == 1){
                        int random = GetRandomInt(5);
                        if (random == 0){
                            ReplaceCell(new MazeCell(1,1,1,0,0),i);
                        } else if (random == 1){
                            ReplaceCell(new MazeCell(1,0,0,1,1),i);
                        } else if (random == 2){
                            ReplaceCell(new MazeCell(1,1,0,1,0),i);
                        } else if (random == 3){
                            ReplaceCell(new MazeCell(1,0,1,1,0),i);
                        } else if (random == 4) {
                            ReplaceCell(new MazeCell(1,1,0,0,1),i);
                        } else if (random == 5) {
                            ReplaceCell(new MazeCell(1, 0, 1, 0, 1), i);
                        }

                    }
                    i++;
                }
                return;
            }
            int rand = GetRandomInt(2);
            if (rand == 0) {
                if (!CheckRightBorder(i)) {
                    if (lastMove == "vertical"){
                        // get the cell and change bottom to border, and right side to no border
                        ReplaceCell(new MazeCell(4,1,0,0,1),i);
                    }
                    if (mazeData[i + 1].getValue() != 3){
                        ReplaceCell(new MazeCell(4, 0, 0, 1, 1), i + 1);  // cell with value 4;
                    }
                    i = i + 1;
                    lastMove = "horizontal";
                }
            } else {
                if (!CheckBottomBorder(i)) {
                    //
                    if (lastMove == "horizontal"){
                        // get the cell and change bottom to border, and right side to no border
                        ReplaceCell(new MazeCell(4,0,1,1,0),i);
                    }
                    if (mazeData[i + xDimension].getValue() != 3){
                        ReplaceCell(new MazeCell(4, 1, 1, 0, 0), i + xDimension);  // cell with value 4
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
    public void ReplaceCell(MazeCell newCell, int cellIndex){

        MazeCell replacement = new MazeCell(newCell.getValue(),newCell.getWallLeft(),newCell.getWallRight(),
                newCell.getWallAbove(),newCell.getWallBelow());

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

        if ((mazeImage.GetIndex() + mazeImage.GetWidth() + ((mazeImage.GetHeight()-1) * xDimension) <= xDimension*yDimension)){
            int scaledWidth = mazeImage.GetWidth() * 2000 / xDimension;
            int scaledHeight = (mazeImage.GetHeight() * 2000 / yDimension);
            // read input image
            File inputFile = new File(mazeImage.GetPath());
            BufferedImage inputImage = ImageIO.read(inputFile);
            // create output image
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
            // scales input image to the output image
            Graphics g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();
            // cut it up
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
                    ReplaceCell(new MazeCell(5,leftValue,rightValue,topValue,bottomValue),
                            mazeImage.GetIndex() + j + (xDimension * i));
                    BufferedImage croppedImage = outputImage.getSubimage(x, y, 2000 / xDimension, 2000 / yDimension);
                    JLabel picLabel = new JLabel(new ImageIcon(croppedImage));
                    this.displayData[mazeImage.GetIndex() + j + (xDimension * i)].add(picLabel);
                    x = x + 2000 / xDimension;
                }
                x = 0;
                y = y + 2000 / yDimension;
            }
        }
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

            int cur = nextToVisit.remove();

            System.out.println("Checking cell index " + cur);

            // If it is found then return
            if (mazeData[cur].getValue() == 3){
                System.out.println("Solution found");
                int parent = parentCells[mazeData.length-1];
                while (parent != 0){
                    directions.add(parent);
                    parent = parentCells[parent];
                }
                this.solutionDirections = directions;
                double cellsReached2 = (double)solutionDirections.size()/(xDimension*yDimension);
                this.cellsReached = (int)(cellsReached2 * 100);


                // Get dead ends
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

            // if not then mark it as visited
            else {

                // mark it as explored
                exploredCells[cur] = true;

                // add all neighbours to the list if they are valid

                // add the right neighbour
                if (mazeData[cur].getWallRight() == 0){
                    if (!exploredCells[cur + 1]){
                        nextToVisit.add(cur + 1);
                        //
                        parentCells[cur + 1] = cur;
                    }
                }

                // add the bottom neighbour
                if (mazeData[cur].getWallBelow() == 0){
                    if (!exploredCells[cur + xDimension]){
                        nextToVisit.add(cur + xDimension);
                        parentCells[cur + xDimension] = cur;
                    }
                }

                // add the top neighbour
                if (mazeData[cur].getWallAbove() == 0){
                    if (!exploredCells[cur - xDimension]){
                        nextToVisit.add(cur - xDimension);
                        parentCells[cur - xDimension] = cur;
                    }
                }

                // add the left neighbour
                if (mazeData[cur].getWallLeft() == 0){
                    System.out.println("Wall left is free " + cur);
                    if (!exploredCells[cur - 1]){
                        nextToVisit.add(cur - 1);
                        parentCells[cur - 1] = cur;
                    }
                }
            }

        }
        return false;
    }

    public void PublishMaze() throws IOException {

        GenerateDisplayData();
        UI.getInstance().display.SetDisplayedMaze(this);

        // Set the width and height
        // divide by 4.
        int width = 2000;
        int height = 2000;
        /**
         if (xDimension < 30){
         width = 64 * xDimension;
         } else if (xDimension < 50){
         width = 40 * xDimension;
         } else if (xDimension < 80){
         width = 16 * xDimension;
         }
         if (yDimension < 30){
         height = 64 * yDimension;
         } else if (yDimension < 50){
         height = 40 * yDimension;
         } else if (yDimension < 80){
         height = 16*yDimension;
         }*/

        // create a new JPanel and populate with displayData
        JPanel imageCanvas = new JPanel();
        imageCanvas.setLayout(new GridLayout(yDimension,xDimension));
        imageCanvas.setPreferredSize(new Dimension(width,height));
        imageCanvas.setBackground(Color.GRAY);
        imageCanvas.setBackground(Color.WHITE);
        for (int i = 0; i < this.xDimension*this.yDimension; i++){;
            imageCanvas.add(displayData[i]);
        }

        // create a frame to check
        JFrame frame = new JFrame();
        frame.add(imageCanvas);
        frame.setVisible(false);
        frame.pack();

        // save that JPanel
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
        } else if (cellIndex + 1 == this.cellCount){
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
