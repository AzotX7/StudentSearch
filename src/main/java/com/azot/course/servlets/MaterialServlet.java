package com.azot.course.servlets;


import com.azot.course.DTO.CommentDTO;
import com.azot.course.DTO.MaterialDTO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Category;
import com.azot.course.models.Image;
import com.azot.course.service.CategoryService;
import com.azot.course.service.ImageService;
import com.azot.course.user.Role;
import com.azot.course.service.CommentService;
import com.azot.course.service.MaterialService;
import com.azot.course.database.Database;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@WebServlet("/materials/*")
@MultipartConfig(location = "C:/temp",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class MaterialServlet extends HttpServlet {
    private MaterialService materialService;
    private CommentService commentService;
    private CategoryService categoryService;
    private ImageService photoService;

    private static final String ACTION_ADD = "/add";
    private static final String ACTION_EDIT = "/edit";
    private static final String ACTION_VIEW = "/view";
    private static final String ACTION_PROFILE = "/profile";
    private static final String ACTION_UPDATE = "/update";
    private static final String ACTION_HOME = "/";


    public MaterialServlet() throws SQLException {
        this.materialService = new MaterialService(Database.getConnection());
        this.commentService = new CommentService(Database.getConnection());
        this.categoryService = new CategoryService(Database.getConnection());
        this.photoService = new ImageService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action == null ? ACTION_HOME : action) {
                case ACTION_HOME:
                    List<MaterialDTO> materials = materialService.getAllMaterials();
                    request.setAttribute("materials", materials);
                    request.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(request, response);
                    break;

                case ACTION_ADD:
                    List<Category> categories = categoryService.getAllCategories();
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
                    break;

                case ACTION_EDIT:
                    UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                    if (!hasPermission(user, Role.ADMIN)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
                        return;
                    }

                    int materialId = Integer.parseInt(request.getParameter("materialId"));
                    MaterialDTO material = materialService.getMaterialById(materialId);
                    if (material != null) {
                        request.setAttribute("material", material);
                        request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Материал не найден");
                    }
                    break;

                case ACTION_VIEW:
                    materialId = Integer.parseInt(request.getParameter("materialId"));
                    material = materialService.getMaterialById(materialId);
                    if (material != null) {
                        request.setAttribute("material", material);
                        List<CommentDTO> comments = commentService.getCommentsByMaterialId(materialId);
                        request.setAttribute("comments", comments);
                        request.getRequestDispatcher("/WEB-INF/views/viewMaterial.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Материал не найден");
                    }
                    break;

                case ACTION_PROFILE:
                    user = (UserDTO) request.getSession().getAttribute("user");
                    if (user != null) {
                        request.setAttribute("user", user);
                        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/login");
                    }
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Неизвестная команда");
                    break;
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
        String method = request.getParameter("_method");
        if ("delete".equalsIgnoreCase(method)) {
            handleDeleteMaterial(request, response, user);
            return;
        }
            switch (action) {
                case ACTION_ADD:
                case ACTION_UPDATE:
                        handleAddOrUpdateMaterial(request, response, user, action);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Неизвестная команда");
                    break;
            }


        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }


    private void handleAddOrUpdateMaterial(HttpServletRequest request, HttpServletResponse response, UserDTO user, String action) throws ServletException, IOException, SQLException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Part filePart = request.getPart("file");
        String[] categoryIds = request.getParameterValues("categories");

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
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

        Image photo = processImage(filePart);

        MaterialDTO material = new MaterialDTO();
        material.setTitle(title);
        material.setContent(content);
        material.setCreatedAt(new Date());
        material.setPhotoId(photo != null ? photo.getId() : 0);
        material.setAuthorId(user.getId());
        material.setCategories(categories);

        if (ACTION_ADD.equals(action)) {
            materialService.addMaterial(material);
        } else if (ACTION_UPDATE.equals(action)) {
            int materialId = Integer.parseInt(request.getParameter("materialId"));
            material.setId(materialId);
            materialService.updateMaterial(material);
        }

        response.sendRedirect(request.getContextPath() + "/materials");
    }

    private void handleDeleteMaterial(HttpServletRequest request, HttpServletResponse response, UserDTO user) throws ServletException, IOException, SQLException {
        if (!hasPermission(user, Role.ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
            return;
        }

        int materialId = Integer.parseInt(request.getParameter("materialId"));
        MaterialDTO material = materialService.getMaterialById(materialId);
        if (material == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Материал не найден");
            return;
        }
        try {
            materialService.deleteMaterial(materialId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при удалении материала: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("success");
        } else {
            response.sendRedirect(request.getContextPath() + "/materials");
        }
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private byte[] readBytesFromInputStream(InputStream inputStream) throws IOException {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    private Image processImage(Part filePart) throws IOException, SQLException {
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = getFileName(filePart);
            String contentType = filePart.getContentType();
            InputStream fileContent = filePart.getInputStream();
            byte[] fileData = readBytesFromInputStream(fileContent);

            Image photo = new Image();
            photo.setFileName(fileName);
            photo.setContentType(contentType);
            photo.setFileData(fileData);
            return photoService.savePhoto(photo);
        }
        return null;
    }

    private boolean hasPermission(UserDTO user, Role requiredRole) {
        return user != null && user.getRole() == requiredRole;
    }
}
