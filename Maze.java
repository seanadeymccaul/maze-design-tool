import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

abstract class Maze {

    // Fields
    protected String name;
    public String getName(){return name;}
    protected MazeCell[] mazeData;
    public MazeCell[] getMazeData(){return mazeData;}
    public void setMazeData(MazeCell[] d){mazeData = d;}
    protected UIPanelDisplayCell[] displayData;
    public void setDisplayData(UIPanelDisplayCell[] d){displayData = d;}
    protected int xDimension;
    public int getXDimension(){return xDimension;}
    protected int yDimension;
    public int getYDimension(){return yDimension;}
    protected int cellCount;
    public int getCellCount(){return  cellCount;}
    protected MazeDatabase db;

    // Constructor
    public Maze() throws SQLException {
        this.db = new MazeDatabase();
    }

    public Maze(String name, int xDimension, int yDimension) throws SQLException {
        this.name = name;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.cellCount = xDimension * yDimension;
        this.mazeData = new MazeCell[this.cellCount];
        this.displayData = new UIPanelDisplayCell[this.cellCount];
        this.db = new MazeDatabase();
    }

    public UIPanelDisplayCell[] GetDisplayData() throws IOException {
        for (int i = 0; i < this.cellCount; i++){
            if (this.mazeData[i].getValue() < 5){
                UIPanelDisplayCell currentPanel = new UIPanelDisplayCell(this.mazeData[i],i);
                this.displayData[i] = currentPanel;
            }
        }
        return this.displayData;
    };

    // Implemented in Maze Adult and Child
    public void GenerateBlankMaze() {
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
        this.ReplaceCell(new MazeCell(3,1,0,0,0),0);
        this.ReplaceCell(new MazeCell(3,0,1,0,0),this.cellCount-1);
    }

    // Implemented in Maze Adult and Child
    public void GenerateAutoMaze() {
        this.GenerateBlankMaze();
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

    //
    public void GenerateSavedMaze(String mazeName) throws SQLException {
        this.mazeData = StringArrayToCellArray(db.GetMazeData(mazeName));
        Integer[] dimensions = db.GetMazeDimensions(mazeName);
        this.xDimension = dimensions[0];
        this.yDimension = dimensions[1];
        this.cellCount = this.xDimension * this.yDimension;
    }

    //
    public void SaveMaze(String tableName) throws SQLException {
        db.SetMazeData(tableName, CellArrayToStringArray(this.mazeData));
    }

    //
    public void ReplaceCell(MazeCell newCell, int cellIndex){
        if (CheckTopBorder(cellIndex)){
            newCell.setWallAbove(1);
        } else {
            this.mazeData[cellIndex - xDimension].setWallBelow(newCell.getWallAbove());
        }
        if (CheckBottomBorder(cellIndex)){
            newCell.setWallBelow(1);
        } else {
            this.mazeData[cellIndex + xDimension].setWallAbove(newCell.getWallBelow());
        }
        if (CheckLeftBorder(cellIndex)){
            newCell.setWallLeft(1);
        } else {
            this.mazeData[cellIndex - 1].setWallRight(newCell.getWallLeft());
        }
        if (CheckRightBorder(cellIndex)){
            newCell.setWallRight(1);
        } else {
            this.mazeData[cellIndex + 1].setWallLeft(newCell.getWallRight());
        }
        this.mazeData[cellIndex] = newCell;
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

    // COMPLETED and TESTED
    private String[] CellArrayToStringArray(MazeCell[] cellData){
        String[] mazeDataString = new String[mazeData.length];
        for (int i = 0; i < cellData.length; i++){
            String value = Integer.toString(cellData[i].getValue()) + Integer.toString(cellData[i].getWallLeft()) +
                    Integer.toString(cellData[i].getWallRight()) + Integer.toString(cellData[i].getWallAbove()) +
                    Integer.toString(cellData[i].getWallBelow());
            mazeDataString[i] = value;
        }
        return mazeDataString;
    }

    // COMPLETED and TESTED
    private MazeCell[] StringArrayToCellArray(String[] stringData){
        MazeCell[] cellData = new MazeCell[stringData.length];
        for (int i = 0; i < stringData.length; i++){
            cellData[i] = new MazeCell(Character.getNumericValue(stringData[i].charAt(0)),
                    Character.getNumericValue(stringData[i].charAt(3)),
                    Character.getNumericValue(stringData[i].charAt(4)),
                    Character.getNumericValue(stringData[i].charAt(1)),
                    Character.getNumericValue(stringData[i].charAt(2)));
        }
        return cellData;
    }

    protected int GetRandomInt(int max){
        return (int)(Math.random()*max);
    }

    public void InsertLogo(String path, int index, int height, int width) throws IOException {
        if (index + width + (height * xDimension) < this.cellCount){
            int displayCellWidth = 2000 / this.xDimension;
            Image image = ImageIO.read(new File(path));
            Image scaledImage = image.getScaledInstance(displayCellWidth * width,
                    displayCellWidth * height,Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            this.mazeData[index].setValue(5);
            this.displayData[index].InsertImage(picLabel);
            UI_new.getInstance().display.UpdateDisplay();

            /**
            BufferedImage image = ImageIO.read(new File(path));
            int xScale = image.getWidth() / displayCellWidth * width;
            int yScale = image.getHeight() / displayCellWidth * height;
            BufferedImage scaledImage = new BufferedImage(xScale * image.getWidth(), yScale * image.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D graph = (Graphics2D) scaledImage.getGraphics();
            graph.scale(xScale,yScale);
            graph.drawImage(image,0,0,null);
            graph.dispose();
            /**
            int x = 0, y = 0;
            for (int i = 0; i < height; i++){
                y = y + 200;
                for (int j = 0; j < width; j++){
                    // Reserve with id of 5
                    ReplaceCell(new MazeCell(5,1,1,1,1),index + i + j);
                    // Add respective
                    BufferedImage individualCrop = image.getSubimage(x,y,200,200);
                    // add that to the coordinates in UIMazePanelDisplay
                    JLabel picLabel = new JLabel(new ImageIcon(individualCrop));
                    this.displayData[index + j + (xDimension * i)].add(picLabel);
                }
            }*/

        }
    }

    /**
     *         BufferedImage image = ImageIO.read(new File(path));
     *         JLabel picLabel = new JLabel(new ImageIcon(image));
     *         add(picLabel);
     */

}
