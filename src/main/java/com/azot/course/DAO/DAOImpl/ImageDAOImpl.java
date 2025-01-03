package com.azot.course.DAO.DAOImpl;

import com.azot.course.DAO.ImageDAO;
import com.azot.course.models.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDAOImpl implements ImageDAO {

    private final Connection connection;

    public ImageDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Image save(Image image) throws SQLException {
        String sql = "INSERT INTO photos (fileName, fileData, contentType) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, image.getFileName());
            statement.setBytes(2, image.getFileData());
            statement.setString(3, image.getContentType());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    image.setId(generatedKeys.getInt(1));
                }
            }
        }
        return image;
    }

    @Override
    public Image findById(int id) throws SQLException {
        String sql = "SELECT * FROM photos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Image image = new Image();
                    image.setId(resultSet.getInt("id"));
                    image.setFileName(resultSet.getString("fileName"));
                    image.setFileData(resultSet.getBytes("fileData"));
                    image.setContentType(resultSet.getString("contentType"));
                    return image;
                }
            }
        }
        return null;
    }
}
