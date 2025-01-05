package com.azot.course.servlets;

import com.azot.course.DTO.UserDTO;
import com.azot.course.models.User;
import com.azot.course.service.UserService;
import com.azot.course.service.serviceImpl.UserServiceImpl;
import com.azot.course.database.Database;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    public LoginServlet() throws SQLException {
        this.userService = new UserServiceImpl(Database.getConnection());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");


            User user = userService.getFullUserByUsername(username);

            if (user != null && userService.authenticateUser(password, user)) {
                HttpSession session = request.getSession();
                UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
                session.setAttribute("user", userDTO);

                String redirectUrl = user.getRole().toString().equals("ADMIN") ? "/users" : "/materials";
                response.sendRedirect(request.getContextPath() + redirectUrl);

            } else {
                request.setAttribute("errorMessage", "Неверное имя пользователя или пароль");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            log("Неизвестная ошибка", e);
            request.setAttribute("errorMessage", "Произошла ошибка: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

}
