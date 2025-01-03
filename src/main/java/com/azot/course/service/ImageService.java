package com.azot.course.service;

import com.azot.course.DAO.DAOImpl.ImageDAOImpl;
import com.azot.course.DAO.ImageDAO;
import com.azot.course.models.Image;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;

public class ImageService {

    private final ImageDAO imageDAO;

    public ImageService(Connection connection) {
        this.imageDAO = new ImageDAOImpl(connection);
    }


    public Image savePhoto(Image photo) throws SQLException {
        return imageDAO.save(photo);
    }


    public Image getPhotoById(int id) throws SQLException {
        return imageDAO.findById(id);
    }
}
