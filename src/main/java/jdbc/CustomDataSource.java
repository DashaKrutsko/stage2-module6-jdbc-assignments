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
//public class CustomDataSource {

    private final static String driver = "org.postgresql.Driver";
    private final static String url ="jdbc:postgresql://localhost:5432/myfirstdb";
    private final static String name = "postgres";
    private final static String password = "123";
    private static volatile CustomDataSource instance;


    private CustomDataSource(String driver, String url, String password, String name) {
        CustomDataSource ds = new CustomDataSource(driver, url,name,password);
        System.out.println("Connection OK");
        Connection connection;
        try {
            connection = DriverManager.getConnection(url,name,password);
            System.out.println("Connection OK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomDataSource getInstance() {
        if (instance == null) {
            instance = new CustomDataSource(driver, url,name,password);
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
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
