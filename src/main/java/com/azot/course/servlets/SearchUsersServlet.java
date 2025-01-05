package com.azot.course.servlets;

import com.azot.course.DTO.UserDTO;
import com.azot.course.service.UserService;
import com.azot.course.service.serviceImpl.UserServiceImpl;
import com.azot.course.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users/search")
public class SearchUsersServlet extends HttpServlet {

    private final UserService userService;

    public SearchUsersServlet() throws SQLException{
        this.userService = new UserServiceImpl(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        try {
            List<UserDTO> userDTOs = userService.searchUsers(query);

            request.setAttribute("users",userDTOs);

            if (userDTOs.isEmpty()) {
                request.setAttribute("noResultsMessage", "Пользователи не найдены.");
            }

            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Ошибка при выполнении поиска" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        }
}

