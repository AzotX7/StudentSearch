package com.azot.course.servlets;

import com.azot.course.DTO.UserDTO;
import com.azot.course.service.CategoryService;
import com.azot.course.service.serviceImpl.CategoryServiceImpl;
import com.azot.course.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/categories/*")
public class CategoryServlet extends HttpServlet {

    private static final String ADD_PATH = "/add";

    private static final String BASE_PATH = "/categories";
    private final CategoryService categoryService;


    public CategoryServlet() throws SQLException {
        this.categoryService = new CategoryServiceImpl(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/materials");
            return;
        }

        String pathInfo = request.getPathInfo();
        try {
            switch (pathInfo == null ? "/" : pathInfo) {
                case "/":
                    request.setAttribute("categories", categoryService.getAllCategories());
                    request.getRequestDispatcher("/WEB-INF/views/categories.jsp").forward(request, response);
                    break;
                case ADD_PATH:
                    request.getRequestDispatcher("/WEB-INF/views/addCategory.jsp").forward(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        try {
            if ((path == null ? "/" : path).equals(ADD_PATH)) {
                handleAddCategory(request, response);
            } else {
                if (path.matches("/\\d+")) {
                    handleDeleteCategory(request, response, path);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole().toString());
    }

    private void handleAddCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String categoryName = request.getParameter("categoryName");

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            categoryService.addCategory(categoryName);
            response.sendRedirect(request.getContextPath() + BASE_PATH + "?success=true");
        } else {
            request.setAttribute("error", "Название категории не может быть пустым!");
            request.getRequestDispatcher("/WEB-INF/views/addCategory.jsp").forward(request, response);
        }
    }

    private void handleDeleteCategory(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, SQLException {
        int categoryId = Integer.parseInt(path.substring(1));
        categoryService.deleteCategory(categoryId);
        response.sendRedirect(request.getContextPath() + BASE_PATH);
    }
}