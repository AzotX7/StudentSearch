package com.azot.course.servlets;

import com.azot.course.models.Image;
import com.azot.course.service.ImageService;
import com.azot.course.service.serviceImpl.ImageServiceImpl;
import com.azot.course.database.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/photo/*")
public class PhotoServlet extends HttpServlet {

    private final ImageService photoService;

    public PhotoServlet() throws Exception {
        this.photoService = new ImageServiceImpl(Database.getConnection());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID фотографии не указан.");
            return;
        }

        try {
            int photoId = Integer.parseInt(pathInfo.substring(1));
            Image photo = photoService.getPhotoById(photoId);

            if (photo == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Фотография не найдена.");
                return;
            }

            response.setContentType(photo.getContentType());
            response.setContentLength(photo.getFileData().length);
            response.setHeader("Cache-Control", "public, max-age=86400");

            response.getOutputStream().write(photo.getFileData());

        } catch (NumberFormatException | SQLException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный ID фотографии.");
        }
    }
}
