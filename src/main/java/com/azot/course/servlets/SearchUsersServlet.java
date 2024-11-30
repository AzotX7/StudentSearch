package com.azot.course.servlets;

import com.azot.course.DAO.MaterialDAO;
import com.azot.course.DAO.UserDAO;
import com.azot.course.entity.Material;
import com.azot.course.entity.User;
import com.azot.course.util.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users/search")
public class SearchUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<User> users;

        try (Connection connection = Database.getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            users = userDAO.searchUsers(query);
        } catch (SQLException e) {

            request.setAttribute("errorMessage", "Ошибка при выполнении поиска: " + e.getMessage());

            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("users", users);

        if (users.isEmpty()) {
            request.setAttribute("noResultsMessage", "Пользователи не найдены.");
        }

        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }
}
