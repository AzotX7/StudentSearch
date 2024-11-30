package com.azot.course.controller;

import com.azot.course.data.Role;
import com.azot.course.entity.User;
import com.azot.course.service.UserService;
import com.azot.course.util.Database;
import lombok.SneakyThrows;


import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import java.util.List;

@WebServlet("/users/*")
public class UserController extends HttpServlet {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    public UserController() {
        this.userService = new UserService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");


        if (user == null || user.getRole() != Role.ADMIN) {

            response.sendRedirect(request.getContextPath() + "/materials");
            return;
        }


        String action = request.getPathInfo();

        try {
            if (action == null || "/".equals(action)) {

                List<User> users = userService.getAllUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
            } else if ("/addUser".equals(action)) {

                request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
            } else if ("/editUser".equals(action)) {

                String idParam = request.getParameter("id");
                if (idParam != null && !idParam.isEmpty()) {
                    int userId = Integer.parseInt(idParam);
                    User editUser = userService.getUserById(userId);

                    if (editUser != null) {
                        request.setAttribute("user", editUser);
                        request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");


        if (user == null || user.getRole() != Role.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/materials");
            return;
        }

        String action = request.getPathInfo();

        if ("/addUser".equals(action)) {

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Role role = Role.valueOf(request.getParameter("role"));

            try {

                userService.registerUser(username, email, password, role);
                response.sendRedirect(request.getContextPath() + "/users");
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } else if ("/editUser".equals(action) && user.getRole() == Role.ADMIN) {
            String idParam = request.getParameter("id");

            if (idParam != null && !idParam.isEmpty()) {
                int userId = Integer.parseInt(idParam);
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String roleParam = request.getParameter("role");
                Role role = Role.valueOf(roleParam);
                String password = request.getParameter("password");

                User editUser = userService.getUserById(userId);
                editUser.setId(userId);
                editUser.setUsername(username);
                editUser.setEmail(email);
                editUser.setRole(role);
                editUser.setPassword(password);

                try {
                    userService.updateUser(editUser);
                    response.sendRedirect(request.getContextPath() + "/users");
                } catch (Exception e) {
                    request.setAttribute("errorMessage", e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
            }
        }
        else if (action.matches("/\\d+") && user.getRole() == Role.ADMIN) {
            int userId = Integer.parseInt(action.substring(1));

            try {
                userService.deleteUser(userId);
                response.sendRedirect(request.getContextPath() + "/users");
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
        }
    }
}


