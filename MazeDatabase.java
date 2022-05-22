import java.sql.*;

public class MazeDatabase {

    private Connection connection;

    public MazeDatabase() throws SQLException {
        try{
            this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/lecture6","root","");
            Statement statement = this.connection.createStatement();
            statement.execute("USE lecture6;");
        } catch (SQLException sql){
            System.err.println(sql);
        }
    }

    private ResultSet RetrieveResultSet(String tableName) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM " + tableName + ";", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return statement.executeQuery();
    }

    public int[] RetrieveTable(String tableName, int numColumns, int numRows) throws SQLException {
        int[] tableData = new int[numColumns * numRows];
        try {
            int j = 0;
            for (int i = 0; i < numColumns; i++) {
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
