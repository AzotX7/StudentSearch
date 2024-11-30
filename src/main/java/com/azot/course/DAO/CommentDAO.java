package com.azot.course.DAO;

import com.azot.course.entity.Comment;
import com.azot.course.entity.Material;
import com.azot.course.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    private final Connection connection;


    public CommentDAO(Connection connection) {
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
        String sql = "SELECT * FROM Comments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setText(rs.getString("content"));

                    comment.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                    int authorId = rs.getInt("author_id");
                    User author = new User();
                    author.setId(authorId);
                    comment.setAuthor(author);

                    int materialId = rs.getInt("material_id");
                    Material material = new Material();
                    material.setId(materialId);
                    comment.setMaterial(material);

                    return comment;
                }
            }
        }
        return null;
    }

    public List<Comment> getAllComments() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setText(rs.getString("content"));

                comment.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                int authorId = rs.getInt("author_id");
                User author = new User();
                author.setId(authorId);
                comment.setAuthor(author);

                int materialId = rs.getInt("material_id");
                Material material = new Material();
                material.setId(materialId);
                comment.setMaterial(material);

                comments.add(comment);
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
        String sql = "SELECT c.id, c.text, c.created_at, u.id AS user_id, u.username AS user_name " +
                "FROM Comments c JOIN Users u ON c.author_id = u.id " +
                "WHERE c.material_id = ? ORDER BY c.created_at DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setText(rs.getString("text"));
                    comment.setCreatedAt(rs.getTimestamp("created_at"));

                    User author = new User();
                    author.setId(rs.getInt("user_id"));
                    author.setUsername(rs.getString("user_name"));
                    comment.setAuthor(author);

                    comments.add(comment);
                }
            }
        }
        return comments;
    }


}
