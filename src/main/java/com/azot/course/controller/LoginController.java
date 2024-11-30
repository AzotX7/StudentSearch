package com.azot.course.controller;

import com.azot.course.data.Role;
import com.azot.course.entity.User;
import com.azot.course.service.UserService;
import com.azot.course.util.Database;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final UserService userService;

    public LoginController() throws SQLException {
        this.userService = new UserService(Database.getConnection());
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");


            User user = userService.getUserByUsername(username);

            if (user != null && userService.authenticateUser(password, user)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);


                if (user.getRole() == Role.ADMIN) {
                    response.sendRedirect(request.getContextPath() + "/users");
                } else {
                    response.sendRedirect(request.getContextPath() + "/materials");
                }
            } else {
                request.setAttribute("errorMessage", "Неверное имя пользователя или пароль");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Произошла ошибка: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
