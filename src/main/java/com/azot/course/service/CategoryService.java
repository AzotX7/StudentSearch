package com.azot.course.service;

import com.azot.course.models.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories() throws SQLException;

    void addCategory(String category) throws SQLException;

    Category getCategoryById(int categoryId) throws SQLException;

    void deleteCategory(int categoryId) throws SQLException;
}
