import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

abstract class Maze {

    // Fields

    private String name;
    public String GetName(){return name;}

    private String author;
    public String GetAuthor(){return author;}

    protected MazeCell[] mazeData;
    public MazeCell[] GetMazeData(){return mazeData;}

    protected UIPanelDisplayCell[] displayData;
    public UIPanelDisplayCell[] GetDisplayData(){return displayData;}

    protected int xDimension, yDimension, cellCount, logoIndex, logoWidth, logoHeight;
    public int GetXDimension(){return xDimension;}
    public int GetYDimension(){return yDimension;}
    public int GetCellCount(){return cellCount;}
    public int GetLogoIndex(){return logoIndex;}
    public int GetLogoWidth(){return logoWidth;}
    public int GetLogoHeight(){return logoHeight;}

    protected String creationTime, lastEditTime, startImagePath, endImagePath, logoImagePath;
    public String GetCreationTime(){return creationTime;}
    public String GetLastEditTime(){return lastEditTime;}
    public String GetStartImagePath(){return startImagePath;}
    public String GetEndImagePath(){return endImagePath;}
    public String GetLogoImagePath(){return logoImagePath;}


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
        this.logoIndex = GetRandomInt(cellCount-xDimension);
        this.logoHeight = 1;
        this.logoWidth = 1;
        System.out.println(logoIndex);
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
    public void GenerateSavedMaze(String mazeName) throws SQLException, IOException {
        this.name = mazeName;
        this.author = MazeDatabase_new.getInstance().GetString(mazeName,"mazeAuthor");
        this.mazeData = StringArrayToCellArray(MazeDatabase_new.getInstance().GetMazeData(mazeName));
        this.xDimension = MazeDatabase_new.getInstance().GetInt(mazeName,"xDimension");
        this.yDimension = MazeDatabase_new.getInstance().GetInt(mazeName,"xDimension");
        this.cellCount = xDimension * yDimension;
        this.lastEditTime = MazeDatabase_new.getInstance().GetString(mazeName,"lastEditTime");
        this.startImagePath = MazeDatabase_new.getInstance().GetString(mazeName,"startImagePath");
        this.endImagePath = MazeDatabase_new.getInstance().GetString(mazeName,"endImagePath");
        this.logoImagePath = MazeDatabase_new.getInstance().GetString(mazeName,"logoImagePath");
        this.logoIndex = MazeDatabase_new.getInstance().GetInt(mazeName,"logoImageIndex");
        this.logoHeight = MazeDatabase_new.getInstance().GetInt(mazeName,"logoImageHeight");
        this.logoWidth = MazeDatabase_new.getInstance().GetInt(mazeName,"logoImageWidth");
    }

    //
    public void SaveMaze(String tableName) throws SQLException {
        MazeDatabase_new.getInstance().SetMazeData(tableName,CellArrayToStringArray(mazeData));
    }

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

    // COMPLETED and TESTED
    public String[] CellArrayToStringArray(MazeCell[] cellData){
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
    public MazeCell[] StringArrayToCellArray(String[] stringData){
        MazeCell[] cellData = new MazeCell[stringData.length];
        for (int i = 0; i < stringData.length; i++){
            cellData[i] = new MazeCell(Character.getNumericValue(stringData[i].charAt(0)),
                    Character.getNumericValue(stringData[i].charAt(1)),
                    Character.getNumericValue(stringData[i].charAt(2)),
                    Character.getNumericValue(stringData[i].charAt(3)),
                    Character.getNumericValue(stringData[i].charAt(4)));

        }

        return cellData;
    }

    protected int GetRandomInt(int max){
        return (int)(Math.random()*max);
    }

    public void InsertImage(String path, int index, int height, int width) throws IOException {
        if ((index + width + ((height-1) * xDimension) <= cellCount)){
            int scaledWidth = width * 2000 / xDimension;
            int scaledHeight = height * 2000 / yDimension;
            // read input image
            File inputFile = new File(path);
            BufferedImage inputImage = ImageIO.read(inputFile);
            // create output image
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
            // scales input image to the output image
            Graphics g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();
            // cut it up
            int x = 0, y = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    mazeData[index + j + (xDimension * i)].setValue(5);
                    BufferedImage croppedImage = outputImage.getSubimage(x, y, 2000 / xDimension, 2000 / yDimension);
                    JLabel picLabel = new JLabel(new ImageIcon(croppedImage));
                    this.displayData[index + j + (xDimension * i)].add(picLabel);
                    x = x + 2000 / xDimension;
                }
                x = 0;
                y = y + 2000 / yDimension;
            }
        }
    }
}
