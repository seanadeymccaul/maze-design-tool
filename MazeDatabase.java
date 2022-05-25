import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MazeDatabase {

    private Connection connection;
    public static String[] tableNames;

    public MazeDatabase() throws SQLException {
        this.GetConnection();
        tableNames = this.GetTableNames();
    }

    // COMPLETED and TESTED
    private void GetConnection(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/MazeDatabase","root","");
            Statement statement = this.connection.createStatement();
            statement.execute("USE MazeDatabase;");
        } catch (SQLException sql){
            System.err.println(sql);
        }
    }

    // COMPLETED and TESTED
    public void CreateTable(String tableName, String[] mazeData, int mazeType, int xDimension, int yDimension) throws SQLException {
        // Create the table
        String sql = "CREATE TABLE " + tableName + " (id int NOT NULL AUTO_INCREMENT, mazeData VARCHAR(5), mazeType INT, xDimension INT, yDimension INT, PRIMARY KEY (id));";
        System.out.println(sql);
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        // Insert the mazeData
        for (int i = 0; i < mazeData.length; i++){
            sql = "INSERT INTO " + tableName + " (mazeData) VALUES (" + mazeData[i] + ");";
            statement = connection.prepareStatement(sql);
            statement.execute();
        }
        // Insert the dimensions and type
        sql = "UPDATE " + tableName + " SET mazeType = " + mazeType + ", xDimension = " + xDimension + ", yDimension = " + yDimension + " WHERE id = " + 1 + ";";
        System.out.println(sql);
        statement = connection.prepareStatement(sql);
        statement.execute();
    }

    // COMPLETED and TESTED
    public String[] GetTableNames() throws SQLException {
        List<String> namesList = new ArrayList<String>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = 'MazeDatabase';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            namesList.add(resultSet.getString(1));
        }
        String[] names = namesList.toArray(new String[namesList.size()]);
        return names;
    }

    // COMPLETED and TESTED
    public void SetMazeData(String tableName, String[] mazeData) throws SQLException {

        List<String> tableNames = Arrays.asList(GetTableNames());

        if (!tableNames.contains(tableName)){
            System.out.println("Name already exists");
        }
        else {
            for (int i = 0; i < mazeData.length; i++){
                String sql = "UPDATE " + tableName + " SET mazeData = " + mazeData[i] + " WHERE id = " + i + ";";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
            }
        }
    }

    // COMPLETED
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

    public int GetMazeType(String tableName) throws SQLException {
        int result = 2;
        String sql = "SELECT mazeType FROM " + tableName + " WHERE id = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            result = resultSet.getInt(1);
        }
        return result;
    }

    // COMPLETED and TESTED
    public Integer[] GetMazeDimensions(String tableName) throws SQLException {
        List<Integer> dimensionList = new ArrayList<Integer>();
        String sql = "SELECT xDimension,yDimension FROM " + tableName + ";";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            dimensionList.add(resultSet.getInt(1));
            dimensionList.add(resultSet.getInt(2));
        }
        Integer[] dimensions = dimensionList.toArray(new Integer[dimensionList.size()]);
        return dimensions;
    }
}
