package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final String HOST = "localhost";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DATABASE_NAME = "travelapp";

    public static Connection getConnection() throws SQLException {
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", USERNAME);
        connectionProperties.put("password", PASSWORD);
        return DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE_NAME + "?useSSL=false", connectionProperties);
    }
}

