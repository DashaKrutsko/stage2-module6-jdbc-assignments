package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class CustomConnector {
    static String dbUrl;
    protected static String dbUser;
    protected static String dbPass;


    public static void readProperty() {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/app.properties");
            property.load(fis);
            dbUrl = property.getProperty("postgres.url");
            dbUser = property.getProperty("postgres.name");
            dbPass = property.getProperty("postgres.password");
        } catch (IOException e) {
            System.err.println("No file");
        }
    }

    public Connection getConnection(String jdbUrl) {
        return  null;
    }

    public Connection getConnection()  {
        readProperty();
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbUrl ,dbUser,dbPass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}