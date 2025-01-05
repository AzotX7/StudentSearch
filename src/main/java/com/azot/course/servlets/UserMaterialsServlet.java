package com.azot.course.servlets;


import com.azot.course.DTO.MaterialDTO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.service.MaterialService;
import com.azot.course.service.serviceImpl.MaterialServiceImpl;
import com.azot.course.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/myMaterials")
public class UserMaterialsServlet extends HttpServlet {

    private final MaterialService materialService;

    public UserMaterialsServlet() throws SQLException {
        this.materialService = new MaterialServiceImpl(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<MaterialDTO> userMaterials = materialService.getMaterialsByUserId(user.getId());
            request.setAttribute("materials", userMaterials);

            request.getRequestDispatcher("/WEB-INF/views/userMaterials.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при получении материалов: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
