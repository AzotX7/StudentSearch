package com.azot.course.service;

import com.azot.course.models.Image;

import java.sql.SQLException;

public interface ImageService {

    Image savePhoto(Image photo) throws SQLException;

    Image getPhotoById(int id) throws SQLException;
}
