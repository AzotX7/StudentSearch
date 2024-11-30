package com.azot.course.controller;

import com.azot.course.data.Role;

import com.azot.course.service.UserService;
import com.azot.course.util.Database;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/register")
public class AuthController extends HttpServlet {
    private final UserService userService;

    public AuthController() throws SQLException {
        this.userService = new UserService(Database.getConnection());
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Role role = Role.USER;

        try {

            userService.registerUser(username, email, password, role);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
