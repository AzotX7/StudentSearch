package com.azot.course.DAO.DAOImpl;

import com.azot.course.DAO.CommentDAO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Comment;
import com.azot.course.models.Material;
import com.azot.course.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {

    private final Connection connection;

    public CommentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void addComment(int materialId,Comment comment) throws SQLException {
        String sql = "INSERT INTO Comments (text, created_at, author_id, material_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, comment.getText());
            stmt.setTimestamp(2, new Timestamp(comment.getCreatedAt().getTime()));
            stmt.setInt(3, comment.getAuthor().getId());
            stmt.setInt(4, materialId);
            stmt.executeUpdate();
        }
    }


    public Comment getCommentById(int id) throws SQLException {
        String sql = "SELECT c.*, u.username AS user_name " +
                "FROM Comments c " +
                "JOIN Users u ON c.author_id = u.id WHERE c.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToComment(rs, true);
                }
            }
        }
        return null;
    }

    public List<Comment> getAllComments() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, u.username AS user_name " +
                "FROM Comments c " +
                "JOIN Users u ON c.author_id = u.id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                comments.add(mapRowToComment(rs, true));
            }
        }
        return comments;
    }

    public void updateComment(Comment comment) throws SQLException {
        String sql = "UPDATE Comments SET content = ?, created_at = ?, author_id = ?, material_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, comment.getText());
            stmt.setTimestamp(2, new Timestamp(comment.getCreatedAt().getTime()));
            stmt.setInt(3, comment.getAuthor().getId());
            stmt.setInt(4, comment.getMaterial().getId());
            stmt.setInt(5, comment.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteComment(int id) throws SQLException {
        String sql = "DELETE FROM Comments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Comment> findCommentsByMaterialId(int materialId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, u.username AS user_name " +
                "FROM Comments c " +
                "JOIN Users u ON c.author_id = u.id " +
                "WHERE c.material_id = ? " +
                "ORDER BY c.created_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapRowToComment(rs, false));
                }
            }
        }
        return comments;
    }

    private Comment mapRowToComment(ResultSet rs, boolean includeMaterial) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setText(rs.getString("text"));
        comment.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

        User author = new User();
        author.setId(rs.getInt("author_id"));
        author.setUsername(rs.getString("user_name"));
        comment.setAuthor(author);

        if (includeMaterial) {
            Material material = new Material();
            material.setId(rs.getInt("material_id"));
            comment.setMaterial(material);
        }

        return comment;
    }
}