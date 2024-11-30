package com.azot.course.DAO;

import com.azot.course.entity.Material;
import com.azot.course.entity.User;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    private final Connection connection;

    public MaterialDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMaterial(Material material) throws SQLException {
        String sql = "INSERT INTO Materials (title, content, created_at, author_id, imageUrl) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, material.getTitle());
            stmt.setString(2, material.getContent());
            stmt.setTimestamp(3, new Timestamp(material.getCreatedAt().getTime()));
            stmt.setInt(4, material.getAuthor().getId());
            stmt.setString(5,material.getImageURL());
            stmt.executeUpdate();
        }
    }
    public Material getMaterialById(int id) throws SQLException {
        String sql = "SELECT * FROM Materials WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Material material = new Material();
                    material.setId(rs.getInt("id"));
                    material.setTitle(rs.getString("title"));
                    material.setContent(rs.getString("content"));
                    material.setImageURL(rs.getString("imageUrl"));

                    material.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                    int authorId = rs.getInt("author_id");
                    User author = new User();
                    author.setId(authorId);
                    material.setAuthor(author);

                    return material;
                }
            }
        }
        return null;
    }

    public List<Material> getAllMaterials() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("id"));
                material.setTitle(rs.getString("title"));
                material.setContent(rs.getString("content"));
                material.setImageURL(rs.getString("imageUrl"));

                material.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                int authorId = rs.getInt("author_id");
                User author = new User();
                author.setId(authorId);
                material.setAuthor(author);

                materials.add(material);
            }
        }
        return materials;
    }

    public void updateMaterial(Material material) throws SQLException {
        String sql = "UPDATE Materials SET title = ?, content = ?, created_at = ?, author_id = ?, imageUrl = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, material.getTitle());
            stmt.setString(2, material.getContent());

            stmt.setTimestamp(3, new Timestamp(material.getCreatedAt().getTime()));

            stmt.setInt(4, material.getAuthor().getId());
            stmt.setString(5, material.getImageURL());
            stmt.setInt(6, material.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteMaterial(int id) throws SQLException {
        String sql = "DELETE FROM Materials WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Material> searchMaterialsByTitle(String query) throws SQLException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE title ILIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Material material = new Material();
                    material.setId(rs.getInt("id"));
                    material.setTitle(rs.getString("title"));
                    material.setContent(rs.getString("content"));
                    material.setImageURL(rs.getString("imageUrl"));
                    material.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                    int authorId = rs.getInt("author_id");
                    User author = new User();
                    author.setId(authorId);
                    material.setAuthor(author);

                    materials.add(material);
                }
            }
        }
        return materials;
    }
    public List<Material> getMaterialsByUserId(int userId) throws SQLException {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM materials WHERE author_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    Material material = new Material();
                    material.setId(resultSet.getInt("id"));
                    material.setTitle(resultSet.getString("title"));
                    material.setContent(resultSet.getString("content"));
                    material.setCreatedAt(new Date(resultSet.getTimestamp("created_at").getTime()));
                    int authorId = resultSet.getInt("author_id");
                    User author = new User();
                    author.setId(authorId);
                    material.setAuthor(author);
                    material.setImageURL(resultSet.getString("imageUrl"));

                    materials.add(material);
                }
            }
        }
        return materials;
    }
}
