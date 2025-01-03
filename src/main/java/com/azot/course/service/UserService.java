package com.azot.course.service;

import com.azot.course.DAO.DAOImpl.UserDAOImpl;
import com.azot.course.DAO.UserDAO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.user.Role;
import com.azot.course.models.User;
import com.azot.course.util.PasswordUtil;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserDAO userDAO;

    public UserService(Connection connection) {
        this.userDAO = new UserDAOImpl(connection);
    }

    public void registerUser(String username, String email, String password, Role role) throws SQLException {

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

    public List<UserDTO> getAllUsers() throws SQLException {
        List<User> users = userDAO.getAllUsers();
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public void updateUser(UserDTO userDTO) throws SQLException {
        User user = userDAO.getUserById(userDTO.getId());
        if (user == null) {
            throw new SQLException("User not found with id " + userDTO.getId());
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        userDAO.updateUser(user);
    }



    public void deleteUser(int id) throws SQLException {
        userDAO.deleteUser(id);
    }



    public UserDTO getUserById(int id) throws SQLException {
        User user = userDAO.getUserById(id);
        if (user == null) {
            throw new SQLException("User not found with id " + id);
        }
        return toDTO(user);
    }

    public boolean authenticateUser(String inputPassword, User user) {
        if (user == null || user.getSalt() == null) {
            throw new IllegalArgumentException("User or salt is null");
        }
        byte[] salt = Base64.getDecoder().decode(user.getSalt());
        return PasswordUtil.verifyPassword(inputPassword, user.getPassword(), salt);
    }


    public User getFullUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }

    public List<UserDTO> searchUsers(String query) throws SQLException{
        return userDAO.searchUsers(query).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public boolean isEmailExists(String email) throws SQLException {
        return userDAO.userExistsByEmail(email);
    }

    public boolean isUsernameExists(String username) throws SQLException {
        return userDAO.userExistsByUsername(username);
    }
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}