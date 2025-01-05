package com.azot.course.servlets;

import com.azot.course.service.UserService;
import com.azot.course.user.Role;

import com.azot.course.service.serviceImpl.UserServiceImpl;
import com.azot.course.database.Database;
import com.azot.course.util.Validator;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/register")
public class AuthServlet extends HttpServlet {
    private final UserService userService;

    public AuthServlet() throws SQLException {
        this.userService = new UserServiceImpl(Database.getConnection());
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Role role = Role.USER;

        try {
            if (!isInputValid(request, response)) {
                return;
            }
        } catch (SQLException e){
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }

        try {
            userService.registerUser(username, email, password, role);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            log("Ошибка при регистрации пользователя", e);
            request.setAttribute("errorMessage",  e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private boolean isInputValid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        if (!Validator.isValidUsername(username)) {
            request.setAttribute("errorMessage", "Имя пользователя не должно содержать пробелы.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return false;
        }

        if (!Validator.isValidEmail(email)) {
            request.setAttribute("errorMessage", "Введите корректный адрес электронной почты.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return false;
        }

        if (userService.isUsernameExists(username)) {
            request.setAttribute("errorMessage", "Имя пользователя уже существует.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return false;
        }

        if (!Validator.isValidPassword(password)) {
            request.setAttribute("errorMessage", "Пароль должен содержать минимум 8 символов, включая буквы в верхнем и нижнем регистре, цифру и специальный символ.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return false;
        }

        if (userService.isEmailExists(email)) {
            request.setAttribute("errorMessage", "Email уже существует.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return false;
        }

        return true;
    }

}
