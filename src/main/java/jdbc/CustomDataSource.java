package jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Getter
@Setter

public class CustomDataSource implements DataSource {
    private static volatile CustomDataSource instance;

    private final String driver = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost:5432/myfirstdb";
    private final String name = "postgres";
    private final String password = "password";
    Connection connection;

    public static void main(String[] args) {
        getInstance();
    }

    public CustomDataSource() {
        try {
            connection = DriverManager.getConnection(url ,name,password);
            System.out.println("ok");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomDataSource getInstance() {
        if (instance == null){
            instance = new CustomDataSource();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}