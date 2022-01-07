package Models;

import java.sql.*;

public class DatabaseManager {

    static private DatabaseManager instance;
    private final String url = "jdbc:mysql://localhost:3306/OrderOnlineProcessing";
    private final String userName;
    private final String password;
    private Connection connection;
    private DatabaseManager(String username, String pass) {
        this.userName = username;
        this.password = pass;
        setConnection();
    }


    public static synchronized DatabaseManager getInstance(String userName, String password) {
        if(instance == null) {
            instance = new DatabaseManager(userName, password);
        }
        return instance;
    }

    private void setConnection () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection () {
        return connection;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);
        return statement.getResultSet();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}