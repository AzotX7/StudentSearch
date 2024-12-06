package com.azot.course.service;

import com.azot.course.DAO.DAOImpl.UserDAOImpl;
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

    private final UserDAOImpl userDAO;

    public UserService(Connection connection) {
        this.userDAO = new UserDAOImpl(connection);
    }

    public void registerUser(String username, String email, String password, Role role) throws SQLException {
        if (userDAO.userExistsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userDAO.userExistsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

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

        if (!user.getUsername().equals(userDTO.getUsername()) && userDAO.userExistsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (!user.getEmail().equals(userDTO.getEmail()) && userDAO.userExistsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
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

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    private User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }
}