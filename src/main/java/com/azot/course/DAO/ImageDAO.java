package com.azot.course.DAO;

import com.azot.course.models.Image;

import java.sql.SQLException;

public interface ImageDAO {

    Image save(Image image) throws SQLException;

    Image findById(int id) throws SQLException;
}
