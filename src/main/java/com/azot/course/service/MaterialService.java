package com.azot.course.service;

import com.azot.course.DTO.MaterialDTO;

import java.sql.SQLException;
import java.util.List;

public interface MaterialService {

    void addMaterial(MaterialDTO materialDTO) throws SQLException;

    MaterialDTO getMaterialById(int id) throws SQLException;

    List<MaterialDTO> getAllMaterials() throws SQLException;

    List<MaterialDTO> searchMaterials(String query, List<Integer> categoryIds) throws SQLException;

    List<MaterialDTO> searchMaterialsByTitle(String query) throws SQLException;

    void updateMaterial(MaterialDTO materialDTO) throws SQLException;

    void deleteMaterial(int id) throws SQLException;

    List<MaterialDTO> getMaterialsByUserId(int userId) throws SQLException;
}
