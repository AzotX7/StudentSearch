package com.azot.course.service.serviceImpl;

import com.azot.course.DAO.DAOImpl.MaterialDAOImpl;
import com.azot.course.DAO.MaterialDAO;
import com.azot.course.DTO.MaterialDTO;
import com.azot.course.models.Category;
import com.azot.course.models.Image;
import com.azot.course.models.Material;
import com.azot.course.models.User;
import com.azot.course.service.MaterialService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialServiceImpl implements MaterialService {

    private final MaterialDAO materialDAO;

    public MaterialServiceImpl(Connection connection){
        this.materialDAO = new MaterialDAOImpl(connection);
    }

    public void addMaterial(MaterialDTO materialDTO) throws SQLException {
        Material material = fromDTO(materialDTO);
        materialDAO.addMaterial(material);

        if (materialDTO.getCategories() != null) {
            for (Category category : materialDTO.getCategories()) {
                materialDAO.addCategoryToMaterial(material.getId(), category.getId());
            }
        }
    }


    public MaterialDTO getMaterialById(int id) throws SQLException {
        Material material = materialDAO.getMaterialById(id);
        return toDTO(material);
    }

    public List<MaterialDTO> getAllMaterials() throws SQLException {
        return materialDAO.getAllMaterials().stream().map(this::toDTO).collect(Collectors.toList());
    }


    public List<MaterialDTO> searchMaterials(String query, List<Integer> categoryIds) throws SQLException {
        return materialDAO.searchMaterials(query,categoryIds).stream().map(this::toDTO).collect(Collectors.toList());
    }


    public List<MaterialDTO> searchMaterialsByTitle(String query) throws SQLException {
        return materialDAO.searchMaterialsByTitle(query).stream().map(this::toDTO).collect(Collectors.toList());
    }


    public void updateMaterial(MaterialDTO materialDTO) throws SQLException {
        Material material = fromDTO(materialDTO);
        materialDAO.updateMaterial(material);    }


    public void deleteMaterial(int id) throws SQLException {
        materialDAO.deleteMaterial(id);
    }

    public List<MaterialDTO> getMaterialsByUserId(int userId) throws SQLException {
        return materialDAO.getMaterialsByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private MaterialDTO toDTO(Material material) {
        return new MaterialDTO(
                material.getId(),
                material.getTitle(),
                material.getContent(),
                material.getCreatedAt(),
                material.getImage() != null ? material.getImage().getId() : 0,
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


        User user = new User();
        user.setId(materialDTO.getAuthorId());
        material.setAuthor(user);

        Image photo = new Image();
        photo.setId(materialDTO.getPhotoId());
        material.setImage(photo);

        return material;
    }
}