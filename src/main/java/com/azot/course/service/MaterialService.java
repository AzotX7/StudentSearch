package com.azot.course.service;

import com.azot.course.DAO.MaterialDAO;
import com.azot.course.entity.Material;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;
public class MaterialService {

    private final MaterialDAO materialDAO;

    public MaterialService(Connection connection){
        this.materialDAO = new MaterialDAO(connection);
    }
    @SneakyThrows
    public void addMaterial(Material material) {
        materialDAO.addMaterial(material);
    }

    @SneakyThrows
    public Material getMaterialById(int id) {
        return materialDAO.getMaterialById(id);
    }

    @SneakyThrows
    public List<Material> getAllMaterials() {
        return materialDAO.getAllMaterials();
    }

    @SneakyThrows
    public void updateMaterial(Material material) {
        materialDAO.updateMaterial(material);
    }

    @SneakyThrows
    public void deleteMaterial(int id) {
        materialDAO.deleteMaterial(id);
    }
}
