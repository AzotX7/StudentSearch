package com.azot.course.servlets;

import com.azot.course.DAO.CategoryDAO;
import com.azot.course.DAO.MaterialDAO;
import com.azot.course.DTO.CommentDTO;
import com.azot.course.DTO.MaterialDTO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Category;
import com.azot.course.service.CategoryService;
import com.azot.course.user.Role;
import com.azot.course.models.Comment;
import com.azot.course.models.Material;
import com.azot.course.models.User;
import com.azot.course.service.CommentService;
import com.azot.course.service.MaterialService;
import com.azot.course.util.Database;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/materials/*")
public class MaterialServlet extends HttpServlet {
    private MaterialService materialService;
    private CommentService commentService;

    private CategoryService categoryService;

    public MaterialServlet(MaterialService materialService, CommentService commentService, CategoryService categoryService) {
        this.materialService = materialService;
        this.commentService = commentService;
        this.categoryService = categoryService;
    }

    public MaterialServlet() throws SQLException {
        this.materialService = new MaterialService(Database.getConnection());
        this.commentService = new CommentService(Database.getConnection());
        this.categoryService = new CategoryService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            if (action == null || "/".equals(action)) {

                List<MaterialDTO> materials = materialService.getAllMaterials();
                request.setAttribute("materials", materials);
                request.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(request, response);

            } else if ("/add".equals(action)) {
                List<Category> categories = categoryService.getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);

            }else if ("/edit".equals(action)) {

                UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                if (user == null || user.getRole() != Role.ADMIN) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    return;
                }

                int materialId = Integer.parseInt(request.getParameter("materialId"));
                MaterialDTO material = materialService.getMaterialById(materialId);
                if (material != null) {
                    request.setAttribute("material", material);
                    request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Material not found");
                }
            } else if ("/view".equals(action)) {

                int materialId = Integer.parseInt(request.getParameter("materialId"));
                MaterialDTO material = materialService.getMaterialById(materialId);
                if (material != null) {
                    request.setAttribute("material", material);
                    List<CommentDTO> comments = commentService.getCommentsByMaterialId(materialId);
                    request.setAttribute("comments", comments);

                    request.getRequestDispatcher("/WEB-INF/views/viewMaterial.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Material not found");
                }
            }else if ("/profile".equals(action)) {

                UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");


        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            if ("/add".equals(action)) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String imageUrl = request.getParameter("imageUrl");
                String[] categoryIds = request.getParameterValues("categories");

                if (title == null || content == null) {
                    request.setAttribute("errorMessage", "Заголовок или содержание не могут быть пустыми.");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;
                }

                List<Category> categories = new ArrayList<>();
                if (categoryIds != null) {
                    for (String categoryId : categoryIds) {
                        Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
                        categories.add(category);
                    }
                }


                MaterialDTO material = new MaterialDTO();
                material.setTitle(title);
                material.setContent(content);
                material.setCreatedAt(new Date());
                material.setImageURL(imageUrl);
                material.setAuthorId(user.getId());
                material.setCategories(categories);

                materialService.addMaterial(material);

                response.sendRedirect(request.getContextPath() + "/materials");

            } else if ("/update".equals(action) && user.getRole() == Role.ADMIN) {
                int materialId = Integer.parseInt(request.getParameter("materialId"));
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String imageUrl = request.getParameter("imageUrl");

                MaterialDTO material = materialService.getMaterialById(materialId);
                if (material != null) {
                    material.setTitle(title);
                    material.setContent(content);
                    material.setAuthorId(user.getId());
                    material.setImageURL(imageUrl);
                    materialService.updateMaterial(material);
                    response.sendRedirect(request.getContextPath() + "/materials");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Material not found");
                }
            }
            else if (action.matches("/\\d+") && user.getRole() == Role.ADMIN) {
                int materialId = Integer.parseInt(action.substring(1));
                materialService.deleteMaterial(materialId);
                response.sendRedirect(request.getContextPath() + "/materials");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
