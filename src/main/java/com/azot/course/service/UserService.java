package com.azot.course.service;

import com.azot.course.DAO.UserDAO;
import com.azot.course.data.Role;
import com.azot.course.entity.User;
import com.azot.course.util.PasswordUtil;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
public class UserService {

    private final UserDAO userDAO;

    public UserService(Connection connection) {
        this.userDAO = new UserDAO(connection);
    }

    @SneakyThrows
    public void registerUser(String username, String email, String password, Role role){
        User user = new User();

        byte[] salt = PasswordUtil.generateSalt();
        user.setSalt(Base64.getEncoder().encodeToString(salt));

        String hashedPassword = PasswordUtil.hashPassword(password, salt);

        user.setPassword(hashedPassword);
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);


        userDAO.addUser(user);
    }
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();

    }

    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }


    public void deleteUser(int id) throws SQLException {
        userDAO.deleteUser(id);
    }


    public User getUserById(int id) throws SQLException {
        return userDAO.getUserById(id);
    }

    public boolean authenticateUser(String inputPassword, User user) {
        if (user == null || user.getSalt() == null) {
            throw new IllegalArgumentException("User or salt is null");
        }
        byte[] salt = Base64.getDecoder().decode(user.getSalt());
        return PasswordUtil.verifyPassword(inputPassword, user.getPassword(), salt);
    }

    public User getUserByUsername(String username) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new SQLException("User not found with username: " + username);
        }
        return user;
    }
}
