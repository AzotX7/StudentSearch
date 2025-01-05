package com.azot.course.servlets;


import com.azot.course.DTO.MaterialDTO;
import com.azot.course.models.Category;
import com.azot.course.service.CategoryService;
import com.azot.course.service.MaterialService;
import com.azot.course.service.serviceImpl.CategoryServiceImpl;
import com.azot.course.service.serviceImpl.MaterialServiceImpl;
import com.azot.course.database.Database;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/materials/search")
public class SearchMaterialsServlet extends HttpServlet {

    private final CategoryService categoryService;
    private final MaterialService materialService;

    public SearchMaterialsServlet() throws SQLException {
        this.categoryService = new CategoryServiceImpl(Database.getConnection());
        this.materialService = new MaterialServiceImpl(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

        String query = request.getParameter("query");
        String[] categoryIdsParam = request.getParameterValues("categories");

        List<Integer> categoryIds = parseCategoryIds(categoryIdsParam);

        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);

        List<MaterialDTO> materials = fetchMaterials(query, categoryIds);
        request.setAttribute("materials", materials);

        request.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(request, response);

        } catch (Exception e){
            request.setAttribute("errorMessage", "Ошибка при выполнении поиска: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private List<Integer> parseCategoryIds(String[] categoryIdsParam) {
        List<Integer> categoryIds = new ArrayList<>();
        if (categoryIdsParam != null) {
            for (String categoryId : categoryIdsParam) {
                try {
                    categoryIds.add(Integer.parseInt(categoryId));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return categoryIds;
    }

    private List<MaterialDTO> fetchMaterials(String query, List<Integer> categoryIds) throws SQLException {
        if ((query == null || query.isEmpty()) && categoryIds.isEmpty()) {
            return materialService.getAllMaterials();
        } else if (categoryIds.isEmpty()) {
            return materialService.searchMaterialsByTitle(query);
        } else {
            return materialService.searchMaterials(query, categoryIds);
        }
    }
}