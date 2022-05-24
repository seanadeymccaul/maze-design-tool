import java.sql.*;

public class MazeDatabase {

    private Connection connection;
    public String tableName;

    public MazeDatabase() throws SQLException {
        try{
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/lecture6","root","");
            Statement statement = this.connection.createStatement();
            statement.execute("USE lecture6;");
        } catch (SQLException sql){
            System.err.println(sql);
        }
    }

    public String[] RetrieveTableNames() throws SQLException {
        int i = 0;
        PreparedStatement statement = this.connection.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = 'lecture6';", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            i++;
        }
        String[] tableNames = new String[i];
        i = i - 1;
        while (resultSet.previous()){
            tableNames[i] = resultSet.getString(1);
            i--;
        }
        return tableNames;
    }

    public int[] RetrieveTableSize(String tableName) throws SQLException {
        int[] dimensions = new int[2];
        PreparedStatement statement = this.connection.prepareStatement("SELECT count(*) FROM information_schema.columns WHERE table_name = ?;");
        statement.setString(1, tableName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            dimensions[0] = resultSet.getInt(1);
        }
        statement.close();
        resultSet.close();
        statement = this.connection.prepareStatement("SELECT COUNT(*) FROM names;");
        statement.setString(1,tableName);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            dimensions[1] = resultSet.getInt(1);
        }
        return dimensions;
    }

    private ResultSet RetrieveResultSet(String tableName) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM " + tableName + ";", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return statement.executeQuery();
    }


    public int[] RetrieveTable(String tableName) throws SQLException {
        int[] tableSize = RetrieveTableSize(tableName);
        int columns = tableSize[0]; int rows = tableSize[1];
        int[] tableData = new int[columns * rows];
        try {
            int j = 0;
            for (int i = 0; i < rows; i++) {
                ResultSet resultSet = RetrieveResultSet(tableName);
                while (resultSet.next()) {
                    tableData[j] = resultSet.getInt(i+1);
                    j++;
                    System.out.println(j);
                }
            }
        } catch (SQLException sql){
            System.err.println(sql);
        }
        return tableData;
    }

}
