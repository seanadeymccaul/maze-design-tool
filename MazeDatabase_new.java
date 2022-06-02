import java.io.IOException;
import java.sql.*;
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

    //
    public void CreateTable(Maze maze, String mazeType, String lastEditDate) throws SQLException {

        // Create the table
        String sql = "CREATE TABLE " + maze.GetName() + " (id int NOT NULL AUTO_INCREMENT, mazeData VARCHAR(5), " +
                "mazeAuthor VARCHAR(64), mazeType TEXT(64), xDimension INT, yDimension INT, startImagePath VARCHAR(64), " +
                "endImagePath VARCHAR(64), logoImagePath VARCHAR(64), logoImageIndex INT, logoImageHeight INT, " +
                "logoImageWidth INT, creationTime VARCHAR(64), lastEditTime VARCHAR(64), PRIMARY KEY (id));";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();

        // Insert the mazeData
        for (int i = 0; i < maze.GetMazeData().length; i++){
            sql = "INSERT INTO " + maze.GetName() + " (mazeData) VALUES (" +
                    maze.CellArrayToStringArray(maze.GetMazeData())[i] + ");";
            statement = connection.prepareStatement(sql);
            statement.execute();
        }

        // Insert essential information
        sql = "UPDATE " + maze.GetName() + " SET mazeType = '" + mazeType + "', xDimension = " + maze.GetXDimension() +
                ", mazeAuthor = '" + maze.GetAuthor() + "', yDimension = " + maze.GetYDimension() + ", creationTime = '" +
                lastEditDate + "', lastEditTime = '" + lastEditDate + "' WHERE id = " + 1 + ";";
        statement = connection.prepareStatement(sql);
        statement.execute();

        // Insert image information
        sql = "UPDATE " + maze.GetName() + " SET startImagePath = '" + maze.GetStartImagePath() + "', endImagePath = '" +
                maze.GetEndImagePath() + "', logoImagePath = '" + maze.GetLogoImagePath() + "', logoImageIndex = " +
                maze.GetLogoIndex() + ", logoImageHeight = " + maze.GetLogoHeight() + ", logoImageWidth = " + maze.GetLogoWidth() +
                " WHERE id = 1;";
        System.out.println(sql);
        statement = connection.prepareStatement(sql);
        statement.execute();

    }

    //
    public Maze[] GetMazes() throws SQLException, IOException {

        //
        String[] mazeNames = this.GetTableNames();
        Maze[] mazes = new Maze[mazeNames.length];

        // get each maze
        for (int i = 0; i < mazeNames.length; i++) {
            String currentType = GetString(mazeNames[i],"mazeType");
            // add it to the array depending on the adult or child
            if (Objects.equals(currentType, "Adult")) {
                Maze currentMaze = new MazeAdult();
                currentMaze.GenerateSavedMaze(mazeNames[i]);
                mazes[i] = currentMaze;
            } else if (Objects.equals(currentType, "Child")) {
                Maze currentMaze = new MazeChild();
                currentMaze.GenerateSavedMaze(mazeNames[i]);
                mazes[i] = currentMaze;
            }
        }
        return mazes;
    }

    //
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

    //
    public void SetMazeData(String tableName, String[] mazeData) throws SQLException {

        List<String> tableNames = Arrays.asList(GetTableNames());

        if (tableNames.contains(tableName)){
            for (int i = 0; i < mazeData.length; i++){
                String sql = "UPDATE " + tableName + " SET mazeData = " + mazeData[i] + " WHERE id = " + (i+1) + ";";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
            }
        }

    }

    //
    public String[] GetMazeData(String tableName) throws SQLException {
        List<String> dataList = new ArrayList<String>();
        String sql = "SELECT mazeData FROM " + tableName + ";";
        PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            dataList.add(resultSet.getString(1));
        }
        String[] data = dataList.toArray(new String[dataList.size()]);
        return data;
    }

    //
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

    public String[] GetStringArray(String tableName, String[] columnNames){
        String[] h = new String[1];
        return h;
    }

    //
    public int GetInt(String tableName, String columnName) throws SQLException {
        int result = 0;
        String sql = "SELECT " + columnName + " FROM " + tableName + " WHERE id = 1";
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            result = resultSet.getInt(1);
        }
        return result;
    }


}

