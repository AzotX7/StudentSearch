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

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            if (action == null || "/".equals(action)) {
                Connection connection = Database.getConnection();
                MaterialDAO materialDAO = new MaterialDAO(connection);

                request.setAttribute("user", user);
                List<Material> userMaterials = materialDAO.getMaterialsByUserId(user.getId());
                request.setAttribute("materials", userMaterials);
                request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);

            } else if ("/edit".equals(action)) {

                request.setAttribute("user", user);
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

            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {

                String username = request.getParameter("username");
                String email = request.getParameter("email");


                if (username != null && !username.trim().isEmpty() && email != null && !email.trim().isEmpty()) {

                    try {
                        Connection connection = Database.getConnection();
                        UserDAO userDAO = new UserDAO(connection);


                        user.setUsername(username);
                        user.setEmail(email);
                        userDAO.updateUser(user);
                        request.getSession().setAttribute("user", user);

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
