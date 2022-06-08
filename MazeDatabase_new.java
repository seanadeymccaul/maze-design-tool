import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MazeDatabase_new {

    private Connection connection;

    protected MazeDatabase_new(){

        // Connect to the database
        try{
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/MazeDatabase","root","");
            Statement statement = this.connection.createStatement();
            statement.execute("USE MazeDatabase;");
        } catch (SQLException sql){
            System.err.println(sql);
        }

    }

    private static class DatabaseHolder {
        private final static MazeDatabase_new INSTANCE;
        static {
            INSTANCE = new MazeDatabase_new();
        }
    }

    public static MazeDatabase_new getInstance(){
        return DatabaseHolder.INSTANCE;
    }

    public void CreateTable(Maze maze, String mazeType) throws SQLException {

        // Create the table
        String sql = "CREATE TABLE " + maze.GetName() + " (id int NOT NULL AUTO_INCREMENT, mazeData VARCHAR(5), " +
                "imagePath VARCHAR(64), imageIndex INT, imageHeight INT, creationTime VARCHAR(64), imageWidth INT, mazeAuthor VARCHAR(64), " +
                "mazeType VARCHAR(64), xDimension INT, yDimension INT, lastEditTime " + "VARCHAR(64), PRIMARY KEY (id));";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();

        // Set the mazeData
        for (int i = 0; i < maze.GetMazeData().length; i++){
            sql = "INSERT INTO " + maze.GetName() + " (mazeData) VALUES (" +
                    CellArrayToStringArray(maze.GetMazeData())[i] + ");";
            statement = connection.prepareStatement(sql);
            statement.execute();
        }

        // Insert maze information
        sql = "UPDATE " + maze.GetName() + " SET mazeType = '" + mazeType + "', xDimension = " + maze.GetXDimension() +
                ", yDimension = " + maze.GetYDimension() + ", mazeAuthor = '" + maze.GetAuthor() + "', lastEditTime = '" +
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + "', creationTime = '" +
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + "' WHERE id = 1;";
        statement = connection.prepareStatement(sql);
        System.out.println(sql);
        statement.execute();

        // Insert the image data
        int i = 0;
        for (MazeImage mazeImage : maze.GetImageList()){
            SetString(maze.GetName(),"imagePath",mazeImage.GetPath(),i+1);
            SetInt(maze.GetName(),"imageIndex",mazeImage.GetIndex(),i+1);
            SetInt(maze.GetName(),"imageHeight",mazeImage.GetHeight(),i+1);
            SetInt(maze.GetName(),"imageWidth",mazeImage.GetWidth(),i+1);
        }

    }

    public void SaveTable(Maze maze) throws SQLException {

        // Update the maze data
        SetStringColumn(maze.GetName(),"mazeData", CellArrayToStringArray(maze.GetMazeData()));
        SetString(maze.GetName(),"lastEditTime",maze.GetLastEditTime(),1);

        // Update the image data
        int i = 0;
        for (MazeImage mazeImage : maze.GetImageList()){
            SetString(maze.GetName(),"imagePath",mazeImage.GetPath(),i+1);
            SetInt(maze.GetName(),"imageIndex",mazeImage.GetIndex(),i+1);
            SetInt(maze.GetName(),"imageHeight",mazeImage.GetHeight(),i+1);
            SetInt(maze.GetName(),"imageWidth",mazeImage.GetWidth(),i+1);
            i = i + 1;
        }

    }

    public Maze LoadTable(String tableName) throws SQLException {

        Maze maze = null;

        // Check if adult or child maze
        if (Objects.equals(GetString(tableName, "mazeType"), "Adult")){
            maze = new MazeAdult();
        } else if (Objects.equals(GetString(tableName, "mazeType"),"Child")){
            maze = new MazeChild();
        }

        //
        if (maze != null) {

            // Set maze information
            maze.SetName(tableName);
            maze.SetAuthor(GetString(tableName,"mazeAuthor"));
            maze.SetMazeData(StringArrayToCellArray(GetStringColumn(tableName,"mazeData")));
            maze.SetXDimension(GetInt(tableName,"xDimension"));
            maze.SetYDimension(GetInt(tableName,"yDimension"));
            maze.SetLastEditTime(GetString(tableName,"lastEditTime"));
            maze.SetCreationTime(GetString(tableName,"creationTime"));

            // Set image information
            String[] imagePaths = GetStringColumn(tableName,"imagePath");
            Integer[] imageIndexes = GetIntegerColumn(tableName,"imageIndex");
            Integer[] imageHeights = GetIntegerColumn(tableName,"imageHeight");
            Integer[] imageWidths = GetIntegerColumn(tableName,"imageWidth");
            ArrayList<MazeImage> arrayList = new ArrayList<>();
            for (int i = 0; i < imagePaths.length; i++){
                if (imagePaths[i] != null){
                    arrayList.add(new MazeImage(imagePaths[i],imageWidths[i],imageHeights[i],imageIndexes[i]));
                }
            }
            maze.SetImageList(arrayList);
        }

        return maze;

    }


    public String[] GetTableNames() throws SQLException {
        List<String> namesList = new ArrayList<String>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT table_name FROM information_schema." +
                "tables WHERE table_schema = 'MazeDatabase';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            namesList.add(resultSet.getString(1));
        }
        String[] names = namesList.toArray(new String[namesList.size()]);
        return names;
    }


    public String GetString(String tableName, String columnName) throws SQLException {
        String result = "";
        String sql = "SELECT " + columnName + " FROM " + tableName + " WHERE id = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            result = resultSet.getString(1);
        }
        return result;
    }

    public int GetInt(String tableName, String columnName) throws SQLException {
        int result = 0;
        String sql = "SELECT " + columnName + " FROM " + tableName + " WHERE id = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            result = resultSet.getInt(1);
        }
        return result;
    }

    public void SetString(String tableName, String columnName, String data, int id) throws SQLException {
        String sql = "UPDATE " + tableName + " SET " + columnName + " = '" + data + "' WHERE id = " + id + ";";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    public void SetInt(String tableName, String columnName, int data, int id) throws SQLException {
        String sql = "UPDATE " + tableName + " SET " + columnName + " = " + data + " WHERE id = " + id + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }

    public String[] GetStringColumn(String tableName, String columnName) throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        String sql = "SELECT " + columnName + " FROM " + tableName + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            arrayList.add(resultSet.getString(1));
        }
        String[] data = arrayList.toArray(new String[arrayList.size()]);
        return data;
    }

    public Integer[] GetIntegerColumn(String tableName, String columnName) throws SQLException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String sql = "SELECT " + columnName + " FROM " + tableName + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            arrayList.add(resultSet.getInt(1));
        }
        Integer[] data = arrayList.toArray(new Integer[arrayList.size()]);
        return data;
    }

    public void SetStringColumn(String tableName, String columnName, String[] data) throws SQLException {
        for (int i = 0; i < data.length; i++){
            String sql = "UPDATE " + tableName + " SET " + columnName + " = " + data[i] + " WHERE id = " + (i+1) + ";";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        }
    }

    public void SetIntColumn(String tableName, String columnName, int[] data) throws SQLException {
        for (int i = 0; i < data.length; i++){
            String sql = "UPDATE " + tableName + " SET " + columnName + " = " + data[i] + " WHERE id = " + (i+1) + ";";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        }
    }

    public String[] CellArrayToStringArray(MazeCell[] cellData){
        String[] mazeDataString = new String[cellData.length];
        for (int i = 0; i < cellData.length; i++){
            String value = Integer.toString(cellData[i].getValue()) + Integer.toString(cellData[i].getWallLeft()) +
                    Integer.toString(cellData[i].getWallRight()) + Integer.toString(cellData[i].getWallAbove()) +
                    Integer.toString(cellData[i].getWallBelow());
            mazeDataString[i] = value;
        }
        return mazeDataString;
    }

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


}

