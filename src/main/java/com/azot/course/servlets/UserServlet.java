package com.azot.course.servlets;

import com.azot.course.DTO.UserDTO;
import com.azot.course.service.UserService;
import com.azot.course.user.Role;
import com.azot.course.service.serviceImpl.UserServiceImpl;
import com.azot.course.database.Database;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


import java.sql.SQLException;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final UserService userService;

    private static final String PATH_USERS = "/users";
    private static final String PATH_ROOT = "/";
    private static final String PATH_ADD_USER = "/addUser";
    private static final String PATH_EDIT_USER = "/editUser";
    private static final String PATH_DELETE_USER_REGEX = "/\\d+";



    public UserServlet() throws SQLException {
        this.userService = new UserServiceImpl(Database.getConnection());
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
            switch (action == null ? PATH_ROOT : action) {
                case PATH_ROOT:
                    List<UserDTO> users = userService.getAllUsers();
                    request.setAttribute("users", users);
                    request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
                    break;

                case PATH_ADD_USER:
                    request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
                    break;

                case PATH_EDIT_USER:
                    handleEditUserForm(request, response);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (userDTO == null || userDTO.getRole() != Role.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/materials");
            return;
        }

        String action = request.getPathInfo();

        try {
            if (PATH_ADD_USER.equals(action)) {
                handleAddUser(request, response);
            } else if (PATH_EDIT_USER.equals(action)) {
                handleEditUser(request, response);
            } else if (action != null && action.matches(PATH_DELETE_USER_REGEX)) {
                handleDeleteUser(request, response, action);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Неизвестная команда");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }


    }

    private void handleEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            int userId = Integer.parseInt(idParam);
            UserDTO user = userService.getUserById(userId);

            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Пользователь не найден");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
        }
    }

    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleParam = request.getParameter("role");

        try {
            Role role = Role.valueOf(roleParam);
            userService.registerUser(username, email, password, role);
            response.sendRedirect(request.getContextPath() + PATH_USERS);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    private void handleEditUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            int userId = Integer.parseInt(idParam);
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String roleParam = request.getParameter("role");
            Role role = Role.valueOf(roleParam);

            UserDTO user = new UserDTO(userId, username, email, role);

            try {
                userService.updateUser(user);
                response.sendRedirect(request.getContextPath() + PATH_USERS);
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
        }
    }

    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        int userId = Integer.parseInt(action.substring(1));

        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("success");
        } else {
            response.sendRedirect(request.getContextPath() + PATH_USERS);
        }

    }
}