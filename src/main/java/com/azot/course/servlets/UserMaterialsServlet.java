package com.azot.course.servlets;

import com.azot.course.DAO.DAOImpl.MaterialDAOImpl;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Material;
import com.azot.course.models.User;
import com.azot.course.util.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/myMaterials")
public class UserMaterialsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Connection connection = Database.getConnection();
            MaterialDAOImpl materialDAO = new MaterialDAOImpl(connection);
            List<Material> userMaterials = materialDAO.getMaterialsByUserId(user.getId());
            request.setAttribute("materials", userMaterials);

            request.getRequestDispatcher("/WEB-INF/views/userMaterials.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при получении материалов: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
