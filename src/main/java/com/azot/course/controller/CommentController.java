package com.azot.course.controller;

import com.azot.course.entity.Comment;
import com.azot.course.entity.Material;
import com.azot.course.entity.User;
import com.azot.course.service.CommentService;
import com.azot.course.util.Database;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/comments")
public class CommentController extends HttpServlet {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SneakyThrows
    public CommentController() {
        this.commentService = new CommentService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int materialId = Integer.parseInt(request.getParameter("materialId"));
        try {
            List<Comment> comments = commentService.getCommentsByMaterialId(materialId);
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("/WEB-INF/views/viewMaterial.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("deleteComment".equals(action)) {

            int commentId = Integer.parseInt(request.getParameter("commentId"));
            try {
                commentService.deleteComment(commentId);
                response.sendRedirect(request.getHeader("Referer"));
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Ошибка при удалении комментария: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
            return;
        }

        String materialIdStr = request.getParameter("materialId");
        int materialId;

        try {

            materialId = Integer.parseInt(materialIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Неверный идентификатор материала");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "Пользователь не авторизован");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        String commentText = request.getParameter("commentText");
        if (commentText == null || commentText.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Комментарий не может быть пустым");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setCreatedAt(new Date());
        comment.setAuthor(user);
        Material material = new Material();
        material.setId(materialId);
        comment.setMaterial(material);

        try {
            commentService.addComment(materialId, comment);
            response.sendRedirect(request.getContextPath() + "/materials/view?materialId=" + materialId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при добавлении комментария: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}