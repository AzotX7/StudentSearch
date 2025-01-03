package com.azot.course.servlets;


import com.azot.course.DTO.MaterialDTO;
import com.azot.course.service.MaterialService;
import com.azot.course.database.Database;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {

    private final MaterialService materialService;

    private static final String FAVORITES_COOKIE_NAME = "favorites";
    private static final int COOKIE_MAX_AGE = 24 * 60 * 60;
    private static final String ADD_FAVORITE = "add";
    private static final String REMOVE_FAVORITE = "remove";

    public FavoritesServlet() throws SQLException {
        this.materialService = new MaterialService(Database.getConnection());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {


            List<String> favoriteMaterialIds = getFavoriteMaterialIdsFromCookies(request);


            List<MaterialDTO> favoriteMaterials = new ArrayList<>();
            for (String materialId : favoriteMaterialIds) {

                MaterialDTO material = materialService.getMaterialById(Integer.parseInt(materialId));
                if (material != null) {
                    favoriteMaterials.add(material);
                }
            }

            request.setAttribute("favoriteMaterials", favoriteMaterials);
            request.getRequestDispatcher("/WEB-INF/views/favorites.jsp").forward(request, response);
        } catch (SQLException e){
            request.setAttribute("errorMessage", "Ошибка при обработке запроса" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String materialId = request.getParameter("materialId");
        String action = request.getParameter("action");

        List<String> favoriteMaterialIds = getFavoriteMaterialIdsFromCookies(request);


        switch (action) {
            case ADD_FAVORITE:
                if (!favoriteMaterialIds.contains(materialId)) {
                    favoriteMaterialIds.add(materialId);
                }
                break;
            case REMOVE_FAVORITE:
                favoriteMaterialIds.remove(materialId);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }

        updateFavoritesCookie(response, favoriteMaterialIds);

        response.sendRedirect(request.getContextPath() + "/favorites");
    }


    private List<String> getFavoriteMaterialIdsFromCookies(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        List<String> favoriteMaterialIds = new ArrayList<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (FAVORITES_COOKIE_NAME.equals(cookie.getName())) {
                    String favoritesCookie = cookie.getValue();
                    if (favoritesCookie != null && !favoritesCookie.isEmpty()) {
                        favoriteMaterialIds = Arrays.asList(URLDecoder.decode(favoritesCookie, "UTF-8").split("\\|"));
                    }
                    break;
                }
            }
        }
        return favoriteMaterialIds;
    }

    private void updateFavoritesCookie(HttpServletResponse response, List<String> favoriteMaterialIds) throws IOException {
        String updatedFavorites = URLEncoder.encode(String.join("|", favoriteMaterialIds), "UTF-8");
        Cookie favoritesCookie = new Cookie(FAVORITES_COOKIE_NAME, updatedFavorites);
        favoritesCookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(favoritesCookie);
    }
}
