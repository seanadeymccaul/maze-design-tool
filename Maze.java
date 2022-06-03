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

    protected ArrayList<MazeImage> imageList;
    public ArrayList<MazeImage> GetImageList(){return imageList;}

    public boolean paintSolution = false;

    // Default constructor

    public Maze() throws SQLException { }


    // New maze constructor

    public Maze(String name,String author, int xDimension, int yDimension) throws SQLException, IOException {
        this.name = name;
        this.author = author;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.cellCount = xDimension * yDimension;
        this.mazeData = new MazeCell[this.cellCount];
        this.lastEditTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.imageList = new ArrayList<MazeImage>();
    }




    // Generates a blank canvas

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


    // Generates a fully populated maze with solution

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

    // Override by adult and child classes

    public void GenerateDisplayData() throws IOException { }

    //
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

    public boolean CheckTopBorder(int cellIndex){
        if (cellIndex < this.xDimension){
            return true;
        }
        return false;
    }

    public boolean CheckBottomBorder(int cellIndex){
        if (cellIndex >= this.xDimension * (this.yDimension - 1)){
            return true;
        }
        return false;
    }

    public boolean CheckLeftBorder(int cellIndex){
        if (cellIndex%this.xDimension == 0){
            return true;
        }
        return false;
    }

    public boolean CheckRightBorder(int cellIndex){
        if (cellIndex%this.xDimension == this.xDimension - 1){
            return true;
        } else if (cellIndex + 1 == this.cellCount){
            return true;
        }
        return false;
    }

    protected int GetRandomInt(int max){
        return (int)(Math.random()*max);
    }

    public void InsertImage(MazeImage mazeImage) throws IOException {

        // If it is on the top then replace with just a top cell

        System.out.println("Inserting image " + mazeImage.GetPath() + "with index " + mazeImage.GetIndex() + "width " + mazeImage.GetWidth() +
                "and height " + mazeImage.GetHeight());
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
                    mazeData[mazeImage.GetIndex() + j + (xDimension * i)].setValue(5);
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



}
