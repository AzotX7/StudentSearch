package com.azot.course.DAO;

import com.azot.course.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    void addUser(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(int id) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    User getUserByUsername(String username) throws SQLException;

    List<User> searchUsers(String query) throws SQLException;

    boolean userExistsByUsername(String username) throws SQLException;

    boolean userExistsByEmail(String email) throws SQLException;
}
