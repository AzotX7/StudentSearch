package com.azot.course.DAO.DAOImpl;

import com.azot.course.DAO.MaterialDAO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Category;
import com.azot.course.models.Material;
import com.azot.course.models.User;

import java.sql.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialDAOImpl implements MaterialDAO {

    private final Connection connection;

    public MaterialDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void addMaterial(Material material) throws SQLException {
        String sql = "INSERT INTO Materials (title, content, created_at, author_id, imageUrl) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, material.getTitle());
            stmt.setString(2, material.getContent());
            stmt.setTimestamp(3, new Timestamp(material.getCreatedAt().getTime()));
            stmt.setInt(4, material.getAuthor().getId());
            stmt.setString(5,material.getImageURL());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    material.setId(rs.getInt("id"));
                }
            }
        }
    }
    public Material getMaterialById(int id) throws SQLException {
        String sql = "SELECT * FROM Materials WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMaterial(rs);
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
                materials.add(mapRowToMaterial(rs));
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
                    materials.add(mapRowToMaterial(rs));
                }
            }
        }
        return materials;
    }

    public List<Material> searchMaterials(String query, List<Integer> categoryIds) throws SQLException {
        List<Material> materials = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT m.* FROM Materials m ");
        sql.append("LEFT JOIN material_categories mc ON m.id = mc.material_id ");

        if (categoryIds != null && !categoryIds.isEmpty()) {
            sql.append("WHERE mc.category_id IN (")
                    .append(String.join(",", categoryIds.stream().map(String::valueOf).toArray(String[]::new)))
                    .append(") ");
        }

        if (query != null && !query.isEmpty()) {
            if (categoryIds != null && !categoryIds.isEmpty()) {
                sql.append("AND ");
            } else {
                sql.append("WHERE ");
            }
            sql.append("m.title ILIKE ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (query != null && !query.isEmpty()) {
                stmt.setString(paramIndex++, "%" + query + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materials.add(mapRowToMaterial(rs));
                }
            }
        }
        return materials;
    }

    public List<Material> getMaterialsByUserId(int userId) throws SQLException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE author_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materials.add(mapRowToMaterial(rs));
                }
            }
        }
        return materials;
    }

    public List<Category> getCategoriesByMaterialId(int materialId) throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.id, c.name FROM categories c "
                + "JOIN material_categories mc ON c.id = mc.category_id "
                + "WHERE mc.material_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    categories.add(category);
                }
            }
        }
        return categories;
    }

    public void addCategoryToMaterial(int materialId, int categoryId) throws SQLException {
        String sql = "INSERT INTO material_categories (material_id, category_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        }
    }

    public void removeCategoryFromMaterial(int materialId, int categoryId) throws SQLException {
        String sql = "DELETE FROM material_categories WHERE material_id = ? AND category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, materialId);
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();
        }
    }

    public List<Material> findMaterialsByIds(List<Integer> ids) throws SQLException{
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM materials WHERE id IN (" + ids.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < ids.size(); i++) {
                stmt.setInt(i + 1, ids.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    materials.add(mapRowToMaterial(rs));
                }
            }
        }
        return materials;
    }


    private Material mapRowToMaterial(ResultSet rs) throws SQLException {
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