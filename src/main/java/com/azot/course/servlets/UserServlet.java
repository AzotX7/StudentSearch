package com.azot.course.servlets;

import com.azot.course.DTO.UserDTO;
import com.azot.course.user.Role;
import com.azot.course.models.User;
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
import java.util.stream.Collectors;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final UserService userService;

    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    public UserServlet() {
        this.userService = new UserService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (userDTO == null || userDTO.getRole() != Role.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/materials");
            return;
        }

        String action = request.getPathInfo();

        try {
            if (action == null || "/".equals(action)) {
                List<UserDTO> users = userService.getAllUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
            } else if ("/addUser".equals(action)) {
                request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
            } else if ("/editUser".equals(action)) {
                String idParam = request.getParameter("id");
                if (idParam != null && !idParam.isEmpty()) {
                    int userId = Integer.parseInt(idParam);
                    UserDTO editUser = userService.getUserById(userId);

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
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (userDTO == null || userDTO.getRole() != Role.ADMIN) {
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
        } else if ("/editUser".equals(action)) {
            String idParam = request.getParameter("id");

            if (idParam != null && !idParam.isEmpty()) {
                int userId = Integer.parseInt(idParam);
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String roleParam = request.getParameter("role");
                Role role = Role.valueOf(roleParam);

                UserDTO editUser = new UserDTO(userId, username, email, role);

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
        } else if (action.matches("/\\d+")) {
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