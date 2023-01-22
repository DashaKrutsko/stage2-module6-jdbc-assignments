package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.postgresql.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class SimpleJDBCRepository {

    private static Connection connection = null;
    private static PreparedStatement ps = null;
    private static Statement st = null;

    private static final String createUserSQL = "INSERT INTO MYUSERS (FIRSTNAME, LASTNAME, AGE) VALUES (?, ?, ?)";
    private static final String updateUserSQL = "UPDATE MYUSERS SET FIRSTNAME=?, LASTNAME=?, AGE=? WHERE ID=?";
    private static final String deleteUser = "DELETE FROM MYUSERS WHERE ID=?";
    private static final String findUserByIdSQL = "SELECT * FROM MYUSERS WHERE ID=?";
    private static final String findUserByNameSQL = "SELECT * FROM MYUSERS WHERE FIRSTNAME=?";
    private static final String findAllUserSQL = "SELECT * FROM MYUSERS";

    public Long createUser() throws SQLException {
        User user = new User();
        ps = connection.prepareStatement(createUserSQL);
        ps.setString(1, "Olga");
        ps.setString(2, "Ivanova");
        ps.setInt(3, 50);
        ps.execute();
        user.setAge(50);
        user.setFirstName("Olga");
        user.setLastName("Ivanova");
        return null;
    }

    public User findUserById(Long userId) throws SQLException {
        ps = connection.prepareStatement(findUserByIdSQL);
        ps.setLong(1, 1);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }

    public User findUserByName(String userName) throws SQLException {
        ps = connection.prepareStatement(findUserByNameSQL);
        ps.setString(1, "dasha");
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }

    public List<User> findAllUser() throws SQLException {
        List<User> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(findAllUserSQL);
        while (resultSet.next()) {
            User user = new User(resultSet.getLong(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
            list.add(user);
        }
        return list;

    }

    public User updateUser() throws SQLException {
        ps = connection.prepareStatement(updateUserSQL);
        ps.setString(1, "Olga2");
        ps.setString(2, "Ivanova2");
        ps.setInt(3, 502);
        ps.setLong(4, 1);
        ps.executeUpdate();
        User user = new User();
        user.setId(1L);
        user.setFirstName("Olga2");
        user.setLastName("Ivanova2");
        user.setAge(502);
        return user;
    }

    private void deleteUser(Long userId) throws SQLException {
        ps = connection.prepareStatement(deleteUser);
        ps.setLong(1, 1);
        ps.executeUpdate();
    }
}
