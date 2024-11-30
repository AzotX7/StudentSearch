package com.azot.course.DAO;

import com.azot.course.data.Role;
import com.azot.course.entity.Material;
import com.azot.course.entity.User;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (username, email, password, role, salt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().name());
            stmt.setString(5, user.getSalt());
            stmt.executeUpdate();
        }
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToUser(rs);
            }
        }
        return null;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET username = ?, email = ?, role = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole().name());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate(); // Получаем количество затронутых строк
            if (rowsAffected == 0) {
                throw new SQLException("User with ID " + id + " does not exist.");
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }

        }
        return users;
    }
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToUser(rs);
            }
        }
        return null;
    }

    public List<User> searchUsers(String query) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE username LIKE ? OR email LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + query + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }

        return users;
    }


    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.valueOf(resultSet.getString("role")));
        user.setRegistrationDate(resultSet.getTimestamp("registration_date"));
        user.setSalt(resultSet.getString("salt"));
        return user;
    }


}
