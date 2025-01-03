package com.azot.course.servlets;

import com.azot.course.DTO.CommentDTO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.service.CommentService;
import com.azot.course.database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

    private final CommentService commentService;

    public CommentServlet() throws SQLException {
        this.commentService = new CommentService(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int materialId = Integer.parseInt(request.getParameter("materialId"));
        try {
            List<CommentDTO> comments = commentService.getCommentsByMaterialId(materialId);
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("/WEB-INF/views/viewMaterial.jsp").forward(request, response);
        } catch (Exception e) {
            log("Ошибка при загрузке комментариев", e);
            request.setAttribute("errorMessage", "Не удалось загрузить комментарии.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String action = request.getParameter("action");
        if ("deleteComment".equals(action)) {
            handleDeleteComment(request, response);
        } else {
            handleAddComment(request, response);
        }
    }

    private UserDTO getAuthenticatedUser(HttpServletRequest request) throws ServletException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user == null) {
            throw new ServletException("Пользователь не авторизован");
        }
        return user;
    }

    private void handleDeleteComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        try {
            commentService.deleteComment(commentId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            log("Ошибка при удалении комментария", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleAddComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int materialId = Integer.parseInt(request.getParameter("materialId"));
        UserDTO user = getAuthenticatedUser(request);
        String commentText = request.getParameter("commentText");

        if (commentText == null || commentText.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CommentDTO comment = new CommentDTO();
        comment.setText(commentText);
        comment.setCreatedAt(new Date());
        comment.setUsername(user.getUsername());
        comment.setAuthorId(user.getId());

        try {
            commentService.addComment(materialId, comment);


            response.setContentType("text/html");
            response.getWriter().write(generateCommentHtml(comment));
        } catch (Exception e) {
            log("Ошибка при добавлении комментария", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String generateCommentHtml(CommentDTO comment) {
        return "<div class='comment'>" +
                "<div class='comment-author'>" + comment.getUsername() + "</div>" +
                "<div class='comment-date'>" + comment.getCreatedAt() + "</div>" +
                "<div class='comment-text'>" + comment.getText() + "</div>" +
                "</div>";
    }
}