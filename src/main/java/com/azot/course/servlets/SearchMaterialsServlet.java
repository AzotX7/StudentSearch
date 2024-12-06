package com.azot.course.servlets;


import com.azot.course.DTO.MaterialDTO;
import com.azot.course.models.Category;
import com.azot.course.service.CategoryService;
import com.azot.course.service.MaterialService;
import com.azot.course.util.Database;
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
        this.categoryService = new CategoryService(Database.getConnection());
        this.materialService = new MaterialService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String[] categoryIdsParam = request.getParameterValues("categories");

        List<Integer> categoryIds = new ArrayList<>();
        if (categoryIdsParam != null) {
            for (String categoryId : categoryIdsParam) {
                try {
                    categoryIds.add(Integer.parseInt(categoryId));
                } catch (NumberFormatException ignored) {
                }
            }
        }

        List<MaterialDTO> materials;
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute("categories", categories);

        if (query == null && categoryIds.isEmpty()) {
            materials = materialService.getAllMaterials();
        } else if (categoryIds.isEmpty()) {
            materials = materialService.searchMaterialsByTitle(query);
        } else {
            materials = materialService.searchMaterials(query, categoryIds);
        }
        request.setAttribute("materials", materials);

        request.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(request, response);
    }
}