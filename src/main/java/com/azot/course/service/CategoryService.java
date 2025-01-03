package com.azot.course.service;

import com.azot.course.DAO.CategoryDAO;
import com.azot.course.DAO.DAOImpl.CategoryDAOImpl;
import com.azot.course.models.Category;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class CategoryService {

    private final CategoryDAO categoryDAOImpl;

    public CategoryService(Connection connection) {
        this.categoryDAOImpl = new CategoryDAOImpl(connection);
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAOImpl.getAllCategories();
    }


    public void addCategory(String category) throws SQLException {
        categoryDAOImpl.addCategory(category);
    }


    public Category getCategoryById(int categoryId) throws SQLException{
        return categoryDAOImpl.getCategoryById(categoryId);
    }


    public void deleteCategory(int categoryId) throws SQLException{
        categoryDAOImpl.deleteCategory(categoryId);
    }
}
