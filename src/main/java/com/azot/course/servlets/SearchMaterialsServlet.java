package com.azot.course.servlets;

import com.azot.course.DAO.MaterialDAO;
import com.azot.course.entity.Material;
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

@WebServlet("/materials/search")
public class SearchMaterialsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Material> materials;

        try (Connection connection = Database.getConnection()) {
            MaterialDAO materialDAO = new MaterialDAO(connection);
            materials = materialDAO.searchMaterialsByTitle(query);
        } catch (SQLException e) {

            request.setAttribute("errorMessage", "Ошибка при выполнении поиска: " + e.getMessage());

            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("materials", materials);
        request.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(request, response);
    }

}
