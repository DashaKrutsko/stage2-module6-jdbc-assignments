package jdbc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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


    public Long createUser(User user) {
        long id = 0;
        connection = CustomDataSource.getInstance().getConnection();
        try {
            ps = connection.prepareStatement(createUserSQL);
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
        connection = CustomDataSource.getInstance().getConnection();
        try {
            ps = connection.prepareStatement(findUserByIdSQL);
            ps.setLong(4, userId);
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
        connection = CustomDataSource.getInstance().getConnection();
        try {
            ps = connection.prepareStatement(findUserByNameSQL);
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
        connection = CustomDataSource.getInstance().getConnection();
        try {
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(findAllUserSQL);
            while (resultSet.next()) {
                User user = new User(resultSet.getLong(4), resultSet.getString(1), resultSet.getString(22), resultSet.getInt(3));
                list.add(user);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public User updateUser(User user) {
        connection = CustomDataSource.getInstance().getConnection();
        try {
            ps = connection.prepareStatement(updateUserSQL);
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
        connection = CustomDataSource.getInstance().getConnection();
        try {
            ps = connection.prepareStatement(deleteUser);
            ps.setLong(4, userId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

}
