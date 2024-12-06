package com.azot.course.servlets;

import com.azot.course.DAO.DAOImpl.UserDAOImpl;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.User;
import com.azot.course.util.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/search")
public class SearchUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<UserDTO> userDTOs = new ArrayList<>();


        try (Connection connection = Database.getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            List<User> users = userDAO.searchUsers(query);

            for (User user : users) {
                UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
                userDTOs.add(userDTO);
            }

        } catch (SQLException e) {

            request.setAttribute("errorMessage", "Ошибка при выполнении поиска: " + e.getMessage());

            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("users", userDTOs);

        if (userDTOs.isEmpty()) {
            request.setAttribute("noResultsMessage", "Пользователи не найдены.");
        }

        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }
}
