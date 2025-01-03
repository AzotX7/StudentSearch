package com.azot.course.DAO.DAOImpl;

import com.azot.course.DAO.CategoryDAO;
import com.azot.course.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private final Connection connection;

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        }
        return categories;
    }


    public void addCategory(String categoryName) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            stmt.executeUpdate();
        }
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        Category category = new Category();
        String sql = "SELECT id, name FROM categories WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()){
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                }
            }
        }
        return category;
    }

    public void deleteCategory(int id) throws SQLException{
        String sql = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}