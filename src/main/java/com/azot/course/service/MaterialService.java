package com.azot.course.service;

import com.azot.course.DAO.DAOImpl.MaterialDAOImpl;
import com.azot.course.DAO.MaterialDAO;
import com.azot.course.DTO.MaterialDTO;
import com.azot.course.models.Category;
import com.azot.course.models.Material;
import com.azot.course.models.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialService {

    private final MaterialDAOImpl materialDAO;

    public MaterialService(Connection connection){
        this.materialDAO = new MaterialDAOImpl(connection);
    }
    @SneakyThrows
    public void addMaterial(MaterialDTO materialDTO) {
        Material material = fromDTO(materialDTO);
        materialDAO.addMaterial(material);

        if (materialDTO.getCategories() != null) {
            for (Category category : materialDTO.getCategories()) {
                materialDAO.addCategoryToMaterial(material.getId(), category.getId());
            }
        }
    }

    @SneakyThrows
    public MaterialDTO getMaterialById(int id) {
        Material material = materialDAO.getMaterialById(id);
        return toDTO(material);
    }

    @SneakyThrows
    public List<MaterialDTO> getAllMaterials() {
        return materialDAO.getAllMaterials().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @SneakyThrows
    public List<MaterialDTO> searchMaterials(String query, List<Integer> categoryIds){
        return materialDAO.searchMaterials(query,categoryIds).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @SneakyThrows
    public List<MaterialDTO> searchMaterialsByTitle(String query){
        return materialDAO.searchMaterialsByTitle(query).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @SneakyThrows
    public void updateMaterial(MaterialDTO materialDTO) {
        Material material = fromDTO(materialDTO);
        materialDAO.updateMaterial(material);    }

    @SneakyThrows
    public void deleteMaterial(int id) {
        materialDAO.deleteMaterial(id);
    }
    @SneakyThrows
    public List<Category> getCategoriesForMaterial(int materialId) {
        return materialDAO.getCategoriesByMaterialId(materialId);
    }

    @SneakyThrows
    public List<MaterialDTO> findMaterialsByIds(List<Integer> ids){
        return materialDAO.findMaterialsByIds(ids).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @SneakyThrows
    public void addCategoryToMaterial(int materialId, int categoryId) {
        materialDAO.addCategoryToMaterial(materialId, categoryId);
    }
    @SneakyThrows
    public void removeCategoryFromMaterial(int materialId, int categoryId) {
        materialDAO.removeCategoryFromMaterial(materialId, categoryId);
    }

    private MaterialDTO toDTO(Material material) {
        return new MaterialDTO(
                material.getId(),
                material.getTitle(),
                material.getContent(),
                material.getCreatedAt(),
                material.getImageURL(),
                material.getAuthor() != null ? material.getAuthor().getId() : 0,
                material.getCategories()
        );
    }

    private Material fromDTO(MaterialDTO materialDTO) {
        Material material = new Material();
        material.setId(materialDTO.getId());
        material.setTitle(materialDTO.getTitle());
        material.setContent(materialDTO.getContent());
        material.setCreatedAt(materialDTO.getCreatedAt());
        material.setImageURL(materialDTO.getImageURL());

        User user = new User();
        user.setId(materialDTO.getAuthorId());
        material.setAuthor(user);

        return material;
    }
}