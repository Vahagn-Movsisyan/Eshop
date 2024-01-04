package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.User;
import org.example.model.enums.Role;

import java.sql.*;

public class UserManager {
    private final Connection connection = DBConnectionProvider.getInstance().getConnection();
    public void addUser(User user) {
        String sql = "INSERT INTO user(user_name, user_email, user_password, user_role) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, Role.USER.toString());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userEmail = resultSet.getString("user_email");
                String userPassword = resultSet.getString("user_password");
                Role role = Role.valueOf(resultSet.getString("user_role"));
                User.builder()
                        .id(userId)
                        .name(userName)
                        .email(userEmail)
                        .password(userPassword)
                        .role(role)
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE user_email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userEmail = resultSet.getString("user_email");
                String userPassword = resultSet.getString("user_password");
                Role role = Role.valueOf(resultSet.getString("user_role"));
                return User.builder()
                        .id(userId)
                        .name(userName)
                        .email(userEmail)
                        .password(userPassword)
                        .role(role)
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
