package com.azot.course.DAO;

import com.azot.course.models.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {

    List<Category> getAllCategories() throws SQLException;

    void addCategory(String categoryName) throws SQLException;

    Category getCategoryById(int categoryId) throws SQLException;

    void deleteCategory(int id) throws SQLException;
}
