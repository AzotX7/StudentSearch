package com.azot.course.DAO;

import com.azot.course.DTO.MaterialDTO;
import com.azot.course.models.Category;
import com.azot.course.models.Material;

import java.sql.SQLException;
import java.util.List;

public interface MaterialDAO {

    void addMaterial(Material material) throws SQLException;

    Material getMaterialById(int id) throws SQLException;

    List<Material> getAllMaterials() throws SQLException;

    void updateMaterial(Material material) throws SQLException;

    void deleteMaterial(int id) throws SQLException;

    List<Material> searchMaterialsByTitle(String query) throws SQLException;

    List<Material> getMaterialsByUserId(int userId) throws SQLException;

    void addCategoryToMaterial(int materialId, int categoryId) throws SQLException;

    List<Material> searchMaterials(String query, List<Integer> categoryIds) throws SQLException;

}
