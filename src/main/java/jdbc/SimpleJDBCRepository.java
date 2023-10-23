package jdbc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jdbc.CustomDataSource.*;

@Getter
@Setter
@NoArgsConstructor
public class SimpleJDBCRepository {
    private static Connection connection = null;
    private static PreparedStatement ps = null;
    private static Statement st = null;
    private static final String CREATE_USER = "INSERT INTO MYUSERS (FIRSTNAME, LASTNAME, AGE) VALUES (?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE MYUSERS SET FIRSTNAME=?, LASTNAME=?, AGE=? WHERE ID=?";
    private static final String DELETE_USER = "DELETE FROM MYUSERS WHERE ID=?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM MYUSERS WHERE ID=?";
    private static final String FIND_USER_BY_NAME = "SELECT * FROM MYUSERS WHERE FIRSTNAME=?";
    private static final String FIND_ALL_USER_SQL = "SELECT * FROM MYUSERS";


    public Long createUser(User user) {
        long id = 0;
        connection = getInstance().getConnection();
        try {
            ps = connection.prepareStatement(CREATE_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.execute();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet != null) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public User findUserById(Long userId) {
        connection = getInstance().getConnection();
        try {
            ps = connection.prepareStatement(FIND_USER_BY_ID);
            ps.setLong(1, userId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setLastName(resultSet.getString("lastname"));
            user.setAge(resultSet.getInt("age"));
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public User findUserByName(String userName) {
        connection = getInstance().getConnection();
        try {
            ps = connection.prepareStatement(FIND_USER_BY_NAME);
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setLastName(resultSet.getString("lastname"));
            user.setAge(resultSet.getInt("age"));
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public List<User> findAllUser() {
        List<User> list = new ArrayList<>();
        connection = getInstance().getConnection();
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(FIND_ALL_USER_SQL);
            while (resultSet.next()) {
                User user = new User(resultSet.getLong(4), resultSet.getString(1), resultSet.getString(22), resultSet.getInt(3));
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public User updateUser(User user) {
        connection = getInstance().getConnection();
        try {
            ps = connection.prepareStatement(UPDATE_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getAge());
            ps.setLong(4, user.getId());
            ps.executeUpdate();
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void deleteUser(Long userId) {
        connection = getInstance().getConnection();
        try {
            ps = connection.prepareStatement(DELETE_USER);
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

}
