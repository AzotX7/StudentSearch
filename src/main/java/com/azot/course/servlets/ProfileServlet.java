package com.azot.course.servlets;

import com.azot.course.DAO.DAOImpl.MaterialDAOImpl;
import com.azot.course.DAO.DAOImpl.UserDAOImpl;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Material;
import com.azot.course.models.User;
import com.azot.course.service.UserService;
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

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

        if (userDTO == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            if (action == null || "/".equals(action)) {
                User user = new UserService(Database.getConnection()).getFullUserByUsername(userDTO.getUsername());

                MaterialDAOImpl materialDAO = new MaterialDAOImpl(Database.getConnection());
                request.setAttribute("user", userDTO);
                List<Material> userMaterials = materialDAO.getMaterialsByUserId(user.getId());
                request.setAttribute("materials", userMaterials);

                request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);

            } else if ("/edit".equals(action)) {
                request.setAttribute("user", userDTO);
                request.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(request, response);

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/update".equals(action)) {
            UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

            if (userDTO != null) {
                String username = request.getParameter("username");
                String email = request.getParameter("email");

                if (username != null && !username.trim().isEmpty() && email != null && !email.trim().isEmpty()) {
                    try {
                        Connection connection = Database.getConnection();
                        UserDAOImpl userDAO = new UserDAOImpl(connection);

                        if (!userDTO.getUsername().equals(username) && userDAO.userExistsByUsername(username)) {
                            request.setAttribute("errorMessage", "Имя пользователя уже существует.");
                            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                            return;
                        }
                        if (!userDTO.getEmail().equals(email) && userDAO.userExistsByEmail(email)) {
                            request.setAttribute("errorMessage", "Email уже существует.");
                            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                            return;
                        }

                        User updatedUser = userDAO.getUserById(userDTO.getId());
                        updatedUser.setUsername(username);
                        updatedUser.setEmail(email);
                        userDAO.updateUser(updatedUser);

                        userDTO.setUsername(username);
                        userDTO.setEmail(email);
                        request.getSession().setAttribute("user", userDTO);

                        response.sendRedirect(request.getContextPath() + "/profile");
                    } catch (SQLException e) {
                        request.setAttribute("errorMessage", "Ошибка при обновлении профиля: " + e.getMessage());
                        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Имя пользователя и email не могут быть пустыми.");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }
}
