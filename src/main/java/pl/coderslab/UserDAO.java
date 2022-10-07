package pl.coderslab;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAO {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(email, username, password) VALUES(?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * from users where id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? where id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users where id = ?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * from users";


    public String hashPassword(String password)
    {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    private User[] addToArray(User user, User[] users)
    {
        User[] temp = Arrays.copyOf(users, users.length + 1);
        temp[users.length] = user;
        return temp;
    }

    public User Create(User user)
    {
        try(Connection conn = DbUtil.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                user.setId(resultSet.getInt(1));

            return user;

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public User Read(int userID)
    {
        try(Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1,userID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void Update(User user)
    {
        try(Connection conn = DbUtil.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public User[] finalAll()
    {
        try(Connection conn = DbUtil.getConnection())
        {
            User[] users = new User[0];

            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                users = addToArray(user, users);
            }
            return users;

        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
