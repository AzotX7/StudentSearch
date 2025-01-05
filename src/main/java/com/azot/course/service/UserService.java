package com.azot.course.service;

import com.azot.course.DTO.UserDTO;
import com.azot.course.models.User;
import com.azot.course.user.Role;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void registerUser(String username, String email, String password, Role role) throws SQLException;

    List<UserDTO> getAllUsers() throws SQLException;

    void updateUser(UserDTO userDTO) throws SQLException;

    void deleteUser(int id) throws SQLException;

    UserDTO getUserById(int id) throws SQLException;

    boolean authenticateUser(String inputPassword, User user);

    User getFullUserByUsername(String username) throws SQLException;

    List<UserDTO> searchUsers(String query) throws SQLException;

    boolean isEmailExists(String email) throws SQLException;

    boolean isUsernameExists(String username) throws SQLException;
}
